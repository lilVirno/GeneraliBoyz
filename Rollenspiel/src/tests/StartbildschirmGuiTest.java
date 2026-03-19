package tests;

import backend.Frage;
import backend.FragenRepository;
import enums.Themenbereich;
import gui.Startbildschirm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;


import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class StartbildschirmGuiTest {

    @Test
    void testLadeFragenUndOeffneMitMockito() {
        try (MockedStatic<FragenRepository> mock = Mockito.mockStatic(FragenRepository.class)) {
            Themenbereich thema = Themenbereich.SQL;
            List<Frage> fragen = List.of(new Frage());
            fragen.getFirst().setFrage("Testfrage");
            mock.when(() -> FragenRepository.getUngeloesteFragen(thema))
                    .thenReturn(fragen);

            Startbildschirm screen = new Startbildschirm();
            screen.LadeFragenUndOeffneExtraFueTest();

            Assertions.assertNotNull(screen.getFragenController());
            Assertions.assertEquals("Testfrage", screen.getFragenController().getAktuelleFrage().getFrage());
        }
    }


}
