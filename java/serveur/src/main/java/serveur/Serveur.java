package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import constantes.Net;
import metier.ChoixUtilisateur;
import metier.Identité;
import metier.Matiere;

import java.util.List;

import static constantes.Net.*;

public class Serveur {


    private final SocketIOServer server;

    public static final void main(String [] args) {
        // config  com.corundumstudio.socketio.Configuration;
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);

        // creation du serveur
        SocketIOServer server = new SocketIOServer(config);

        Serveur s = new Serveur(server);
        s.démarrer();
    }

    public Serveur(SocketIOServer server) {
        this.server = server;

        this.server.addEventListener(CONNEXION, Identité.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité id, AckRequest ackRequest) throws Exception {
                nouveauClient(socketIOClient, id);
            }
        });

        this.server.addEventListener(CHOIX, Matiere.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Matiere matiere, AckRequest ackRequest) throws Exception {
                nouveauChoix(socketIOClient,matiere);
            }
        });

        this.server.addEventListener(VALIDATION, ChoixUtilisateur.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient socketIOClient, ChoixUtilisateur choix, AckRequest ackRequest) throws Exception {
                validation(socketIOClient, choix);
            }
        });
    }

    protected void nouveauChoix(SocketIOClient socketIOClient, Matiere matiere) {
        System.out.println(matiere.toString());
        socketIOClient.sendEvent(CHOIX, matiere.toString());
    }

    protected void nouveauClient(SocketIOClient socketIOClient, Identité id) {
        // map.put(id, socketIOClient);
        String str = new String("Bienvenue");
        System.out.println(id+" vient de se connecter");
        socketIOClient.sendEvent("Test event bienvenue",str);
    }

    protected void validation(SocketIOClient socketIOClient, ChoixUtilisateur SelectionUE) {
        System.out.println("Votre sélection (" + SelectionUE.toString() +") a été enregistrée.");
        socketIOClient.sendEvent(VALIDATION, SelectionUE.toString());
    }

    private void démarrer() {
        server.start();
    }
}