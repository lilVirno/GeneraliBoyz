package tests;

import backend.Antwort;
import backend.Frage;
import backend.FragenController;
import enums.Themenbereich;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static enums.Fragenkategorie.MULTIPLE_CHOICE;
import static enums.Fragenkategorie.WAHR_FALSCH;
import static enums.Themenbereich.SQL;
import static org.junit.jupiter.api.Assertions.*;

public class FragenControllerTest {
    private FragenController controller;
    private Frage f1;
    private Frage f2;

    @BeforeEach
    void setUp() {
        // Wir erstellen Test-Daten
        f1 = new Frage(5, SQL, WAHR_FALSCH, "Wähle eine Antwort aus",List.of(new Antwort(true,"Das ist Korrekt"), new Antwort(false,"Das ist Flasch")),3);
        f2 = new Frage(8, SQL, MULTIPLE_CHOICE, "Wähle eine Antwort aus",List.of(new Antwort(true,"Das ist falsch"), new Antwort(false,"Das ist Korrekt")),5);
        List<Frage> liste = Arrays.asList(f1, f2);

        // Initialisiere den Controller vor jedem Test neu..... Außerdem eine liste von den Fragen erstellt
        controller = new FragenController(liste);
    }

    @Test
    void testGetAktuelleFrage_StartetMitErsterFrage() {
        assertEquals(f1, controller.getAktuelleFrage(), "Sollte initial die erste Frage zurückgeben.");
    }

    @Test
    void testNaechsteFrage_WechseltZurNaechsten() {
        controller.naechsteFrage();
        assertEquals(f2, controller.getAktuelleFrage(), "Nach naechsteFrage() sollte die zweite Frage erscheinen.");
    }

    @Test
    void testHatNaechsteFrage() {
        assertTrue(controller.hatNaechsteFrage(), "Sollte true sein, wenn noch Fragen folgen.");
        controller.naechsteFrage();
        assertFalse(controller.hatNaechsteFrage(), "Sollte false sein, wenn man bei der letzten Frage ist.");
    }

    @Test
    void testNaechsteFrage_IndexOutOfBounds() {
        controller.naechsteFrage(); // Index ist jetzt 1 (letzte Frage)

        // Was passiert, wenn wir über das Ziel hinausschießen?
        // Das provoziert aktuell eine IndexOutOfBoundsException
        assertThrows(IndexOutOfBoundsException.class, () -> {
            controller.naechsteFrage();
            controller.getAktuelleFrage();
        });
    }

}
