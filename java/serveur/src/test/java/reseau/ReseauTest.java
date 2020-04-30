package reseau;

import Fichiers.GestionnaireDeFichiers;
import com.corundumstudio.socketio.SocketIOClient;
import metier.ChoixUtilisateur;
import metier.Etudiant;
import metier.Identité;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import static constantes.Net.*;
import static org.junit.Assert.*;


@RunWith(MockitoJUnitRunner.class)
public class ReseauTest {

    private GestionnaireDeReseau gestionnaireResauSpy;
    private SocketIOClient socketIOClient;
    private ChoixUtilisateur choixUtilisateur;
    private GestionnaireDeFichiers fileHandler;
    private Identité identité;
    private Etudiant etudiant;

    @Before
    public void setup() {
        gestionnaireResauSpy = new GestionnaireDeReseau();
        gestionnaireResauSpy = Mockito.spy(gestionnaireResauSpy);
        socketIOClient = Mockito.mock(SocketIOClient.class);
        choixUtilisateur = Mockito.mock(ChoixUtilisateur.class);
        identité = new Identité();
        etudiant = new Etudiant();
        fileHandler = Mockito.mock(GestionnaireDeFichiers.class);
        gestionnaireResauSpy.setFileHandler(fileHandler);
    }


    /**
     * On vérifie si la méthode "EcrireDansFichierListe" de la classe "GestionnaireDeFichier" a bien été effectué.
     * @throws IOException
     */
    @Test
    public void nouvelleConfirmationTest() throws IOException {
        gestionnaireResauSpy.nouvelleConfirmation(choixUtilisateur);
        Mockito.verify(fileHandler).EcrireDansFichierListe(Mockito.anyString(), Mockito.anyList());
    }


    /**
     * On vérifie si la méthode "EcrireDansFichier" et "EnregistrerInfoEtudiant" de la classe
     * "GestionnaireDeFichier" a bien été effectué.
     * @throws IOException
     */
    @Test
    public void nouveauEtuTest() throws IOException {
        etudiant.setNumEtudiant("123456789");
        etudiant.setMotDePasse("MdpTest");
        gestionnaireResauSpy.nouveauEtu(etudiant);
        Mockito.verify(fileHandler).EcrireDansFichier(Mockito.eq(BD), Mockito.eq(etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse()));
        Mockito.verify(fileHandler).EcrireDansFichier(Mockito.eq("BD Matieres.txt"), Mockito.eq("$" + etudiant.getNumEtudiant()));
        Mockito.verify(fileHandler).EnregistrerInfoEtudiant(Mockito.eq("BD INFO ETUDIANTS.txt"), Mockito.any(Etudiant.class));
    }


    /**
     *  On vérifie si la méthode "EcrireDansFichierListe" de la classe "GestionnaireDeFichier" a bien été effectué.
     */
    @Test
    public void nouvelleConnexionTest() {
        identité.setNom("test");
        gestionnaireResauSpy.nouvelleConnexion(identité);
        Mockito.verify(fileHandler).trouverEtudiant(Mockito.eq(BD), Mockito.eq("test"));
    }


    /**
     * On vérifie si la méthode "lireFichierPredefini" de la classe "GestionnaireDeFichier" a bien été effectué.
     */
    @Test
    public void lireFichierPredefiniTest() {
        String predefiniTest = "PredefiniTest.txt";
        gestionnaireResauSpy.lireFichierPredefini(predefiniTest);
        Mockito.verify(fileHandler).lireFichierPredefini(Mockito.anyString());
    }


