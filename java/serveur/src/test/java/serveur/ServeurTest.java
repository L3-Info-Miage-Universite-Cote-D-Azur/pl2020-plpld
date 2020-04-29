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


    /**
     * On vérifie que la méthode "sendEvent" est bien effectué avec les bons paramètres dans la méthode "envoyerUETest".
     */
    @Test
    public void envoyerUETest() {
        String semestreTest = "SemestreTest.txt";           // Fichier de test
        serveur.envoyerUE(socketIOClient, semestreTest);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(UE), Mockito.anyMap());
    }


    /**
     * On vérifie que la méthode "sendEvent" est bien effectué avec les bons paramètres dans la méthode "envoyerTout".
     */
    @Test
    public void envoyerToutTest() {
        String semestreTest = "SemestreTest.txt";           // Fichier de test
        serveur.envoyerTout(socketIOClient, semestreTest, semestreTest, semestreTest, semestreTest);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(ENVOIE_TOUT), Mockito.anyList());
    }


    /**
     * On vérifie que la méthode "sendEvent" est bien effectué avec les bons paramètres dans la méthode "envoiePrerequis".
     */
    @Test
    public void envoiePrerequisTest() {
        serveur.envoiePrerequis(socketIOClient);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(PREREQUIS), Mockito.anyMap());
    }


    /**
     * On vérifie que la méthode "sendEvent" est bien effectué avec les bons paramètres dans la méthode "envoiePredefini".
     */
    @Test
    public void envoiePredefiniTest() {
        String PredefiniTest = "PredefiniTest.txt";         // Fichier de test
        serveur.envoiePredefini(socketIOClient, PredefiniTest);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(PREDEFINI), Mockito.anyMap());
    }


    /**
     * On vérifie que la méthode "sendEvent" est bien effectué avec les bons paramètres dans la méthode "nouveauChoix".
     */
    @Test
    public void nouveauChoixTest() {
        serveur.nouveauChoix(socketIOClient, "Math");
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(CHOIX), Mockito.anyString());
    }
}
