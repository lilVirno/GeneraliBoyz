package tests;

import backend.Antwort;
import backend.Frage;
import enums.Fragenkategorie;
import enums.Themenbereich;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class FrageTest {

    @Test
    void testGetKorrekteAntworten() {
        // Vorbereitung: Antworten erstellen
        Antwort a1 = new Antwort(true, "Richtig 1");
        Antwort a2 = new Antwort(false, "Falsch 1");
        Antwort a3 = new Antwort(true, "Richtig 2");

        Frage frage = new Frage(1, Themenbereich.SQL, Fragenkategorie.MULTIPLE_CHOICE,
                "Test?", List.of(a1, a2, a3), 10);

        // Ausführung
        List<String> korrekte = frage.getKorrekteAntworten();

        // Überprüfung
        Assertions.assertEquals(2, korrekte.size(), "Es sollten 2 korrekte Antworten sein");
        Assertions.assertTrue(korrekte.contains("Richtig 1"));
        Assertions.assertTrue(korrekte.contains("Richtig 2"));
        Assertions.assertFalse(korrekte.contains("Falsch 1"));
    }

    @Test
    void testSetGeloest() {
        Frage frage = new Frage();
        Assertions.assertFalse(frage.isGeloest(), "Initial sollte die Frage nicht gelöst sein");

        frage.setGeloest();
        Assertions.assertTrue(frage.isGeloest(), "Nach setGeloest() muss isGeloest() true zurückgeben");
    }

    @Test
    void testCountOccurrences() {
        // Test der statischen Hilfsmethode
        String text = "Java ist toll, Java ist super, Java!";
        String token = "Java";

        int result = Frage.countOccurrences(text, token);

        Assertions.assertEquals(3, result, "Java sollte 3-mal vorkommen");
        Assertions.assertEquals(0, Frage.countOccurrences(text, "Python"), "Python sollte 0-mal vorkommen");
    }

    @Test
    void testConstructorAndGetters() {
        // Testet, ob die Daten korrekt im Objekt ankommen
        Frage frage = new Frage(42, Themenbereich.SQL, Fragenkategorie.LUECKENTEXT, "Was ist SQL?", null, 20);

        Assertions.assertEquals(42, frage.getPunkte() == 20 ? 42 : 0); // Kleiner Check
        Assertions.assertEquals(Themenbereich.SQL, frage.getThemenbereich());
        Assertions.assertEquals("Was ist SQL?", frage.getFrage());
        Assertions.assertEquals(20, frage.getPunkte());
    }
}