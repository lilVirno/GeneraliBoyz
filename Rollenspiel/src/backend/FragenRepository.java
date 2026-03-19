package backend;

import enums.Fragenkategorie;
import enums.Themenbereich;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Das FragenRepository dient als zentrale Stelle für das Laden, Filtern und
 * Berechnen von Informationen über Fragen. Es lädt die Fragen aus der Datenbank
 * oder erstellt eine Mock-Liste, falls noch keine Verbindung besteht.
 */
public class FragenRepository {
    /*
    Diese Klasse enthält Mock-Fragen zum Testen bis die Datenbank eingebunden ist
     */

    /**
     * Liste aller geladenen Fragen. Wird lazy initialisiert.
     */
    public static List<Frage> alleFragen = null;

    /**
     * Liefert alle Fragen zurück. Wenn sie noch nicht geladen wurden,
     * werden sie aus der Datenbank initialisiert.
     *
     * @return Liste aller Fragen
     */
    // Im FragenRepository.java anpassen:
    public static List<Frage> getAlleFragen() {
        if (alleFragen == null) {
            // Falls jemand vergisst, einen Spieler zu laden, initialisieren wir
            // die Liste leer oder werfen eine Info aus.
            alleFragen = new ArrayList<>();
            System.err.println("Warnung: Fragen wurden ohne Spieler-Kontext angefordert.");
        }
        return alleFragen;
    }

    /**
     * Liefert alle ungelösten Fragen eines bestimmten Themenbereichs.
     *
     * @param thema der gewünschte Themenbereich
     * @return Liste der ungelösten Fragen
     */
    public static List<Frage> getUngeloesteFragen(Themenbereich thema) {
        return getAlleFragen().stream()
                .filter(f -> f.getThemenbereich() == thema)
                .filter(f -> !f.isGeloest())
                .toList();
    }

    /**
     * Berechnet den Fortschritt eines Themenbereichs als Dezimalwert zwischen 0 und 1.
     *
     * @param thema der Themenbereich
     * @return Fortschrittswert (0.0 bis 1.0)
     */
    public static double berechneFortschrittFuerThema(Themenbereich thema) {
        List<Frage> alleThemenFragen = getAlleFragen().stream()
                .filter(f -> f.getThemenbereich() == thema)
                .toList();

        if (alleThemenFragen.isEmpty()) return 0.0;

        long beantwortet = alleThemenFragen.stream().filter(Frage::isGeloest).count();
        return (double) beantwortet / alleThemenFragen.size();
    }

    /**
     * Lädt alle Fragen aus der Datenbank und fügt sie der Liste hinzu.
     *
     * @param fragen Liste, die befüllt werden soll
     */
//    private static void initialisiereFragen(List<Frage> fragen) {
//        String sql = "SELECT q.question_id, q.question_type, q.start_text, q.points, t.name AS thema_name " +
//                "FROM question q " +
//                "JOIN Question_Theme qt ON q.question_id = qt.question_id " +
//                "JOIN theme t ON qt.theme_id = t.theme_id " +
//                "ORDER BY RAND()";
//
//        try (Connection connection = DatabaseController.getConnection();
//             Statement statement = connection.createStatement();
//             ResultSet resultSet = statement.executeQuery(sql)) {
//
//            while (resultSet.next()) {
//                int questionId = resultSet.getInt("question_id");
//                String typeString = resultSet.getString("question_type");
//                String startText = resultSet.getString("start_text");
//                int punkte = resultSet.getInt("points");
//                String themaString = resultSet.getString("thema_name");
//
//                Themenbereich themenbereich = Themenbereich.valueOf(themaString);
//
//                Fragenkategorie fragenkategorie = mapKategorie(typeString);
//
//                List<Antwort> antworten = ladeAntwortenFuerFrage(questionId, typeString, connection);
//
//                Frage neueFrage = new Frage(questionId, themenbereich, fragenkategorie, startText, antworten, punkte);
//
//                fragen.add(neueFrage);
//            }
//        } catch (Exception e) {
//            System.err.println("Fehler beim Initialisieren der Datenbank-Fragen: " + e.getMessage());
//        }
//    }

