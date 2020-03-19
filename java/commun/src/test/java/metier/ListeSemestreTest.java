package metier;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

public class ListeSemestreTest {

    ListeSemestre listeSemestre;
    ListeSemestre listeElectronique;

    @Before
    public void setup() {

        listeSemestre = new ListeSemestre() {{
            add("Informatique", new ArrayList<String>() {{
                add("Bases de l'informatique");
                add("Introduction à l'informatique par le web");
                add("Structures de données et programmation C");
                add("Bases de donnée");
                add("Outils formels pour l'informatique");
            }});
            add("Mathématiques", new ArrayList<String>() {{
                add("Fondements 1");
                add("Méthodes : approche continue");
                add("Complements 1");
                add("Fondements 3");
                add("Compléments d'Analyse");
                add("Méthodes : Mathématiques et ingénierie");
            }});
        }};

        listeElectronique = new ListeSemestre() {{
           add("Electronique", new ArrayList<String>() {{
               add("Electronique numerique - Bases");
               add("Automatique : une introduction");
               add("Système embarqué");
               add("Physique des capteurs");
           }});
        }};
    }


    @Test
    public void test() {
        // On vérifient que le HashMap est bien initialisé
        assertEquals(listeSemestre.getMapUE().get("Informatique").get(0), "Bases de l'informatique");
        assertEquals(listeSemestre.getMapUE().get("Informatique").get(1), "Introduction à l'informatique par le web");
        assertEquals(listeSemestre.getMapUE().get("Informatique").get(2), "Structures de données et programmation C");
        assertEquals(listeSemestre.getMapUE().get("Informatique").get(3), "Bases de donnée");
        assertEquals(listeSemestre.getMapUE().get("Informatique").get(4), "Outils formels pour l'informatique");

        assertEquals(listeSemestre.getMapUE().get("Mathématiques").get(0), "Fondements 1");
        assertEquals(listeSemestre.getMapUE().get("Mathématiques").get(1), "Méthodes : approche continue");
        assertEquals(listeSemestre.getMapUE().get("Mathématiques").get(2), "Complements 1");
        assertEquals(listeSemestre.getMapUE().get("Mathématiques").get(3), "Fondements 3");
        assertEquals(listeSemestre.getMapUE().get("Mathématiques").get(4), "Compléments d'Analyse");
        assertEquals(listeSemestre.getMapUE().get("Mathématiques").get(5), "Méthodes : Mathématiques et ingénierie");

        // On ajoute un autre HashMap, puis on revérifient
        listeSemestre.setMapUE(listeElectronique.getMapUE());
        assertEquals(listeSemestre.getMapUE().get("Electronique").get(0), "Electronique numerique - Bases");
        assertEquals(listeSemestre.getMapUE().get("Electronique").get(1), "Automatique : une introduction");
        assertEquals(listeSemestre.getMapUE().get("Electronique").get(2), "Système embarqué");
        assertEquals(listeSemestre.getMapUE().get("Electronique").get(3), "Physique des capteurs");
    }
}
