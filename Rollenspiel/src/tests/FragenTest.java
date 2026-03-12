package tests;

import backend.Antwort;
import backend.Frage;
import enums.Fragenkategorie;
import enums.Themenbereich;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FragenTest {

    @Test
    void testErmittleKategorieLueckentext() {
        List<Antwort> antworten = List.of(
                new Antwort(true, "Wahr")
        );
        Frage frage = new Frage(Themenbereich.SQL,"Welche Fragenkateforie möchtest du haben?", antworten,1);
        assertEquals(Fragenkategorie.LUECKENTEXT, frage.kategorie());

    }
    @org.junit.jupiter.api.Test
    void testErmittleKategorieWahrFalsch() {
        List<Antwort> antworten = List.of(
                new Antwort(true, "Wahr"),
                new Antwort(true, "falsch")
        );

        Frage frage = new Frage(Themenbereich.SQL, "Welche Fragenkateforie möchtest du haben?", antworten, 10);

        assertEquals(Fragenkategorie.WAHR_FALSCH, frage.kategorie());
    }

    @org.junit.jupiter.api.Test
    void testErmittleKategorieMultipleChoice() {
        List<Antwort> antworten = List.of(
                new Antwort(true, "Wahr"),
                new Antwort(false, "falsch"),
                new Antwort(false, "falsch"),
                new Antwort(false, "falsch")
        );
        Frage frage = new Frage(Themenbereich.SQL, "Welche Fragenkateforie möchtest du haben?", antworten, 10);

        assertEquals(Fragenkategorie.MULTIPLE_CHOICE, frage.kategorie());
    }


}
