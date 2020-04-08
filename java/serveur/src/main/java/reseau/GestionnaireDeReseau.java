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


    public void nouvelleConfirmation(Identité id) throws IOException {
        System.out.println(id.getNom());
        FileHandler.EcrireDansFichier("BD Matieres",id.getNom());
    }
    public void nouveauEtu(Etudiant etudiant) throws IOException {
        System.out.println(" L'étudiant numero " + etudiant.getNumEtudiant() + " s'est inscrit");
        System.out.println( etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse());
        FileHandler.EcrireDansFichier("BD.txt",etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse());
        FileHandler.EcrireDansFichier("BD Matieres","$"+etudiant.getNumEtudiant());
    }


    public void nouveauChoix(Identité id,Matiere matiere) {
        System.out.println(matiere.toString());
        //socketIOClient.sendEvent(CHOIX, matiere.toString());
    }

    public boolean nouvelleConnexion(Identité id)
    {

        System.out.println("Tentative de connexion...." + id.getNom());
        if(FileHandler.trouverEtudiant(BD, id.getNom())) {
           System.out.println("accepté");
           return true;
           //socketIOClient.sendEvent(NV_CONNEXION, "true");
        }
        else {
            System.out.println("refusé");
            return false;
           // socketIOClient.sendEvent(NV_CONNEXION, "false");
        }

    }
  /*  // TODO: 28/02/2020  Modifier cette méthode et la rendre plus utile
    /**
     *  Pour l'instant, cette méthode affiche un message de validation
     * @param socketIOClient
     * @param id  Identité du client

    public void nouveauClient(SocketIOClient socketIOClient, Identité id) {
        System.out.println(id+" vient de se connecter");
    }
*/
    /**
     * Envoie le fichier des prerequis au client
     * @param socketIOClient
     */
   /* public void envoiePrerequis(SocketIOClient socketIOClient) {
        socketIOClient.sendEvent(PREREQUIS, FileHandler.constructionPrerequis(S1, S2, S3, S4, FICHIER_PREREQUIS));
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
   /* public void envoyerUE(String path) {
        socketIOClient.sendEvent(UE,FileHandler.lireFichier(path));
    }*/
}
