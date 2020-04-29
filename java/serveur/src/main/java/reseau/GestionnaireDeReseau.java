package reseau;

import Fichiers.GestionnaireDeFichiers;
import com.corundumstudio.socketio.SocketIOClient;
import metier.ChoixUtilisateur;
import metier.Etudiant;
import metier.Identité;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

    /**
     *  Variable pour recupérer le numéro de l'étudiant et enregistrer son parcours avec un fichier texte à son nom
     */
    private String tmpNum;

    /**
     *  Cette méthode enregistre le choix de parcours de l'étudiant dans un fichier texte correspondant à son numéro étudiant
     * @param choix Le choix de parcours de l'étudiant
     * @throws IOException
     */
    public void nouvelleConfirmation(ChoixUtilisateur choix) throws IOException {
        System.out.println(choix.toString());
        FileHandler.EcrireDansFichierListe(tmpNum + ".txt",choix.getChoix());

    }

    /**
     *  Cette méthode enregistre l'étudiant passé en paramètre elle renvoie un boolean.
     *  Si l'étudiant est déjà inscrit dans la base de donnée, cette méthode renvoie faux, ainsi le serveur
     *  sait quoi faire, et transmettra cette information au client.
     *  Sinon, l'étudiant est inscrit dans les bases de données.
     * @param etudiant L'étudiant a enregistrer
     * @throws IOException
     */
    public boolean nouveauEtu(Etudiant etudiant) throws IOException {
        System.out.println("L'étudiant numero " + etudiant.getNumEtudiant() + " s'est inscrit");
        System.out.println( etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse());

        if(FileHandler.etuDejaInscrit(BD, etudiant.getNumEtudiant())) {
            System.out.println("l'étudiant est déjà inscrit, refus de l'inscription..");
            return false;
        }
        else {
            System.out.println(" l'étudiant est nouveau");
            tmpNum = etudiant.getNumEtudiant();
            FileHandler.EcrireDansFichier("BD.txt", etudiant.getNumEtudiant() + " " + etudiant.getMotDePasse());
            FileHandler.EcrireDansFichier("BD Matieres.txt", "$" + etudiant.getNumEtudiant());
            FileHandler.EnregistrerInfoEtudiant("BD INFO ETUDIANTS.txt", etudiant);
            return true;
        }
    }

    /**
     *  Cette méthode renvoie un boolean. Elle vérifie si l'étudiant voulant se connecter existe bien dans la BD.
     *  C'est à dire elle vérifie que les informations données par l'utilisateur sont valides.
     *  Le résultat est renvoyé sous forme de boolean.
     *  Le serveur communiquera cette information au client
     * @param id L'identité de la personne voulant se connecter
     * @return un boolean
     */
    public boolean nouvelleConnexion(Identité id)
    {

        System.out.println("Tentative de connexion...." + id.getNom());
        if(FileHandler.trouverEtudiant(BD, id.getNom())) {
           System.out.println("accepté");
           return true;
        }
        else {
            System.out.println("refusé");
            return false;
        }
    }



    /**
     *  Les méthodes suivantes sont implémentées dans GestionnairesDeRéseau afin de faire le pont entre le Serveur et GestionnaireDeFichier
     *  Une sorte d'entremetteur. Ainsi, elle retourne principalement les méthodes de GestionnaireDeFichiers.
     */

    /**
     *  Cette méthode retourne la map du parcours prédefini.
     *  Elle appelle la fonction de GestionnaireDeFichier et retourne la map construite
     * @param path Le chemin du fichier à lire
     * @return
     */
    public Map<String, Map<Integer, List<String>>> lireFichierPredefini(String path) {
        return FileHandler.lireFichierPredefini(path);
    }

    /**
     *  Cette méthode retourne la map des prérequis
     * @param s1 le chemin du S1
     * @param s2 le chemin du S2
     * @param s3 le chemin du S3
     * @param s4 le chemin du S4
     * @param fichierPrerequis le chemin du fichier des prerequis
     * @return
     */
    public HashMap<String, List<String>> lireConstructionPrerequis(String s1, String s2, String s3, String s4, String fichierPrerequis) {
        return FileHandler.constructionPrerequis(s1,s2,s3,s4,fichierPrerequis);
    }


    /**
     *
     * @param path le fichier à lire
     * @return la map construite à partir du fichier lu
     */
    public HashMap<String, List<String>> lireFichier(String path) {
        return FileHandler.lireFichier(path);
    }

    /**
     *  Cette méthode retourne un étudiant grâce à son numéro étudiant, s'il existe dans la BD.
     * @param numEtudiant
     * @return un étudiant
     */
    public Etudiant getEtudiant(String path, String numEtudiant) {return FileHandler.getEtudiant(path, numEtudiant); }

    /**
     *  Cette méthode associe chaque étudiant au parcours qu'il a choisi
     *  @return map associant un étudiant à la liste d'UE qu'il a choisi
     */
    public Map<Etudiant, List<String>> getUEChoisies(String path) {
        HashMap<Etudiant, List<String>> map = new HashMap<>();
        for(Etudiant etudiant: FileHandler.etuInscrits(path)) {
            map.put(new Etudiant(etudiant.getNom().toUpperCase() + " " + etudiant.getPrenom() + " (" + etudiant.getNumEtudiant() + ')'), FileHandler.selectionUE(etudiant));
        }
        return map;
    }

    /**
     *  Cette méthode renvoie la liste de numéro des étudiants inscrits
     *  @return liste constituée de tous les numéros étudiants inscrits
     */
    public List<String> getNumEtudiants(String path) {
        File fichier = new File(path);
        if (!fichier.exists())
            return null;
        List<String> listeNumEtudiants = new ArrayList<>();

        for(Etudiant etudiant: FileHandler.etuInscrits(path)) {
            listeNumEtudiants.add(etudiant.getNumEtudiant());
        }
        return listeNumEtudiants;
    }



    /**
     *
     * @param s1 le chemin du S1
     * @param s2 le chemin du S2
     * @param s3 le chemin du S3
     * @param s4 le chemin du S4
     * @return La liste de Map des fichiers lus
     */
    public List<Map<String, List<String>>> lireTout(String s1, String s2, String s3, String s4) {
        return FileHandler.lireTout(s1,s2,s3,s4);
    }

    public void setFileHandler(GestionnaireDeFichiers fileHandler) {
        FileHandler = fileHandler;
    }
}
