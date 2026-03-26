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
        s.setFortschrittDATENBANK(80);
        s.setGesamtFortschritt();
        s.setLevel();
        assertEquals(Level.BRONZE, s.getLevel());
        s.setFortschrittPSEUDOCODE(200); // 1.0
        s.setGesamtFortschritt();
        s.setLevel();
        assertEquals(Level.MASTER, s.getLevel());
    }

    @Test
    public void testSetGesamtFortschritt() {
        Spieler s = new Spieler("Max");
        s.setPunktekonto(50);
        s.setGesamtFortschritt();
        assertEquals(0.25, s.getGesamtFortschritt(),0.25);
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
            assertEquals(0.1, s.getGesamtFortschritt(), 0.1);
            assertEquals(Level.ANFÄNGER, s.getLevel());
            assertEquals(0.5, s.getFortschrittSQL(), 0.5);
        }
    }

}












