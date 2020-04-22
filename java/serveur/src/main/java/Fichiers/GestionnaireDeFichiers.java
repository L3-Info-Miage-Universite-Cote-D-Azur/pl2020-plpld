package Fichiers;

import metier.ListeSemestre;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static constantes.Net.*;

public class GestionnaireDeFichiers {
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

    public Map<String, Map<Integer, List<String>>> lireFichierPredefini(String fichier) {
        //La map suivante contient en clé le nom du parcours prédéfini et en valeur une autre map ayant pour clé le num du semestre et pour valeur la liste des UE prédéfinis pour ce semestre
        Map<String, Map<Integer, List<String>>> Predefini = new HashMap();
        int numSemestre = 1;
        BufferedReader br;
        String previousKey = null;
        try{
            br = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fichier),"UTF-8"));
            String line = br.readLine();
            while(line != null){
                if(line.contains("$$")) {
                    line = line.replace("$$","");
                    numSemestre = Integer.parseInt(line);
                    Predefini.get(previousKey).put(numSemestre, new ArrayList<>());
                }
                else if(line.contains("$")){
                    line = line.replace("$","");
                    previousKey = line;
                    numSemestre = 1;
                    Predefini.put(previousKey, new HashMap<>());
                }
                else{
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

    public List<Map<String, List<String>>> lireTout(String S1, String S2, String S3, String S4) {
        List ListeUE = new ArrayList<>();
        ListeUE.add(lireFichier(S1));
        ListeUE.add(lireFichier(S2));
        ListeUE.add(lireFichier(S3));
        ListeUE.add(lireFichier(S4));
        return ListeUE;
    }

    public HashMap<String, List<String>> constructionPrerequis(String S1, String S2, String S3, String S4, String Prerequis)
    {
        ListeSemestre listePrerequis = new ListeSemestre();
        List<Map<String,List<String>>> listofMaps = new ArrayList<>();
        listofMaps.add(lireFichier(S1));
        listofMaps.add(lireFichier(S2));
        listofMaps.add(lireFichier(S3));
        listofMaps.add(lireFichier(S4));

        for(int i = 0; i < listofMaps.size();i++) {
            for (Map.Entry<String, List<String>> entry : listofMaps.get(i).entrySet()) {
                for(String str :  entry.getValue())
                {
                    listePrerequis.add(str, new ArrayList<>());
                }
            }
        }

        for(Map.Entry<String, List<String>> entry : lireFichier(Prerequis).entrySet())
        {
            listePrerequis.add(entry.getKey(),entry.getValue());
        }
        return listePrerequis.getMapUE();
    }

    public void EcrireDansFichier(String fichier,String str) throws IOException {
        FileWriter fw = new FileWriter(fichier, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        out.println(str);
        out.flush();
        out.close();
    }
    public void EcrireDansFichierListe(String fichier,List<String> list) throws IOException {

        FileWriter fw = new FileWriter(fichier, true);
        BufferedWriter bw = new BufferedWriter(fw);
        PrintWriter out = new PrintWriter(bw);
        for(String str : list)
            out.println(str);
        out.flush();
        out.close();
    }

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



 /*   public void ecrireParcours(String numEtudiant,Map<String,List<String>>)
    {

        Map<String, Map<String,List<String>>> choixEtudiant;

    }*/
}
