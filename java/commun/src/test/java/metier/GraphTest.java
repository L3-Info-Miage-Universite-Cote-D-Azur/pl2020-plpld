package metier;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GraphTest {

    Graphe graphe;
    Map<String, List<String>> prerequis;
    Map<String, List<String>> graph;
    List<String> ueMaths;
    @Before
    public void setup(){
        prerequis= new HashMap<>();
        graph= new HashMap<>();
        ueMaths=new ArrayList<>();

        ueMaths.add("Algèbre linéaire");
        ueMaths.add("Équation différentielle");

        prerequis.put("Informatique",new ArrayList<>());
        prerequis.put("Mathématique",ueMaths);
        graphe=new Graphe(prerequis);

    }

    @Test
    public void creationSommetEtcreationAretes() {
        //creationSommet modifications
        List<String> origine = new ArrayList<>();
        origine.add("Informatique");
        graph.put("Origine",origine);

        //creationAretes modifications
        List<String> aretes = new ArrayList<>();
        aretes.addAll(origine);
        graph.put("Informatique",aretes);
        graph.put("Mathématique",origine);

        aretes=new ArrayList<>();
        aretes.add("Mathématique");
        graph.put("Équation différentielle",aretes);
        graph.put("Algèbre linéaire",aretes);

        assertEquals(graphe.getGraphe(),graph);
    }


}
