package backend;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;
import java.io.File;

public class DatabaseController {
    private static final String DB_PATH = "./Rollenspiel/db/lernspiel";
    private static final String URL = "jdbc:h2:" + DB_PATH + ";MODE=MySQL;DATABASE_TO_UPPER=FALSE";
    private static final String USER = "sa";
    private static final String PASS = "";

    public static Connection getConnection() throws Exception {
        try {
            // Zwingt Java, die H2-Treiberklasse zu laden
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new Exception("H2 Treiber JAR nicht gefunden! Prüfe den libs Ordner.");
        }
        return DriverManager.getConnection(URL, USER, PASS);
    }

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
