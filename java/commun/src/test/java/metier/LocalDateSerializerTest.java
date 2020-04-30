package metier;

import com.fasterxml.jackson.core.JsonGenerator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.IOException;
import java.time.LocalDate;

public class LocalDateSerializerTest {

    private LocalDateSerializer localDateSerializer;
    private JsonGenerator jsonGenerator;


    @Before
    public void setup() {
        localDateSerializer = new LocalDateSerializer();
        jsonGenerator = Mockito.mock(JsonGenerator.class);
    }


    /**
     * On vérifie si la méthode "writeString" de la classe JsonGenerator s'effectue bien avec le bon paramètre
     * lors de l'apelle de la méthode "serialize" de la classe LocalDateSerializer.
     * @throws IOException
     */
    @Test
    public void serializerTest() throws IOException {
        // On test avec plusieurs valeurs.
        localDateSerializer.serialize(LocalDate.of(1999, 04, 30), jsonGenerator, null);
        Mockito.verify(jsonGenerator).writeString("1999-04-30");

        localDateSerializer.serialize(LocalDate.of(1800, 12, 12), jsonGenerator, null);
        Mockito.verify(jsonGenerator).writeString("1800-12-12");

        localDateSerializer.serialize(LocalDate.of(3000, 01, 23), jsonGenerator, null);
        Mockito.verify(jsonGenerator).writeString("3000-01-23");

        localDateSerializer.serialize(LocalDate.of(2020, 05, 31), jsonGenerator, null);
        Mockito.verify(jsonGenerator).writeString("2020-05-31");

        localDateSerializer.serialize(LocalDate.of(1789, 07, 14), jsonGenerator, null);
        Mockito.verify(jsonGenerator).writeString("1789-07-14");
    }
}
