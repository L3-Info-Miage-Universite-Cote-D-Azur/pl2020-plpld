package metier;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ModelTest {

    private Model model1;
    private Model model2;
    private Model model3;

    @Before
    public void setup() {

        model1 = new Model("Model de test 1");
        model2 = new Model("Model de test 2");
        model3 = new Model("Model de test 3");
    }


    /**
     * On vérifie la méthode "getText".
     */
    @Test
    public void stringTest() {
        assertEquals(model1.getText(), "Model de test 1");
        assertEquals(model2.getText(), "Model de test 2");
        assertEquals(model3.getText(), "Model de test 3");

        assertEquals(model1.isSelected(), false);

        model1.setSelected(true);
        assertEquals(model1.isSelected(), true);
    }


    /**
     * On vérifie les méthodes "isSelected" et "setSelected".
     */
    @Test
    public void isSelectedTest() {
        assertEquals(model1.isSelected(), false);
        assertEquals(model2.isSelected(), false);
        assertEquals(model3.isSelected(), false);

        model1.setSelected(true);
        model2.setSelected(true);
        model3.setSelected(true);

        assertEquals(model1.isSelected(), true);
        assertEquals(model2.isSelected(), true);
        assertEquals(model3.isSelected(), true);
    }


    /**
     * On vérifie la méthode "toJSON" qui transforme un model de text en objet JSON.
     */
    @Test
    public void toJSONTest() {
        assertEquals(model1.toJSON().toString(), "{\"nom\":\"Model de test 1\"}");
        assertEquals(model2.toJSON().toString(), "{\"nom\":\"Model de test 2\"}");
        assertEquals(model3.toJSON().toString(), "{\"nom\":\"Model de test 3\"}");
    }
}
