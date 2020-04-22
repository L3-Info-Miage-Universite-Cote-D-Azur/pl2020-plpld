package serveur;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import reseau.GestionnaireDeReseau;
import static constantes.Net.*;


public class ServeurTest {

    private Serveur serveur;
    private SocketIOClient socketIOClient;
    private GestionnaireDeReseau NetHandler;

    @Before
    public void setup() {
        serveur = new Serveur(Mockito.mock(SocketIOServer.class));
        serveur = Mockito.spy(serveur);
        socketIOClient = Mockito.mock(SocketIOClient.class);
        NetHandler = Mockito.mock(GestionnaireDeReseau.class);
        serveur.setNetHandler(NetHandler);
    }


    @Test
    public void envoyerUETest() {
        String semestreTest = "SemestreTest.txt";           // Fichier de test
        serveur.envoyerUE(socketIOClient, semestreTest);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(UE), Mockito.anyMap());
    }


    @Test
    public void envoyerToutTest() {
        String semestreTest = "SemestreTest.txt";
        serveur.envoyerTout(socketIOClient, semestreTest, semestreTest, semestreTest, semestreTest);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(ENVOIE_TOUT), Mockito.anyList());
    }

    @Test
    public void envoiePrerequisTest() {
        serveur.envoiePrerequis(socketIOClient);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(PREREQUIS), Mockito.anyMap());
    }

    @Test
    public void envoiePredefiniTest() {
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
