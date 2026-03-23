package tests;

import gui.LueckentextPruefer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class LueckentextGuiTest {

    @Test
    @DisplayName("Alle korrekt – mit Trim und Case-Insensitivity")
    void alleKorrekt_mitTrimUndCase() {
        List<String> eingaben = List.of("  frankreich ", "  SEINE");
        List<String> korrekt  = List.of("Frankreich", "Seine");

        assertTrue("Trim + equalsIgnoreCase sollten als korrekt gewertet werden.",
                LueckentextPruefer.alleRichtig(eingaben, korrekt));
    }

    @Test
    @DisplayName("Mindestens eine falsche Antwort → false")
    void eineFalsch() {
        List<String> eingaben = List.of("Frankreich", "Donau"); // 2. ist falsch
        List<String> korrekt  = List.of("Frankreich", "Seine");

        assertFalse(LueckentextPruefer.alleRichtig(eingaben, korrekt),
                "Bei mindestens einer falschen Antwort muss false zurückkommen.");
    }

    @Test
    @DisplayName("Ungleiche Länge → false")
    void ungleicheLaenge() {
        List<String> eingaben = List.of("Frankreich", "Seine");
        List<String> korrekt  = List.of("Frankreich");

        assertFalse(LueckentextPruefer.alleRichtig(eingaben, korrekt),
                "Ungleiche Listengrößen dürfen nicht als korrekt gelten.");
    }


    @Test
    @DisplayName("Null-Listen → false")
    void nullListen() {
        assertFalse(LueckentextPruefer.alleRichtig(null, List.of("A")),
                "Null bei Eingaben muss false liefern.");
        assertFalse(LueckentextPruefer.alleRichtig(List.of("A"), null),
                "Null bei korrekten Antworten muss false liefern.");
    }


    @Test
    @DisplayName("Leere Strings – korrekt, wenn beide Seiten leer sind")
    void leereStringsKorrektWennBeideLeer() {
        List<String> eingaben = List.of("", "  ");
        List<String> korrekt  = List.of("", "");

        assertTrue("Leere Strings auf beiden Seiten gelten als korrekt.",
                LueckentextPruefer.alleRichtig(eingaben, korrekt));
    }




}
