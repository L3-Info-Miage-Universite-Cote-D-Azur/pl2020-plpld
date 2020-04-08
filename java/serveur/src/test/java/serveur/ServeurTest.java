package serveur;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import metier.ChoixUtilisateur;
import metier.Identité;
import metier.Matiere;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static constantes.Net.*;

public class ServeurTest {

    Serveur serveur;

    SocketIOClient socketIOClient;

    Matiere matiere;
    ChoixUtilisateur choixUtilisateur;
    Identité identité;

    @Before
    public void setup() {
        serveur = new Serveur(Mockito.mock(SocketIOServer.class));
        serveur = Mockito.spy(serveur);
        socketIOClient = Mockito.mock(SocketIOClient.class);
        matiere = Mockito.mock(Matiere.class);
        choixUtilisateur = Mockito.mock(ChoixUtilisateur.class);
        identité = Mockito.mock(Identité.class);
    }



    @Test
    public void envoyerUETest() {
        String semestreTest = "SemestreTest.txt";
        serveur.envoyerUE(socketIOClient, semestreTest);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(UE), Mockito.anyMap());
    }

    @Test
    public void envoiePrerequisTest() {
        serveur.envoiePrerequis(socketIOClient);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(PREREQUIS), Mockito.anyMap());
    }


    @Test
    public void nouveauChoixTest() {
        serveur.nouveauChoix(socketIOClient, matiere);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(CHOIX), Mockito.anyString());
    }
}