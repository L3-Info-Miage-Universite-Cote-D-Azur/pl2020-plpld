package metier;

import static org.junit.Assert.*;
import com.fasterxml.jackson.core.JsonParser;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.time.LocalDate;

public class LocalDateDeserializerTest {

    private LocalDateDeserializer localDateDeserializer;
    private JsonParser jsonParser;
    private LocalDate localDate;


    @Before
    public void setup() {
        localDateDeserializer = Mockito.mock(LocalDateDeserializer.class);
        jsonParser = Mockito.mock(JsonParser.class);
    }


    /**
     * On vérifie que la méthode "deserialize" de la classe LocalDateDeserializer renvoie bien un LocalDate.
     * @throws IOException
     */
    @Test
    public void deserializerTest() throws IOException {
        // On test avec plusieurs valeurs
        Mockito.when(localDateDeserializer.deserialize(jsonParser, null)).thenReturn(LocalDate.of(1999, 04, 30));
        localDate = localDateDeserializer.deserialize(jsonParser, null);
        assertEquals(localDate, LocalDate.of(1999, 04, 30));

        Mockito.when(localDateDeserializer.deserialize(jsonParser, null)).thenReturn(LocalDate.of(1800, 12, 12));
        localDate = localDateDeserializer.deserialize(jsonParser, null);
        assertEquals(localDate, LocalDate.of(1800, 12, 12));

        Mockito.when(localDateDeserializer.deserialize(jsonParser, null)).thenReturn(LocalDate.of(3000, 01, 23));
        localDate = localDateDeserializer.deserialize(jsonParser, null);
        assertEquals(localDate, LocalDate.of(3000, 01, 23));

        Mockito.when(localDateDeserializer.deserialize(jsonParser, null)).thenReturn(LocalDate.of(2020, 05, 31));
        localDate = localDateDeserializer.deserialize(jsonParser, null);
        assertEquals(localDate, LocalDate.of(2020, 05, 31));

        Mockito.when(localDateDeserializer.deserialize(jsonParser, null)).thenReturn(LocalDate.of(1789, 07, 14));
        localDate = localDateDeserializer.deserialize(jsonParser, null);
        assertEquals(localDate, LocalDate.of(1789, 07, 14));
    }
}
