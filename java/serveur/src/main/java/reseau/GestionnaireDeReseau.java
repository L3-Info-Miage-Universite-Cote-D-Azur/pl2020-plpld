package reseau;

import Fichiers.GestionnaireDeFichiers;
import com.corundumstudio.socketio.SocketIOClient;
import metier.ChoixUtilisateur;
import metier.Etudiant;
import metier.Identité;
import metier.Matiere;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static constantes.Net.*;
import static constantes.Net.UE;

public class GestionnaireDeReseau {

    private GestionnaireDeFichiers FileHandler = new GestionnaireDeFichiers();



    public void nouveauEtu(SocketIOClient socketIOClient, Etudiant etudiant) throws IOException {
        System.out.println(" L'étudiant numero " + etudiant.getNumEtudiant() + " s'est inscrit");
        System.out.println( etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse());
        FileHandler.EcrireDansFichier("BD.txt",etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse());
    }

    /**
     * Envoie le choix de l'étudiant au client
     * @param socketIOClient Le client
     * @param matiere La matière choisi par le client
     */
    public void nouveauChoix(SocketIOClient socketIOClient, Matiere matiere) {
        System.out.println(matiere.toString());
        socketIOClient.sendEvent(CHOIX, matiere.toString());
    }

    public void nouvelleConnexion(SocketIOClient socketIOClient,Identité id)
    {

        System.out.println("Tentative de connexion...." + id.getNom());
        if(FileHandler.trouverEtudiant(id.getNom())) {
           System.out.println("accepté");
           socketIOClient.sendEvent(NV_CONNEXION, "true");
        }
        else {
            System.out.println("refusé");
            socketIOClient.sendEvent(NV_CONNEXION, "false");
        }

    }
    // TODO: 28/02/2020  Modifier cette méthode et la rendre plus utile
    /**
     *  Pour l'instant, cette méthode affiche un message de validation
     * @param socketIOClient
     * @param id  Identité du client
     */
    public void nouveauClient(SocketIOClient socketIOClient, Identité id) {
        System.out.println(id+" vient de se connecter");
    }

    /**
     * Envoie le fichier des prerequis au client
     * @param socketIOClient
     */
    public void envoiePrerequis(SocketIOClient socketIOClient) {
        socketIOClient.sendEvent(PREREQUIS, FileHandler.constructionPrerequis());
    }

    /**
     *   Valide le choix de l'étudiant et transmet la liste des UEs choisit par l'étudiant au client
     * @param socketIOClient
     * @param SelectionUE Liste de matières
     */
    public void validation(SocketIOClient socketIOClient, ChoixUtilisateur SelectionUE) {
        System.out.println("Votre sélection pour le semestre n°" + SelectionUE.getNumSemestre() + " (" + SelectionUE.toString() +") a été enregistrée.");
        socketIOClient.sendEvent(VALIDATION, SelectionUE.toString());
    }

    /**
     * Envoie la liste des UE au client
     * @param socketIOClient
     * @param path Liste des UE
     */
    public void envoyerUE(SocketIOClient socketIOClient,String path) {
        socketIOClient.sendEvent(UE,FileHandler.lireFichier(path));
    }
}
