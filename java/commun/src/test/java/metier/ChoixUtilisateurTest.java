package metier;

import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;

public class ChoixUtilisateurTest {

    private ChoixUtilisateur choixMath;
    private ChoixUtilisateur choixInfo;
    private ChoixUtilisateur choixPhysique;
    private ChoixUtilisateur choixChimie;
    private ChoixUtilisateur choixListe;

    private String matiereMath;
    private String matiereInfo;
    private String matierePhysique;
    private String matiereChimie;

    private ArrayList<String> liteMatiere;

    @Before
    public void setup() {
        //Initialisation
        choixMath = new ChoixUtilisateur("Mathématiques");
        choixInfo = new ChoixUtilisateur("Informatique");
        choixPhysique = new ChoixUtilisateur("Physique");
        choixChimie = new ChoixUtilisateur("Chimie");

        matiereMath = new String("Mathématiques");
        matiereInfo = new String("Informatique");
        matierePhysique = new String("Physique");
        matiereChimie = new String("Chimie");

        liteMatiere = new ArrayList<String>();
        liteMatiere.add(matiereMath);
        liteMatiere.add(matiereInfo);
        liteMatiere.add(matierePhysique);
        liteMatiere.add(matiereChimie);

        choixListe = new ChoixUtilisateur(liteMatiere);
    }


    @Test
    public void testString() {
        //Test d'égalité sur un seul choix de matière
        assertEquals(choixMath.getChoix().get(0), matiereMath);
        assertEquals(choixInfo.getChoix().get(0), matiereInfo);
        assertEquals(choixPhysique.getChoix().get(0), matierePhysique);
        assertEquals(choixChimie.getChoix().get(0), matiereChimie);

        //Test d'égalité sur plusieurs choix de matières (liste des matière choixListe)
        assertEquals(choixListe.getChoix().get(0), "Mathématiques");
        assertEquals(choixListe.getChoix().get(1), "Informatique");
        assertEquals(choixListe.getChoix().get(2), "Physique");
        assertEquals(choixListe.getChoix().get(3), "Chimie");

        //choixListe.toString() renvoie une la liste des matières
        assertEquals(choixListe.toString(), "[Mathématiques, Informatique, Physique, Chimie]");
    }


    @Test
    public void toJSON() {
        // Test du JSON avec un seul choix de matière
        assertEquals(choixMath.toJSON().toString(), "{\"liste choisie\":\"[Mathématiques]\"}");
        assertEquals(choixInfo.toJSON().toString(), "{\"liste choisie\":\"[Informatique]\"}");
        assertEquals(choixPhysique.toJSON().toString(), "{\"liste choisie\":\"[Physique]\"}");
        assertEquals(choixChimie.toJSON().toString(), "{\"liste choisie\":\"[Chimie]\"}");

        // Test du JSON avec une liste de choix de matières
        assertEquals(choixListe.toJSON().toString(), "{\"liste choisie\":\"[Mathématiques, Informatique, Physique, Chimie]\"}");
    }
}
