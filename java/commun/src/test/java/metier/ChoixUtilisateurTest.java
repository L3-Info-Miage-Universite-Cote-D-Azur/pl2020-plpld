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

    private Matiere matiereMath;
    private Matiere matiereInfo;
    private Matiere matierePhysique;
    private Matiere matiereChimie;

    private ArrayList<Matiere> liteMatiere;

    @Before
    public void setup() {
        //Initialisation
        choixMath = new ChoixUtilisateur("Mathématiques");
        choixInfo = new ChoixUtilisateur("Informatique");
        choixPhysique = new ChoixUtilisateur("Physique");
        choixChimie = new ChoixUtilisateur("Chimie");

        matiereMath = new Matiere("Mathématiques");
        matiereInfo = new Matiere("Informatique");
        matierePhysique = new Matiere("Physique");
        matiereChimie = new Matiere("Chimie");

        liteMatiere = new ArrayList<Matiere>();
        liteMatiere.add(matiereMath);
        liteMatiere.add(matiereInfo);
        liteMatiere.add(matierePhysique);
        liteMatiere.add(matiereChimie);

        choixListe = new ChoixUtilisateur(liteMatiere);
    }


    @Test
    public void testString() {
        //Test d'égalité sur un seul choix de matière
        assertEquals(choixMath.getChoix().get(0).getNom(), matiereMath.getNom());
        assertEquals(choixInfo.getChoix().get(0).getNom(), matiereInfo.getNom());
        assertEquals(choixPhysique.getChoix().get(0).getNom(), matierePhysique.getNom());
        assertEquals(choixChimie.getChoix().get(0).getNom(), matiereChimie.getNom());

        //Test d'égalité sur plusieurs choix de matières (liste des matière choixListe)
        assertEquals(choixListe.getChoix().get(0).getNom(), "Mathématiques");
        assertEquals(choixListe.getChoix().get(1).getNom(), "Informatique");
        assertEquals(choixListe.getChoix().get(2).getNom(), "Physique");
        assertEquals(choixListe.getChoix().get(3).getNom(), "Chimie");

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
