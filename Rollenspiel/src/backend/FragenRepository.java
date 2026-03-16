package backend;

import enums.Fragenkategorie;
import enums.Themenbereich;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FragenRepository {

    public static List<Frage> alleFragen = null;

    public static List<Frage> getAlleFragen() {
        if (alleFragen == null) {
            alleFragen = new ArrayList<>();
            initialisiereFragen(alleFragen);
        }
        return alleFragen;
    }

    public static List<Frage> getUngeloesteFragen(Themenbereich thema) {
        return getAlleFragen().stream()
                .filter(f -> f.getThemenbereich() == thema)
                .filter(f -> !f.isGeloest())
                .toList();
    }

    public static double berechneFortschrittFuerThema(Themenbereich thema) {
        List<Frage> alleThemenFragen = getAlleFragen().stream()
                .filter(f -> f.getThemenbereich() == thema)
                .toList();

        if (alleThemenFragen.isEmpty()) return 0.0;

        long beantwortet = alleThemenFragen.stream().filter(Frage::isGeloest).count();
        return (double) beantwortet / alleThemenFragen.size();
    }


    private static void initialisiereFragen(List<Frage> fragen) {
        String sql = "SELECT q.question_id, q.question_type, q.start_text, q.points, t.name AS thema_name " +
                "FROM question q " +
                "JOIN Question_Theme qt ON q.question_id = qt.question_id " +
                "JOIN theme t ON qt.theme_id = t.theme_id " +
                "ORDER BY RAND()";

        try (Connection connection = DatabaseController.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                int questionId = resultSet.getInt("question_id");
                String typeString = resultSet.getString("question_type");
                String startText = resultSet.getString("start_text");
                int punkte = resultSet.getInt("points");
                String themaString = resultSet.getString("thema_name");

                Themenbereich themenbereich = Themenbereich.valueOf(themaString);

                Fragenkategorie fragenkategorie = mapKategorie(typeString);

                List<Antwort> antworten = ladeAntwortenFuerFrage(questionId, typeString, connection);

                Frage neueFrage = new Frage(questionId, themenbereich, fragenkategorie, startText, antworten, punkte);

                fragen.add(neueFrage);
            }
        } catch (Exception e) {
            System.err.println("Fehler beim Initialisieren der Datenbank-Fragen: " + e.getMessage());
        }
    }

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

    private static Fragenkategorie mapKategorie(String datenbankTyp) {
        return switch (datenbankTyp) {
            case "MC" -> Fragenkategorie.MULTIPLE_CHOICE;
            case "TF" -> Fragenkategorie.WAHR_FALSCH;
            case "GAP" -> Fragenkategorie.LUECKENTEXT;
            default -> throw new IllegalArgumentException("Unbekannter Fragentyp in DB: " + datenbankTyp);
        };
    }
}
