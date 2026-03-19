package tests;

import backend.Antwort;
import backend.Frage;
import backend.Spieler;
import gui.LueckentextGUI;
import gui.LueckentextPruefer;
import gui.Startbildschirm;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.timeout;

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
