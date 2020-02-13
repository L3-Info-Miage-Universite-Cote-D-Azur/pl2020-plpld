package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import metier.Compteur;
import metier.Identité;

import static constantes.Net.*;


public class Serveur {


    private final SocketIOServer server;
    private final Compteur compteur;

    public static final void main(String [] args) {
        // config  com.corundumstudio.socketio.Configuration;
        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);

        // creation du serveur
        SocketIOServer server = new SocketIOServer(config);

        // obj metier
        Compteur cpt = new Compteur();

        Serveur s = new Serveur(server, cpt);
        s.démarrer();

    }

    public Serveur(SocketIOServer server, Compteur cpt) {

        this.compteur = cpt;
        this.server = server;

        this.server.addEventListener(CONNEXION, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité id, AckRequest ackRequest) throws Exception {
                nouveauClient(socketIOClient, id);
            }
        });


        this.server.addEventListener(AJOUTER, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                nouvelAjout(socketIOClient);
            }
        });
    }

    protected void nouveauClient(SocketIOClient socketIOClient, Identité id) {
        // map.put(id, socketIOClient);
        System.out.println(id+" vient de se connecter");
        socketIOClient.sendEvent(VALEUR_CPT,compteur.getCpt());
    }

    protected void nouvelAjout(SocketIOClient socketIOClient) {
        System.out.println("on ajoute");
        compteur.ajouter();
        socketIOClient.sendEvent(VALEUR_CPT,compteur.getCpt());
    }

    private void démarrer() {
        server.start();
    }


}
