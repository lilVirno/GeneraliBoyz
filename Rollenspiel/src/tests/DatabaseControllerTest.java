package tests;

import backend.DatabaseController;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.sql.Connection;

class DatabaseControllerTest {

    @Test
    void testGetConnection() {
        // Prüft, ob der Treiber geladen werden kann und die URL gültig ist
        try (Connection conn = DatabaseController.getConnection()) {
            Assertions.assertNotNull(conn, "Verbindung sollte nicht null sein");
            Assertions.assertFalse(conn.isClosed(), "Verbindung sollte offen sein");
        } catch (Exception e) {
            Assertions.fail("Verbindung zur Datenbank fehlgeschlagen: " + e.getMessage());
        }
    }

    @Test
    void testSchemaResourceExists() {
        // Einer der häufigsten Fehler: Datei liegt im falschen Ordner
        // Wir prüfen, ob der Controller die Datei überhaupt finden würde
        InputStream is = DatabaseController.class.getResourceAsStream("/resources/schema.sql");

        Assertions.assertNotNull(is, "Die Datei 'schema.sql' wurde nicht unter /resources/ gefunden!");
    }
}
