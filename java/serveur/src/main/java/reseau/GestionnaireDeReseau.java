package reseau;

import Fichiers.GestionnaireDeFichiers;
import com.corundumstudio.socketio.SocketIOClient;
import metier.ChoixUtilisateur;
import metier.Etudiant;
import metier.Identité;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constantes.Net.*;
import static constantes.Net.UE;

/**
 *  Classe GestionnaireDeRéseau, cette classe sert de passerelle entre le serveur et GestionnaireDeFichier
 *  afin de faciliter la communication entre les classes
 */

public class GestionnaireDeReseau {



    /**
     *  objet GestionnaireDeFichiers qui communique avec la classe
     */
    private GestionnaireDeFichiers FileHandler = new GestionnaireDeFichiers();
    private String tmpNum;

    public void nouvelleConfirmation(ChoixUtilisateur choix) throws IOException {
        System.out.println(choix.toString());
        FileHandler.EcrireDansFichierListe(tmpNum + ".txt",choix.getChoix());

    }

    /**
     *
     * @param etudiant
     * @throws IOException
     */
    public void nouveauEtu(Etudiant etudiant) throws IOException {
        System.out.println("L'étudiant numero " + etudiant.getNumEtudiant() + " s'est inscrit");
        System.out.println( etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse());
        tmpNum = etudiant.getNumEtudiant();
        FileHandler.EcrireDansFichier("BD.txt",etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse());
        FileHandler.EcrireDansFichier("BD Matieres.txt","$"+etudiant.getNumEtudiant());
        FileHandler.EnregistrerInfoEtudiant("BD INFO ETUDIANTS.txt",etudiant);
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
        }
    }

    /**
     *   Valide le choix de l'étudiant et transmet la liste des UEs choisit par l'étudiant au client
     * @param socketIOClient
     * @param SelectionUE Liste de matières
     */
    public void validation(SocketIOClient socketIOClient, ChoixUtilisateur SelectionUE) {
        if(SelectionUE != null)
        {
            socketIOClient.sendEvent(VALIDATION, SelectionUE.toString());
          System.out.println("Votre sélection pour le semestre n°" + SelectionUE.getNumSemestre() + " (" + SelectionUE.toString() +") a été enregistrée.");
    }}

    public Map<String, Map<Integer, List<String>>> lireFichierPredefini(String path) {
        return FileHandler.lireFichierPredefini(path);
    }

    public HashMap<String, List<String>> lireConstructionPrerequis(String s1, String s2, String s3, String s4, String fichierPrerequis) {
        return FileHandler.constructionPrerequis(s1,s2,s3,s4,fichierPrerequis);
    }

    public HashMap<String, List<String>> lireFichier(String path) {
        return FileHandler.lireFichier(path);
    }

    public Etudiant getEtudiant(String numEtudiant) {return FileHandler.getEtudiant("BD INFO ETUDIANTS.txt", numEtudiant); }

    public List<Map<String, List<String>>> lireTout(String s1, String s2, String s3, String s4) {
        return FileHandler.lireTout(s1,s2,s3,s4);
    }

    public void setFileHandler(GestionnaireDeFichiers fileHandler) {
        FileHandler = fileHandler;
    }
}
