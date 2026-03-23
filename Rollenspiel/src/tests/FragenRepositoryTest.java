package tests;
import backend.Antwort;
import backend.Frage;
import backend.FragenRepository;
import enums.Fragenkategorie;
import enums.Themenbereich;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class FragenRepositoryTest {

    @Test
    void getAlleFragen_LädtFragenNurEinmal() {
        // Act
        List<Frage> firstCall = FragenRepository.getAlleFragen();
        List<Frage> secondCall = FragenRepository.getAlleFragen();

        // Assert
        assertSame(firstCall, secondCall, "Beim zweiten Aufruf darf die Liste nicht neu geladen werden.");
    }

    @Test
    void getUngeloesteFragen_FiltertNurUngelösteFragen() {
        // Arrange
        Frage f1 = new Frage(1, Themenbereich.SQL, Fragenkategorie.MULTIPLE_CHOICE, "x?", List.of(), 1);

        Frage f2 = new Frage(2, Themenbereich.SQL, Fragenkategorie.MULTIPLE_CHOICE, "y?", List.of(), 1);
        f2.setGeloest();

        FragenRepository.alleFragen = List.of(f1, f2);

        // Act
        List<Frage> result = FragenRepository.getUngeloesteFragen(Themenbereich.SQL);

        // Assert
        assertEquals(1, result.size());
        assertTrue(result.contains(f1));
        assertFalse(result.contains(f2));
    }
    @Test
    void berechneFortschrittFuerThema_BerechnetKorrekt() {
        // Arrange
        Frage f1 = new Frage(1, Themenbereich.SQL, Fragenkategorie.MULTIPLE_CHOICE, "A?", List.of(), 1);
        Frage f2 = new Frage(2, Themenbereich.SQL, Fragenkategorie.MULTIPLE_CHOICE, "B?", List.of(), 1);

        f1.setGeloest();


        FragenRepository.alleFragen = List.of(f1, f2);

        // Act
        double fortschritt = FragenRepository.berechneFortschrittFuerThema(Themenbereich.SQL);

        // Assert
        assertEquals(0.5, fortschritt);
    }
    @Test
    void mapKategorie_MappedKorrekt() throws Exception {
        Method method = FragenRepository.class.getDeclaredMethod("mapKategorie", String.class);
        method.setAccessible(true);

        Fragenkategorie result = (Fragenkategorie) method.invoke(null, "MC");

        assertEquals(Fragenkategorie.MULTIPLE_CHOICE, result);
    }
    @Test
    void ladeAntwortenFuerFrage_LädtMultipleChoiceAntworten() throws Exception {
        // Arrange
        Connection conn = mock(Connection.class);
        PreparedStatement stmt = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);

        when(rs.next()).thenReturn(true, false);
        when(rs.getString("option_text")).thenReturn("Test Option");
        when(rs.getBoolean("is_correct")).thenReturn(true);

        Method m = FragenRepository.class.getDeclaredMethod(
                "ladeAntwortenFuerFrage", int.class, String.class, Connection.class);
        m.setAccessible(true);

        // Act
        List<Antwort> antworten = (List<Antwort>) m.invoke(null, 1, "MC", conn);

        // Assert
        assertEquals(1, antworten.size());
        assertEquals("Test Option", antworten.get(0).getAntwort());
        assertTrue(antworten.get(0).isRichtig());
    }

}