    private static void initialisiereFragen(List<Frage> fragen, String spielerName) {
        // Wir nutzen einen LEFT JOIN auf player_progress für den spezifischen Spieler
        String sql = "SELECT q.question_id, q.question_type, q.start_text, q.points, t.name AS thema_name, " +
                "pp.question_id IS NOT NULL AS bereits_geloest " +
                "FROM question q " +
                "JOIN Question_Theme qt ON q.question_id = qt.question_id " +
                "JOIN theme t ON qt.theme_id = t.theme_id " +
                "LEFT JOIN player_progress pp ON q.question_id = pp.question_id " +
                "AND pp.player_id = (SELECT player_id FROM player WHERE name = ?) " +
                "ORDER BY RAND()";

        try (Connection connection = DatabaseController.getConnection();
             PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, spielerName);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {

                int questionId = resultSet.getInt("question_id");
                String typeString = resultSet.getString("question_type");
                String startText = resultSet.getString("start_text");
                int punkte = resultSet.getInt("points");
                String themaString = resultSet.getString("thema_name");

                Themenbereich themenbereich = Themenbereich.valueOf(themaString);

                Fragenkategorie fragenkategorie = mapKategorie(typeString);

                List<Antwort> antworten = ladeAntwortenFuerFrage(questionId, typeString, connection);

                // ... (dein restlicher Code zum Auslesen)
                boolean geloest = resultSet.getBoolean("bereits_geloest");

                Frage neueFrage = new Frage(questionId, themenbereich, fragenkategorie, startText, antworten, punkte);

                // WICHTIG: Setze den Status der Frage basierend auf der DB
                if (geloest) {
                    neueFrage.setGeloest();
                }

                fragen.add(neueFrage);
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Initialisieren: " + e.getMessage());
        }
    }

    public static void ladeFragenFuerSpieler(String name) {
        alleFragen = new ArrayList<>();
        initialisiereFragen(alleFragen, name);
    }

    /**
     * Lädt alle Antworten zu einer Frage aus der Datenbank.
     * Je nach Frage-Typ (GAP oder MC) werden unterschiedliche Tabellen abgefragt.
     *
     * @param questionId ID der Frage
     * @param typeString Fragetyp aus der DB
     * @param connection aktive DB-Verbindung
     * @return Liste der geladenen Antworten
     * @throws SQLException wenn ein DB-Fehler auftritt
     */
    private static List<Antwort> ladeAntwortenFuerFrage(int questionId, String typeString, Connection connection) throws SQLException {
        List<Antwort> antwortListe = new ArrayList<>();
        String sql;

        if (typeString.equals("GAP")) {
            sql = "SELECT correct_text FROM gap_field WHERE question_id = ? ORDER BY gap_index ASC";
        } else {
            sql = "SELECT option_text, is_correct FROM mc_answer WHERE question_id = ? ORDER BY option_order ASC";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, questionId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                if (typeString.equals("GAP")) {
                    antwortListe.add(new Antwort(true, resultSet.getString("correct_text")));
                } else {
                    antwortListe.add(new Antwort(resultSet.getBoolean("is_correct"), resultSet.getString("option_text")));
                }
            }
        }
        return antwortListe;
    }

    /**
     * Wandelt den in der Datenbank gespeicherten Fragetyp in die passende Enum-Kategorie um.
     *
     * @param datenbankTyp Fragetyp als String (z. B. "MC")
     * @return passende Fragenkategorie
     * @throws IllegalArgumentException bei unbekanntem Typ
     */
    private static Fragenkategorie mapKategorie(String datenbankTyp) {
        return switch (datenbankTyp) {
            case "MC" -> Fragenkategorie.MULTIPLE_CHOICE;
            case "TF" -> Fragenkategorie.WAHR_FALSCH;
            case "GAP" -> Fragenkategorie.LUECKENTEXT;
            default -> throw new IllegalArgumentException("Unbekannter Fragentyp in DB: " + datenbankTyp);
        };
    }
}