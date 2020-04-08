package reseau;

import com.corundumstudio.socketio.SocketIOClient;
import metier.ChoixUtilisateur;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import static constantes.Net.*;


@RunWith(MockitoJUnitRunner.class)
public class ReseauTest {

    GestionnaireDeReseau gestionnaireResau;
    SocketIOClient socketIOClient;

    ChoixUtilisateur choixUtilisateur;

    @Before
    public void setup() {
        gestionnaireResau = new GestionnaireDeReseau();
        gestionnaireResau = Mockito.spy(gestionnaireResau);
        socketIOClient = Mockito.mock(SocketIOClient.class);
        choixUtilisateur = Mockito.mock(ChoixUtilisateur.class);
    }



    @Test
    public void validationTest() {
        gestionnaireResau.validation(socketIOClient, choixUtilisateur);
        Mockito.verify(socketIOClient).sendEvent(Mockito.eq(VALIDATION), Mockito.anyString());
    }
}
