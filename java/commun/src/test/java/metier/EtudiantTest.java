package metier;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

public class EtudiantTest {

    Etudiant etudiant;
    LocalDate localDate = LocalDate.of(1998,05,19);


    @Before
    public void setup(){
        etudiant=new Etudiant("ashraf","ossama","ao910223",localDate,"aaaaaaa");

    }

    @Test
    public void nom() {
        assertEquals("ashraf",etudiant.getNom());
        etudiant.setNom("ASHRAF");
        assertEquals("ASHRAF",etudiant.getNom());
    }

    @Test
    public void prénom() {
        assertEquals("ossama",etudiant.getPrenom());
        etudiant.setPrenom("OSSAMA");
        assertEquals("OSSAMA",etudiant.getPrenom());
    }

    @Test
    public void numEtudiant() {
        assertEquals("ao910223",etudiant.getNumEtudiant());
        etudiant.setNumEtudiant("Ao910223");
        assertEquals("Ao910223",etudiant.getNumEtudiant());
    }

    @Test
    public void dateNaissance() {
        LocalDate localDate = LocalDate.of(1998,05,19);
        LocalDate localDate2 = LocalDate.of(2000,07,07);

        assertEquals(localDate,etudiant.getDateNaissance());
        etudiant.setDateNaissance(localDate2);
        assertEquals(localDate2,etudiant.getDateNaissance());
    }

    @Test
    public void motDePasse() {
        assertEquals("aaaaaaa",etudiant.getMotDePasse());
        etudiant.setMotDePasse("AAAAAA");
        assertEquals("AAAAAA",etudiant.getMotDePasse());
    }

    @Test
    public void toJSON() {
        LocalDate localDate2 = LocalDate.of(2000,07,07);

        assertEquals("{\"motDePasse\":\"aaaaaaa\",\"numEtudiant\":\"ao910223\",\"dateNaissance\":\"1998-05-19\",\"nom\":\"ashraf\",\"prenom\":\"ossama\"}"
                ,etudiant.toJSON().toString());

        etudiant.setNom("ASHRAF");
        etudiant.setPrenom("OSSAMA");
        etudiant.setNumEtudiant("Ao910223");
        etudiant.setDateNaissance(localDate2);
        etudiant.setMotDePasse("AAAAAA");

        assertEquals("{\"motDePasse\":\"AAAAAA\",\"numEtudiant\":\"Ao910223\",\"dateNaissance\":\"2000-07-07\",\"nom\":\"ASHRAF\",\"prenom\":\"OSSAMA\"}"
                ,etudiant.toJSON().toString());
    }

}
