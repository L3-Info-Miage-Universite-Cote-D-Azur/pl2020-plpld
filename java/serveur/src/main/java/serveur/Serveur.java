package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import metier.ChoixUtilisateur;
import metier.Identité;
import metier.ListeSemestre;
import metier.Matiere;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static constantes.Net.*;

/**
 *  Classe du serveur, contenant toutes les méthodes pour la communication vers le client
 */
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
                envoyerUE(socketIOClient,S1);
                envoiePrerequis(socketIOClient,FICHIER_PREREQUIS);


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


                switch (choix.getNumSemestre()) {
                    case 1:
                        envoyerUE(socketIOClient, S2);
                        break;
                    case 2 :
                        envoyerUE(socketIOClient,S3);
                        break;
                    case 3 :
                        envoyerUE(socketIOClient,S4);
                        break;
                }
                    System.out.println("semestre en cours : " + choix.getNumSemestre());
            }
        });
    }

    /**
     * Envoie le choix de l'étudiant au client
     * @param socketIOClient Le client
     * @param matiere La matière choisi par le client
     */
    protected void nouveauChoix(SocketIOClient socketIOClient, Matiere matiere) {
        System.out.println(matiere.toString());
        socketIOClient.sendEvent(CHOIX, matiere.toString());
    }

    // TODO: 28/02/2020  Modifier cette méthode et la rendre plus utile
    /**
     *  Pour l'instant, cette méthode affiche un message de validation
     * @param socketIOClient
     * @param id  Identité du client
     */
    protected void nouveauClient(SocketIOClient socketIOClient, Identité id) {
        System.out.println(id+" vient de se connecter");
    }

    /**
     * Envoie le fichier des prerequis au client
     * @param socketIOClient
     * @param path Fichier des prerequis
     */
    protected void envoiePrerequis(SocketIOClient socketIOClient,String path) {
        socketIOClient.sendEvent(PREREQUIS,lireFichier(path));
    }

    /**
     *   Valide le choix de l'étudiant et transmet la liste des UEs choisit par l'étudiant au client
     * @param socketIOClient
     * @param SelectionUE Liste de matières
     */
    protected void validation(SocketIOClient socketIOClient, ChoixUtilisateur SelectionUE) {
        System.out.println("Votre sélection pour le semestre n°" + SelectionUE.getNumSemestre() + " (" + SelectionUE.toString() +") a été enregistrée.");
        socketIOClient.sendEvent(VALIDATION, SelectionUE.toString());
    }

    /**
     * Envoie la liste des UE au client
     * @param socketIOClient
     * @param path Liste des UE
     */
    protected void envoyerUE(SocketIOClient socketIOClient,String path) {
        socketIOClient.sendEvent(UE,lireFichier(path));
    }

    /**
     * Permet de lire un fichier (UE ou prerequis) afin d'en faire un HashMap<String,Liste<String>>
     * @param fichier Fichier a lire
     * @return HashMap<String, Liste<String>>
     */
    public HashMap<String, List<String>> lireFichier(String fichier)
    {
        ListeSemestre listeSemestre = new ListeSemestre();
        String previousKey = null;
        BufferedReader br;
        try{
            br = new BufferedReader(new FileReader(fichier));
            String line = br.readLine();
            while(line != null)
            {
                if(line.contains("$"))
                {
                    line = line.replace("$","");
                    listeSemestre.add(line,new ArrayList<String>());
                    previousKey = line;
                }
                else
                {
                    listeSemestre.getMapUE().get(previousKey).add(line);
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listeSemestre.getMapUE();
    }

    /**
     * Démarre le serveur
     */
    private void démarrer() {
        server.start();
    }
}