package serveur;

import Fichiers.GestionnaireDeFichiers;
import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import metier.*;
import reseau.GestionnaireDeReseau;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static constantes.Net.*;

/**
 *  Classe du serveur, contenant toutes les méthodes pour la communication vers le client
 */
public class Serveur {

    private final SocketIOServer server;
    private GestionnaireDeReseau NetHandler = new GestionnaireDeReseau();

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
                NetHandler.nouveauClient(socketIOClient, id);
                NetHandler.envoyerUE(socketIOClient,S1);
                NetHandler.envoiePrerequis(socketIOClient,FICHIER_PREREQUIS);


            }
        });
        this.server.addEventListener(NV_CONNEXION, Identité.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité etudiant, AckRequest ackRequest) throws Exception {
                NetHandler.nouvelleConnexion(socketIOClient,etudiant);
            }
        });
        this.server.addEventListener(NV_ETU, Etudiant.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Etudiant etudiant, AckRequest ackRequest) throws Exception {
                System.out.println(etudiant.getNom());
                System.out.println(etudiant.getPrenom());
                System.out.println(etudiant.getDateNaissance());

                System.out.println(etudiant.getMotDePasse());
                System.out.println(etudiant.getNumEtudiant());


                NetHandler.nouveauEtu(socketIOClient,etudiant);
            }
        });
        this.server.addEventListener(CHOIX, Matiere.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Matiere matiere, AckRequest ackRequest) throws Exception {
               NetHandler.nouveauChoix(socketIOClient,matiere);
            }
        });

        this.server.addEventListener(VALIDATION, ChoixUtilisateur.class, new DataListener<>() {
            @Override
            public void onData(SocketIOClient socketIOClient, ChoixUtilisateur choix, AckRequest ackRequest) throws Exception {
                NetHandler.validation(socketIOClient, choix);
                switch (choix.getNumSemestre()) {
                    case 1:
                        NetHandler.envoyerUE(socketIOClient, S2);
                        break;
                    case 2 :
                        NetHandler.envoyerUE(socketIOClient,S3);
                        break;
                    case 3 :
                        NetHandler.envoyerUE(socketIOClient,S4);
                        break;
                }
            }
        });
    }


    private void démarrer() {
        server.start();
    }
}