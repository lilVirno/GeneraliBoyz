package tests;
import backend.Frage;
import backend.FragenRepository;
import enums.Themenbereich;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class FragenRepositoryTest {

    @BeforeEach
    void resetMockDaten() {
        // sorgt dafür, dass getAlleFragen() die Liste neu initialisiert
        FragenRepository.alleFragen = null;
    }

    // Falls die Datenbank da ist und der Zugriff auf die Mockdaten nicht nötig ist
    @Test
    void getAlleFragen_sollImmerDieselbeListeZurueckgeben() {

        List<Frage> fragen1 = FragenRepository.getAlleFragen();
        List<Frage> fragen2 = FragenRepository.getAlleFragen();
        assertSame(fragen1, fragen2);
    }

    @Test
    void getAlleFragen_sollListeMitMockDatenZurueckgeben() {
        // alleFragen == null → initialisiereMockDaten wird aufgerufen
        List<Frage> fragen = FragenRepository.getAlleFragen();
        // Prüfen, dass die Liste nicht null ist
        assertNotNull(fragen);
        // Prüfen, dass initialisiereMockDaten etwas hinzugefügt hat
        assertFalse(fragen.isEmpty());
    }
    @Test
    void getUngeloesteFragen_sollNurUngelosteZurueckgeben() {
        List<Frage> alleFragen = FragenRepository.getAlleFragen();

        alleFragen.get(0).setGeloest();
        alleFragen.get(1).setGeloest();

        List<Frage> ungeloste = FragenRepository.getUngeloesteFragen(Themenbereich.SQL);

        assertTrue(ungeloste.stream().noneMatch(Frage::isGeloest));
        int expected = alleFragen.size() - 2;
        assertEquals(expected, ungeloste.size());
        assertTrue(ungeloste.stream().allMatch(f -> f.getThemenbereich() == Themenbereich.SQL));
    }
    @Test
    void berechneFortschrittFuerThema_sollNullZurueckgebenWennKeineFragen() {
        // Angenommen, wir nehmen ein Thema, das nicht existiert
        double fortschritt = FragenRepository.berechneFortschrittFuerThema(Themenbereich.KEINTHEMA);

        assertEquals(0.0, fortschritt);
    }
    @Test
    void berechneFortschrittFuerThema_sollKorrekteQuoteZurueckgeben() {

        List<Frage> fragen = FragenRepository.getAlleFragen();

        // Markiere 3 Fragen als gelöst
        fragen.get(0).setGeloest();
        fragen.get(1).setGeloest();
        fragen.get(2).setGeloest();

        double fortschritt = FragenRepository.berechneFortschrittFuerThema(Themenbereich.SQL);

        int alle = (int) fragen.stream().filter(f -> f.getThemenbereich() == Themenbereich.SQL).count();

        double expected = 3.0 / alle;
        assertEquals(expected, fortschritt);
    }
    @Test
    void berechneFortschrittFuerThema_soll1ZurueckgebenWennAlleGeloest() {

        List<Frage> fragen = FragenRepository.getAlleFragen();

        // Alle Fragen des Themas SQL als gelöst markieren
        fragen.stream()
                .filter(f -> f.getThemenbereich() == Themenbereich.SQL)
                .forEach(Frage::setGeloest);

        double fortschritt = FragenRepository.berechneFortschrittFuerThema(Themenbereich.SQL);

        assertEquals(1.0, fortschritt);
    }

}
