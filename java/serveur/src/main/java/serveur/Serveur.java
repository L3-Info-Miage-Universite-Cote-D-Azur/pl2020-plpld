package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import metier.Identité;

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

        this.server.addEventListener(CONNEXION, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité id, AckRequest ackRequest) throws Exception {
                nouveauClient(socketIOClient, id);
            }
        });

    }

    protected void nouveauClient(SocketIOClient socketIOClient, Identité id) {
        // map.put(id, socketIOClient);
        System.out.println(id+" vient de se connecter");
        socketIOClient.sendEvent("Bienvenue sur le portail pour la selection des cours de la licence sciences et technologies"," ");
    }

    private void démarrer() {
        server.start();
    }


}