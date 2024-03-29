package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import metier.*;
import reseau.GestionnaireDeReseau;

import java.io.*;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.*;

import static constantes.Net.*;

/**
 *  Classe du serveur, contenant toutes les méthodes pour la communication vers le client
 */
public class Serveur {

    /**
     *   Variable socketIOServer, le serveur.
     */
    private SocketIOServer server;

    /**
     *  variable GestionnaireDeReseau, qui joue le role de passerelle entre le gestionnaire de fichier et le serveur
     */

    private GestionnaireDeReseau NetHandler;

    /**
     *  map qui gère les connexions des différents étudiants, chaque étudiant est relié a une connexion
     */

    private Map<Identité,SocketIOClient> mapEtudiants = new HashMap<>();

    public Serveur(SocketIOServer server) {

        this.server = server;
        /**
         *  Evenement de connexion qui gère la connexion d'un étudiant, la connexion est enregistrée dans la map.
         *  Les prérequis de chaque parcours sont alors envoyés au client par le serveur.
         *  La description des UE est envoyé au client par le serveur .
         */

        this.server.addEventListener(CONNEXION, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité id, AckRequest ackRequest) throws IOException, ParseException, URISyntaxException {
                mapEtudiants.put(id, socketIOClient);
                socketIOClient.sendEvent(NV_CONNEXION, "false");
                envoiePrerequis(mapEtudiants.get(id));
                socketIOClient.sendEvent(NUM_ETUDIANTS, NetHandler.getNumEtudiants("BD INFO ETUDIANTS.txt"));
                socketIOClient.sendEvent(DESCRIPTION_UE,NetHandler.description_UE("DescriptionUE.txt"));
                System.out.println(id.getNom() + " s'est connecté.");
            }
        });
        /**
         *  Evenement qui envoie la liste des prerequis brut au serveur,juste les prerequis en eux même.
         */
        this.server.addEventListener(PREREQUIS_BRUT, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité identité, AckRequest ackRequest) throws Exception {
                socketIOClient.sendEvent(PREREQUIS_BRUT,NetHandler.lireFichier(FICHIER_PREREQUIS));
            }
        });

        /**
         *  Evenement qui envoie au client le choix du parcours de l'étudiant s'il existe
         */
        this.server.addEventListener(CHANGEMENT_PREREQUIS, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité identité, AckRequest ackRequest) throws Exception {
                socketIOClient.sendEvent(CHANGEMENT_PREREQUIS,NetHandler.getParcoursEtu(identité.getNom()));
                System.out.println("Nouvelle demande de parcours ... " + identité.getNom());
            }
        });
        /**
         *  Evenement de confirmation finale du choix du parcours de l'étudiant
         *  le choix de l'étudiant est enregistré dans un fichier
         */

        this.server.addEventListener(CONFIRMATION, ChoixUtilisateur.class, new DataListener<ChoixUtilisateur>() {
            @Override
            public void onData(SocketIOClient socketIOClient, ChoixUtilisateur choix, AckRequest ackRequest) throws Exception {
                NetHandler.nouvelleConfirmation(choix);
            }
        });

        /**
         * Evenement qui gère les tentatives de connexion des étudiants à l'application
         */
        this.server.addEventListener(NV_CONNEXION, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité etudiant, AckRequest ackRequest) throws Exception {
                if (NetHandler.nouvelleConnexion(etudiant)) {
                    socketIOClient.sendEvent(NV_CONNEXION, "true");
                } else
                    socketIOClient.sendEvent(NV_CONNEXION, "false");
            }
        });

        /**
         * Evenement qui gère les nouvelles inscriptions des étudiants à l'application
         * l'étudiant est ensuite enregistré dans la base de donnée
         */
        this.server.addEventListener(NV_ETU, Etudiant.class, new DataListener<Etudiant>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Etudiant etudiant, AckRequest ackRequest) throws Exception {
                System.out.println(etudiant.getNom());
                System.out.println(etudiant.getPrenom());
                System.out.println(etudiant.getDateNaissance());

                System.out.println(etudiant.getMotDePasse());
                System.out.println(etudiant.getNumEtudiant());

                if (NetHandler.nouveauEtu(etudiant))
                    socketIOClient.sendEvent(NV_ETU, "true");
                else
                    socketIOClient.sendEvent(NV_ETU, "false");
            }
        });

        /**
         *  Evenement qui gère les choix de l'étudiant en fonction de sa selection de matière
         */
        this.server.addEventListener(CHOIX, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String matiere, AckRequest ackRequest) {
                nouveauChoix(socketIOClient, matiere);
            }
        });
        /**
         *  Evenement qui gère la validation des UE de chaque semestre
         */
        this.server.addEventListener(VALIDATION, ChoixUtilisateur.class, new DataListener<ChoixUtilisateur>() {
            @Override
            public void onData(SocketIOClient socketIOClient, ChoixUtilisateur choix, AckRequest ackRequest) {
                socketIOClient.sendEvent(VALIDATION, choix.toString());
                if (mapEtudiants.containsValue(socketIOClient)) {
                    switch (choix.getNumSemestre()) {
                        case 1:
                            envoyerUE((socketIOClient), S2);
                            break;
                        case 2:
                            envoyerUE((socketIOClient), S3);
                            break;
                        case 3:
                            envoyerUE((socketIOClient), S4);
                            break;
                    }
                }
            }
        });
        /**
         *  Evenement qui gère l'envoie du S1 au client
         */
        this.server.addEventListener(ENVOIE_S1, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité matiere, AckRequest ackRequest) {
                if (mapEtudiants.containsValue(socketIOClient))
                    envoyerUE(socketIOClient, S1);
            }
        });
        /**
         *  Evenement qui gère l'envoie des parcours prédéfinis
         */
        this.server.addEventListener(ENVOIE_PREDEFINI, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String parcours, AckRequest ackRequest) {
                envoiePredefini(socketIOClient, FICHIER_PREDEFINI);
                envoyerTout(socketIOClient, S1, S2, S3, S4);
            }
        });

        /**
         *  Evenement qui gère l'envoi d'un mot de passe oublié
         */
        this.server.addEventListener(RESET_PASSWORD, Etudiant.class, new DataListener<Etudiant>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Etudiant et, AckRequest ackRequest) {
                System.out.println("Demande de réinitialisation du mot de passe.");
                System.out.println(et.getNumEtudiant());
                if (Objects.nonNull(NetHandler.getEtudiant("BD INFO ETUDIANTS.txt", et.getNumEtudiant()))) {
                    if (NetHandler.getEtudiant("BD INFO ETUDIANTS.txt", et.getNumEtudiant()).getDateNaissance().equals(et.getDateNaissance())) {
                        socketIOClient.sendEvent(ENVOIE_PASSWORD, NetHandler.getEtudiant("BD INFO ETUDIANTS.txt", et.getNumEtudiant()));
                        return;
                    }
                }
                socketIOClient.sendEvent(ENVOIE_PASSWORD, new Etudiant("Combinaison invalide"));
            }
        });

        /**
         * Evenement qui gère la consultation des UE choisies pour chaque étudiants
         */
        this.server.addEventListener(CONSULTATION, Identité.class, new DataListener<Identité>() {
            @Override
            public void onData(SocketIOClient socketIOClient, Identité id, AckRequest ackRequest) {
                socketIOClient.sendEvent(ENVOIE_CONSULTATION, NetHandler.getUEChoisies("BD INFO ETUDIANTS.txt"));
                System.out.println("Envoie des UE pour consultation : " + NetHandler.getUEChoisies("BD INFO ETUDIANTS.txt"));
            }
        });

    }


    public void démarrer() {
        server.start();
    }

    public void envoyerUE(SocketIOClient socketIOClient,String path){
        socketIOClient.sendEvent(UE,NetHandler.lireFichier(path));
    }

    public void envoyerTout(SocketIOClient socketIOClient, String S1, String S2, String S3, String S4){
        socketIOClient.sendEvent(ENVOIE_TOUT, NetHandler.lireTout(S1, S2, S3, S4));
    }

    /**
     * Envoie le fichier des prerequis au client
     * @param socketIOClient
     */
    public void envoiePrerequis(SocketIOClient socketIOClient) {
        socketIOClient.sendEvent(PREREQUIS, NetHandler.lireConstructionPrerequis(S1, S2, S3, S4, FICHIER_PREREQUIS));
    }

    /**
     * Envoie le fichier des parcours prédéfinis au client
     * @param socketIOClient
     * @param path Fichier des prédéfinis
     */
    public void envoiePredefini(SocketIOClient socketIOClient,String path) {
        System.out.println("Envoi du parcours prédéfini");
        socketIOClient.sendEvent(PREDEFINI, NetHandler.lireFichierPredefini(path));
    }

    /**
     *  Envoie le choix de l'UE au client
     * @param socketIOClient
     * @param matiere matiere selectionnée
     */
    public void nouveauChoix(SocketIOClient socketIOClient, String matiere){
        socketIOClient.sendEvent(CHOIX, matiere);
    }

    public void setNetHandler(GestionnaireDeReseau netHandler) {
        NetHandler = netHandler;
    }

}