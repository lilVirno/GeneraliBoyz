package backend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
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
}