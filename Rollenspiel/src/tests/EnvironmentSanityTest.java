package tests;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EnvironmentSanityTest {
    @Test
    void mockitoCore_FunktioniertOhneInline() {
        @SuppressWarnings("unchecked")
        List<String> list = mock(List.class);
        when(list.size()).thenReturn(42);
        assertEquals(42, list.size());
    }
}
