package Fichiers;

import constantes.Net;
import metier.Etudiant;
import metier.ListeSemestre;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *  Classe GestionnaireDeFichiers qui s'occupe de lire les données reçues et les écrire dans un fichier
 */
public class GestionnaireDeFichiers {


    /**
     * Cette méthode prend en parametre le chemin du fichier à lire puis sérialise les données contenues dans le fichier en une map
     * Cette map est ensuite envoyée au client.
     * @param fichier Le fichier a lire
     * @return une map <String,List<String>> une map des UE des semestres
     */
    public HashMap<String, List<String>> lireFichier(String fichier) {
        ListeSemestre listeSemestre = new ListeSemestre();
        String previousKey = null;
        BufferedReader br;
        try{
            br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fichier),"UTF-8"));
            String line = br.readLine();
            while(line != null){
                if(line.contains("$")){
                    line = line.replace("$","");
                    listeSemestre.add(line,new ArrayList<String>());
                    previousKey = line;
                }
                else{
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
     *  Cette méthode lit le fichier des parcours prédéfini, son fonctionnement est similaire à la méthode précédente
     * @param fichier le fichier à lire
     * @return Une map serialisée qui comptera comme un parcours prédéfini puis sera envoyé au client
     */
    public Map<String, Map<Integer, List<String>>> lireFichierPredefini(String fichier) {
        //La map suivante contient en clé le nom du parcours prédéfini et en valeur une autre map ayant pour clé le num du semestre et pour valeur la liste des UE prédéfinis pour ce semestre
        Map<String, Map<Integer, List<String>>> Predefini = new HashMap();
        int numSemestre = 1;
        BufferedReader br;
        String previousKey = null;
        try {
            br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fichier), "UTF-8"));
            String line = br.readLine();
            while (line != null) {
                if (line.contains("$$")) {
                    line = line.replace("$$", "");
                    numSemestre = Integer.parseInt(line);
                    Predefini.get(previousKey).put(numSemestre, new ArrayList<>());
                } else if (line.contains("$")) {
                    line = line.replace("$", "");
                    previousKey = line;
                    numSemestre = 1;
                    Predefini.put(previousKey, new HashMap<>());
                } else {
                    Predefini.get(previousKey).get(numSemestre).add(line);
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Envoi de la map prédéfini " + Predefini.toString());
        return Predefini;
    }

    /**
     * Cette méthode prend en paramètre les fichiers des 4 semestres et les lit, chaque map est ajoutée à une liste qui est ensuite retournée
     * @param S1
     * @param S2
     * @param S3
     * @param S4
     * @return
     */
    public List<Map<String, List<String>>> lireTout(String S1, String S2, String S3, String S4) {
        List ListeUE = new ArrayList<>();
        ListeUE.add(lireFichier(S1));
        ListeUE.add(lireFichier(S2));
        ListeUE.add(lireFichier(S3));
        ListeUE.add(lireFichier(S4));
        return ListeUE;
    }

    /**
     *  Cette méthode sert à la mise en place des prérequis.
     *  Tout d'abord, les fichiers de tous les semestre sont lu.
      * @param S1
     * @param S2
     * @param S3
     * @param S4
     * @param Prerequis
     * @return une map des prérequis avec comme clé les UE et comme valeurs les UE qui sont nécessaires à la selection de cette UE par l'étudiant
     */
    public HashMap<String, List<String>> constructionPrerequis(String S1, String S2, String S3, String S4, String Prerequis)
    {
        ListeSemestre listePrerequis = new ListeSemestre();

        // Une nouvelle liste de map est crée avec tous les semestres.
        List<Map<String,List<String>>> listofMaps = new ArrayList<>();
        listofMaps.add(lireFichier(S1));
        listofMaps.add(lireFichier(S2));
        listofMaps.add(lireFichier(S3));
        listofMaps.add(lireFichier(S4));

        for(int i = 0; i < listofMaps.size();i++) {
            for (Map.Entry<String, List<String>> entry : listofMaps.get(i).entrySet()) {
                for(String str :  entry.getValue())
                {
                    // une nouvelle map est crée avec comme valeur toutes les UE de toutes les maps dans notre liste
                    listePrerequis.add(str, new ArrayList<>());
                }
            }
        }

        for(Map.Entry<String, List<String>> entry : lireFichier(Prerequis).entrySet())
        {
            // Les UE qui ont des prérequis sont ensuite complétées
            listePrerequis.add(entry.getKey(),entry.getValue());
        }
        return listePrerequis.getMapUE();
    }

    /**
     *  Cette méthode prend un chemin et une chaine de caractère en paramètre puis écrit cette caractère dans le fichier
     *  Si le fichier n'existe pas,il est crée sinon la chaine de caractère est ajoutée au contenu du fichier
     * @param fichier le fichier où l'on écrit
     * @param str le string qui doit être écrit dans le fichier
     * @throws IOException
     */
    public void EcrireDansFichier(String fichier,String str) throws IOException {
        FileWriter fw = new FileWriter(fichier, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        out.println(str);
        out.flush();
        out.close();
    }

    public void EnregistrerInfoEtudiant(String fichier, Etudiant etudiant) throws IOException {
        FileWriter fw = new FileWriter(fichier, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        out.println(etudiant.getNumEtudiant());
        out.println(etudiant.getNom());
        out.println(etudiant.getPrenom());
        out.println(etudiant.getDateNaissance().toString());
        out.println(etudiant.getMotDePasse());
        out.flush();
        out.close();
    }

    public Etudiant getEtudiant(String fichier,String numEtudiant)
    {
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(fichier));
            String line = br.readLine();
            while(line != null)
            {
                System.out.println(line);
                if(line.equals(numEtudiant))
                {
                   String num = line;
                   List<String> tmpList = new ArrayList<>();
                   for(int i = 0; i < 5; i ++) {
                       line = br.readLine();
                       tmpList.add(line);
                   }
                   return new Etudiant(tmpList.get(0),tmpList.get(1),num, LocalDate.parse(tmpList.get(2)),tmpList.get(3));
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *  Cette méthode sert à la désérialisation d'une liste de chaine de caractères afin d'écrire dans le fichier chaque élément de la liste
     *  et non pas la liste tout entière
     * @param fichier le chemin du fichier
     * @param list  une liste
     * @throws IOException
     */
    public void EcrireDansFichierListe(String fichier,List<String> list) throws IOException {

        FileWriter fw = new FileWriter(fichier, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        for(String str : list)
            out.println(str);
        out.flush();
        out.close();
    }

    /**
     *  Cette méthode compare la chaine de caractère passée en paramètre avec les informations contenues dans la BD
     *  Si la chaine de caractère est présente dans le fichier, l'étudiant est déjà enregistré, la méthode renvoie vrai et la connexion est autorisée,
     *  faux sinon
     * @param fichier le chemin du fichier
     * @param logs  les identifiants de l'étudiant
     * @return un boolean pour savoir si les identifiants passés en paramètre correspondent à ceux dans la BD
     */
    public Boolean trouverEtudiant(String fichier, String logs)
    {
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(fichier));
            String line = br.readLine();
            while(line != null)
            {
                System.out.println(line);
                if(line.equals(logs))
                {
                    br.close();
                    return true;
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public Boolean etuDejaInscrit(String fichier, String numEtudiant) {

        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(fichier));

            String line = br.readLine();
            while(line != null)
            {
                int i = line.indexOf(" ");
                System.out.println(line);
                if(line.substring(0,i).equals(numEtudiant))
                {
                    br.close();
                    return true;
                }
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;

        }

}
