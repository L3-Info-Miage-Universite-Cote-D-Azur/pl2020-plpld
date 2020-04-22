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
import static constantes.Net.*;


@RunWith(MockitoJUnitRunner.class)
public class ReseauTest {

    private GestionnaireDeReseau gestionnaireResau;
    private SocketIOClient socketIOClient;
    private ChoixUtilisateur choixUtilisateur;
    private GestionnaireDeFichiers fileHandler;
    private Identité identité;
    private Etudiant etudiant;

    @Before
    public void setup() {
        gestionnaireResau = new GestionnaireDeReseau();
        gestionnaireResau = Mockito.spy(gestionnaireResau);
        socketIOClient = Mockito.mock(SocketIOClient.class);
        choixUtilisateur = Mockito.mock(ChoixUtilisateur.class);
        identité = new Identité();
        etudiant = new Etudiant();
        fileHandler = Mockito.mock(GestionnaireDeFichiers.class);
        gestionnaireResau.setFileHandler(fileHandler);
    }


    @Test
    public void nouvelleConfirmation() throws IOException {
        gestionnaireResau.nouvelleConfirmation(choixUtilisateur);
        Mockito.verify(fileHandler).EcrireDansFichierListe(Mockito.eq(null), Mockito.anyList());
    }

    @Test
    public void nouveauEtuTest() throws IOException {
        etudiant.setNumEtudiant("123456789");
        etudiant.setMotDePasse("MdpTest");
        gestionnaireResau.nouveauEtu(etudiant);
        Mockito.verify(fileHandler).EcrireDansFichier(Mockito.eq(BD), Mockito.eq(etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse()));
        Mockito.verify(fileHandler).EcrireDansFichier(Mockito.eq("BD Matieres"), Mockito.eq("$" + etudiant.getNumEtudiant()));
    }

    @Test
    public void nouvelleConnexionTest() {
        identité.setNom("test");
        gestionnaireResau.nouvelleConnexion(identité);
        Mockito.verify(fileHandler).trouverEtudiant(Mockito.eq(BD), Mockito.eq("test"));
    }


    @Test
    public void validationTest() {
        gestionnaireResau.validation(socketIOClient, choixUtilisateur);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(VALIDATION), Mockito.anyString());
    }


    @Test
    public void lireFichierPredefini() {
        String predefiniTest = "PredefiniTest.txt";
        gestionnaireResau.lireFichierPredefini(predefiniTest);
        Mockito.verify(fileHandler).lireFichierPredefini(Mockito.anyString());
    }

    @Test
    public void lireConstructionPrerequis() {
        String prerequisTest = "PrerequisTest.txt";
        String semestreTest = "SemestreTest.txt";
        gestionnaireResau.lireConstructionPrerequis(semestreTest, semestreTest, semestreTest, semestreTest, prerequisTest);
        Mockito.verify(fileHandler).constructionPrerequis(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }


    @Test
    public void lireFichier() {
        String semestreTest = "SemestreTest.txt";
        gestionnaireResau.lireFichier(semestreTest);
        Mockito.verify(fileHandler).lireFichier(Mockito.anyString());
    }


    @Test
    public void lireTout() {
        String semestreTest = "SemestreTest.txt";
        gestionnaireResau.lireTout(semestreTest, semestreTest, semestreTest, semestreTest);
        Mockito.verify(fileHandler).lireTout(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}
