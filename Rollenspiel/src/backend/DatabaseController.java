package backend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.io.File;

/**
 * Verwaltet den Zugriff auf die H2-Datenbank und initialisiert das nötige Schema,
 * falls die Datenbank noch nicht existiert. Diese Klasse bietet Methoden zum
 * Herstellen einer Verbindung sowie zum Setup der Datenbankstruktur.
 */
public class DatabaseController {

    /**
     * Dateipfad zur H2-Datenbankdatei (ohne Dateiendung).
     */
    private static final String DB_PATH = "./Rollenspiel/db/lernspiel";

    /**
     * JDBC-URL zur Verbindung mit der H2-Datenbank, inklusive MySQL-Kompatibilitätsmodus.
     */
    private static final String URL = "jdbc:h2:" + DB_PATH + ";MODE=MySQL;DATABASE_TO_UPPER=FALSE";

    /**
     * Benutzername für die Datenbankverbindung.
     */
    private static final String USER = "sa";

    /**
     * Passwort für die Datenbankverbindung. Standardmäßig leer.
     */
    private static final String PASS = "";

    /**
     * Stellt eine Verbindung zur H2-Datenbank her. Lädt vorher explizit den H2-Treiber.
     *
     * @return eine aktive {@link Connection} zur Datenbank
     * @throws Exception wenn der H2-Treiber nicht gefunden wird oder keine Verbindung möglich ist
     */
    public static Connection getConnection() throws Exception {
        try {
            // Zwingt Java, die H2-Treiberklasse zu laden
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("H2 Treiber JAR nicht gefunden! Prüfe den libs Ordner.");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }

    /**
     * Überprüft, ob die Datenbank bereits existiert. Falls nicht, wird das erforderliche
     * Schema aus der Datei {@code schema.sql} geladen und in der H2-Datenbank ausgeführt.
     *
     * Gibt Statusmeldungen auf der Konsole aus.
     */
    public static void setupDatabase() {
        // Prüfen, ob die .mv.db Datei existiert
        File dbFile = new File(DB_PATH + ".mv.db");

        if (dbFile.exists()) {
            System.out.println("Datenbank gefunden. Überspringe Initialisierung.");
            return;
        }

        System.out.println("Datenbank nicht gefunden. Erstelle neues Schema...");

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             InputStream inputStream = DatabaseController.class.getResourceAsStream("/resources/schema.sql")) {

            if (inputStream == null) throw new Exception("schema.sql nicht im Resource-Ordner gefunden!");

            // SQL-Datei einlesen
            String sql = new BufferedReader(new InputStreamReader(inputStream))
                    .lines().collect(Collectors.joining("\n"));

            // H2 kann mehrere Statements ausführen, wenn sie durch ; getrennt sind
            statement.execute(sql);
            System.out.println("Schema erfolgreich importiert.");

        } catch (Exception e) {
            System.err.println("Fehler beim DB-Setup: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void speichereSpielstand(Spieler spieler, List<Integer> geloesteFragenIds) {
        String upsertPlayer = "MERGE INTO player (name, score, rank_name) KEY(name) VALUES (?, ?, ?)";
        String getPlayerId = "SELECT player_id FROM player WHERE name = ?";
        String insertProgress = "MERGE INTO player_progress (player_id, question_id) KEY(player_id, question_id) VALUES (?, ?)";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);

            // 1. Spieler speichern/aktualisieren
            try (PreparedStatement pstmt = conn.prepareStatement(upsertPlayer)) {
                pstmt.setString(1, spieler.getName());
                pstmt.setInt(2, spieler.getPunktekonto());
                pstmt.setString(3, spieler.getLevel().toString());
                pstmt.executeUpdate();
            }

            // 2. ID holen (sicher via PreparedStatement)
            int playerId = -1;
            try (PreparedStatement pstmtId = conn.prepareStatement(getPlayerId)) {
                pstmtId.setString(1, spieler.getName());
                try (ResultSet rs = pstmtId.executeQuery()) {
                    if (rs.next()) playerId = rs.getInt("player_id");
                }
            }

            // 3. Fortschritt speichern
            if (playerId != -1 && geloesteFragenIds != null) {
                try (PreparedStatement pProgress = conn.prepareStatement(insertProgress)) {
                    for (Integer qId : geloesteFragenIds) {
                        pProgress.setInt(1, playerId);
                        pProgress.setInt(2, qId);
                        pProgress.addBatch();
                    }
                    pProgress.executeBatch();
                }
            }

            conn.commit();
            System.out.println("Spielstand für " + spieler.getName() + " erfolgreich gesichert.");
        } catch (Exception e) {
            System.err.println("Fehler beim Speichern: " + e.getMessage());
            // Bei Fehler: Alles zurückrollen
            try (Connection conn = getConnection()) { conn.rollback(); } catch (Exception ignored) {}
        }
    }

    public static List<String> getAlleSpielerNamen() {
        List<String> namen = new ArrayList<>();
        String sql = "SELECT name FROM player";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                namen.add(rs.getString("name"));
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen der Namen: " + e.getMessage());
        }
        return namen;
    }


    /**
     * Lädt einen Spieler aus der Datenbank.
     * @return ein Spieler-Objekt oder null, wenn der Name nicht existiert.
     */
    public static Spieler ladeSpieler(String name) {
        String sql = "SELECT * FROM player WHERE name = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Spieler s = new Spieler(rs.getString("name"));
                s.setPunktekonto(rs.getInt("score"));

                // Erst die Fragen für diesen Spieler laden, damit
                // die Fortschrittsberechnung im nächsten Schritt klappt!
                FragenRepository.ladeFragenFuerSpieler(name);

                // Jetzt die internen Berechnungen triggern
                s.setGesamtFortschritt();
                s.setLevel();

                // Wichtig: Alle Themen-Fortschritte initialisieren
                for (enums.Themenbereich tb : enums.Themenbereich.values()) {
                    double fortschritt = FragenRepository.berechneFortschrittFuerThema(tb);
                    // Da die Felder im Spieler private sind, brauchen wir die Setter:
                    switch(tb) {
                        case SQL -> s.setFortschrittSQL(fortschritt);
                        case UML -> s.setFortschrittUML(fortschritt);
                        case DATENBANK -> s.setFortschrittDATENBANK(fortschritt);
                        case PSEUDOCODE -> s.setFortschrittPSEUDOCODE(fortschritt);
                        case RECHT -> s.setFortschrittRECHT(fortschritt);
                        case WIRTSCHAFT -> s.setFortschrittWIRTSCHAFT(fortschritt);
                        case MASCHINELLESLEARNING -> s.setFortschrittMASCHINELLES_LEARNING(fortschritt);
                    }
                }

                s.setMedallienArray();
                return s;
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Laden des Spielers: " + e.getMessage());
        }
        return null;
    }
}