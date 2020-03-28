package Fichiers;

import constantes.Net;
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
    public HashMap<String, List<String>> constructionPrerequis()
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

        for(Map.Entry<String, List<String>> entry : lireFichier(FICHIER_PREREQUIS).entrySet())
        {
            listePrerequis.add(entry.getKey(),entry.getValue());
        }


        return listePrerequis.getMapUE();

    }
    public void EcrireDansFichier(String fichier,String str) throws IOException {


        try(FileWriter fw = new FileWriter(fichier, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(str);

        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }



    }

    public Boolean trouverEtudiant(String logs)
    {
        BufferedReader br;
        try
        {
            br = new BufferedReader(new FileReader(BD));
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
