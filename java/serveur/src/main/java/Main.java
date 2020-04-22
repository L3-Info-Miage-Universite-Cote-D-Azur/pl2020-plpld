import Fichiers.GestionnaireDeFichiers;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import reseau.GestionnaireDeReseau;
import serveur.Serveur;

import java.io.File;

public class Main {


    private GestionnaireDeReseau NetHandler;
    private GestionnaireDeFichiers FileHandler;
    private Serveur serveur;




    public static final void main(String[] args)

    {
     GestionnaireDeReseau NetHandler = new GestionnaireDeReseau();

        Configuration config = new Configuration();
        config.setHostname("127.0.0.1");
        config.setPort(10101);
        // creation du serveur
        SocketIOServer server = new SocketIOServer(config);

        Serveur serveur = new Serveur(server);
        serveur.setNetHandler(NetHandler);
        serveur.démarrer();
    }
}
