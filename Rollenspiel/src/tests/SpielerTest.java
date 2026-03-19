package tests;

import backend.Frage;
import backend.FragenRepository;
import backend.Spieler;
import enums.Level;
import enums.Themenbereich;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;



public class SpielerTest {


    @Test
    public void testSetLevel() {
        Spieler s = new Spieler("Max");
        s.setPunktekonto(0); // Fortschritt = 0
        s.setGesamtFortschritt();
        s.setLevel();
        assertEquals(Level.ANFÄNGER, s.getLevel());
        s.setPunktekonto(80); // 80/200 = 0.4
        s.setGesamtFortschritt();
        s.setLevel();
        assertEquals(Level.BRONZE, s.getLevel());
        s.setPunktekonto(200); // 1.0
        s.setGesamtFortschritt();
        s.setLevel();
        assertEquals(Level.MASTER, s.getLevel());
    }

    @Test
    public void testSetGesamtFortschritt() {
        Spieler s = new Spieler("Max");
        s.setPunktekonto(50);
        s.setGesamtFortschritt();
        assertEquals(0.25, s.getGesamtFortschritt(), 0.0001);
    }

    @Test
    public void testAktualisiereThemenFortschritt() {
        Spieler s = new Spieler("Max");
        // Frage als Mock – kein Konstruktor nötig
        Frage frage = mock(Frage.class);
        when(frage.getPunkte()).thenReturn(10);
        when(frage.getThemenbereich()).thenReturn(Themenbereich.SQL);
        try (MockedStatic<FragenRepository> mocked = mockStatic(FragenRepository.class)) {
            mocked.when(() -> FragenRepository.berechneFortschrittFuerThema(Themenbereich.SQL))
                    .thenReturn(0.75);
            s.addPunkte(frage);
            // verifiziert Logik: Fortschritt für SQL übernommen
            assertEquals(0.75, s.getFortschrittSQL(), 1e-4);
            // optional: Interaktion prüfen
            verify(frage).getPunkte();
            verify(frage).getThemenbereich();
        }
    }

    @Test
    public void testSetMedallienArray() {
        Spieler s = new Spieler("Max");
        s.setFortschrittSQL(1.0);
        s.setFortschrittUML(0.5);
        s.setGesamtFortschritt(); // nicht 1 → keine Endboss‑Medaille
        s.setMedallienArray();
        assertTrue(s.getMedallien().contains("Rollenspiel/src/resources/Medaille_SQL_T.png"));
        assertFalse(s.getMedallien().contains("Rollenspiel/src/resources/EndbossMedallie.jpg"));
    }

    @Test
    public void testAddPunkte() {
        Spieler s = new Spieler("Max");
        Frage frage = mock(Frage.class);
        when(frage.getPunkte()).thenReturn(20);
        when(frage.getThemenbereich()).thenReturn(Themenbereich.SQL);
        try (MockedStatic<FragenRepository> mocked = mockStatic(FragenRepository.class)) {
            mocked.when(() -> FragenRepository.berechneFortschrittFuerThema(Themenbereich.SQL))
                    .thenReturn(0.5);
            s.addPunkte(frage);
            assertEquals(20, s.getPunktekonto());
            assertEquals(0.1, s.getGesamtFortschritt(), 0.0001);
            assertEquals(Level.ANFÄNGER, s.getLevel());
            assertEquals(0.5, s.getFortschrittSQL(), 0.0001);
        }
    }

}












