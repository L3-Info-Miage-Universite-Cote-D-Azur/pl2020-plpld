package serveur;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import metier.ChoixUtilisateur;
import metier.Identité;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static constantes.Net.*;

public class ServeurTest {

    Serveur serveur;

    SocketIOClient socketIOClient;

    String matiere;
    ChoixUtilisateur choixUtilisateur;
    Identité identité;

    @Before
    public void setup() {
        serveur = new Serveur(Mockito.mock(SocketIOServer.class));
        serveur = Mockito.spy(serveur);
        socketIOClient = Mockito.mock(SocketIOClient.class);
        choixUtilisateur = Mockito.mock(ChoixUtilisateur.class);
        identité = Mockito.mock(Identité.class);
    }



    @Test
    public void envoyerUETest() {
        String semestreTest = "SemestreTest.txt";           // Fichier de test
        serveur.envoyerUE(socketIOClient, semestreTest);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(UE), Mockito.anyMap());
    }

    @Test
    public void envoiePrerequisTest() {
        serveur.envoiePrerequis(socketIOClient);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(PREREQUIS), Mockito.anyMap());
    }

    @Test
    public void envoiePredefini() {
        String PredefiniTest = "PredefiniTest.txt";         // Fichier de test
        serveur.envoiePredefini(socketIOClient, PredefiniTest);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(PREDEFINI), Mockito.anyMap());
    }

    @Test
    public void nouveauChoixTest() {
        serveur.nouveauChoix(socketIOClient, "Math");
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(CHOIX), Mockito.anyString());
    }
}