    /**
     * On vérifie si la méthode "constructionPrerequis" de la classe "GestionnaireDeFichier" a bien été effectué.
     */
    @Test
    public void lireConstructionPrerequisTest() {
        String prerequisTest = "PrerequisTest.txt";
        String semestreTest = "SemestreTest.txt";
        gestionnaireResauSpy.lireConstructionPrerequis(semestreTest, semestreTest, semestreTest, semestreTest, prerequisTest);
        Mockito.verify(fileHandler).constructionPrerequis(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }


    /**
     * On vérifie si la méthode "lireFichier" de la classe "GestionnaireDeFichier" a bien été effectué.
     */
    @Test
    public void lireFichierTest() {
        String semestreTest = "SemestreTest.txt";
        gestionnaireResauSpy.lireFichier(semestreTest);
        Mockito.verify(fileHandler).lireFichier(Mockito.anyString());
    }


    /**
     * On vérifie si la méthode "getEtudiant" de la classe "GestionnaireDeFichier" a bien été effectué.
     */
    @Test
    public void getEtudiantTest() {
        String baseDonneEtuTest = "src/test/resources/BaseDonneeEtuTest.txt";
        gestionnaireResauSpy.getEtudiant(baseDonneEtuTest, "ad123456");
        Mockito.verify(fileHandler).getEtudiant(Mockito.eq(baseDonneEtuTest), Mockito.eq("ad123456"));
    }


    /**
     * On vérifie que la méthode "getUEChoisies" renvoie bien une Map<Etudiant, List<String>> avec comme
     * List<String> la liste des UEs choisies par l'étudiant en question.
     * Pour les besoins de ce teste on utilise la ressource de teste "BaseDonneePourGetUEChoisies.txt", qui
     * contient les informations d'un étudiant dont son numéro d'étudiant est : "src/test/resources/ListeUEetudiantTest",
     * pour pouvoir s'adapter a la méthode "etuInscrits" de la classe "GestionnaireDeFichier".
     */
    @Test
    public void getUEChoisiesTest() {
        String baseDonneGetUETest = "src/test/resources/BaseDonneePourGetUEChoisies.txt";
        GestionnaireDeReseau gestionnaireDeReseau = new GestionnaireDeReseau();
        Map<Etudiant, List<String>> mapUE = gestionnaireDeReseau.getUEChoisies(baseDonneGetUETest);

        for (Map.Entry<Etudiant, List<String>> e : mapUE.entrySet()) {
            Etudiant etu = e.getKey();
            List<String> UE = e.getValue();

            assertEquals(etu.getNumEtudiant(), "SIMPSON Homer (src/test/resources/ListeUEetudiantTest)");
            assertEquals(UE.get(0), "Bases de l'informatique");
            assertEquals(UE.get(1), "Introduction à l'informatique par le web");
            assertEquals(UE.get(2), "Structures de données et programmation C");
            assertEquals(UE.get(3), "Fondements 1");
            assertEquals(UE.get(4), "Méthodes : approche continue");
            assertEquals(UE.get(5), "Economie-Gestion S1");
            assertEquals(UE.get(6), "Intro R");
            assertEquals(UE.get(7), "Mécanique 1");
        }
    }


    /**
     * On vérifie que la méthode "getNumEtudiants" renvoie bien une List<String> des numéros d'étudiant.
     */
    @Test
    public void getNumEtudiantsTest() {
        String baseDonneEtuTest = "src/test/resources/BaseDonneeEtuTest.txt";
        GestionnaireDeReseau gestionnaireDeReseau = new GestionnaireDeReseau();
        List<String> liste = gestionnaireDeReseau.getNumEtudiants(baseDonneEtuTest);

        // On vérifie les numéros d'étudiant
        assertEquals(liste.get(0), "ad123456");
        assertEquals(liste.get(1), "ad111111");
        assertEquals(liste.get(2), "ad000000");
    }


    /**
     * On vérifie si la méthode "lireTout" de la classe "GestionnaireDeFichier" a bien été effectué.
     */
    @Test
    public void lireToutTest() {
        String semestreTest = "SemestreTest.txt";
        gestionnaireResauSpy.lireTout(semestreTest, semestreTest, semestreTest, semestreTest);
        Mockito.verify(fileHandler).lireTout(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }


    /**
     * On vérifie si la méthode "descriptionUE" de la classe "GestionnaireDeFichier" a bien été effectué.
     */
    @Test
    public void description_UETest() {
        String descriptionUETest = "DescriptionUETest.txt";
        gestionnaireResauSpy.description_UE(descriptionUETest);
        Mockito.verify(fileHandler).descriptionUE(Mockito.eq(descriptionUETest));
    }
}
