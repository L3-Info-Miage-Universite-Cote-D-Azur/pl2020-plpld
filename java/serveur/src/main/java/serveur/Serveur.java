package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import constantes.Net;
import metier.Identité;
import metier.Matiere;

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


    String selectionMatiere;
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

        this.server.addEventListener(VALIDATION, Matiere.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Matiere matiere, AckRequest ackRequest) throws Exception {
                validation(socketIOClient, matiere);
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

    protected void validation(SocketIOClient socketIOClient, Matiere matiere) {
        selectionMatiere = matiere.toString();
        System.out.println("Votre sélection (" + selectionMatiere +") a été enregistrée.");
        socketIOClient.sendEvent(VALIDATION, matiere.toString());
    }

    private void démarrer() {
        server.start();
    }
}