package metier;

import java.util.HashMap;
import java.util.List;

/**
 * Permet de faire un HashMap de la liste des matières par semestre
 * Key (String) : Domaine de la matière (Informatiqque, Mathématiques, ...)
 * Value (List<String>) : Liste des matières correspondant a son domaine (Bases de linfo, ...)
 */
public class ListeSemestre {

    /**
     * @return HashMap
     */
    public HashMap<String, List<String>> getMapUE() {
        return mapUE;
    }

    /**
     * Ajoute un élément au HashMap
     * @param key Domaine de la matière
     * @param value Liste des matières correspondant a son domaine
     */
    public void add(String key,List<String> value) {
        mapUE.put(key,value);
    }

    /**
     * Permet de remplacer le HashMap
     * @param mapUE Nouveau HashMap
     */
    public void setMapUE(HashMap<String, List<String>> mapUE) {
        this.mapUE = mapUE;
    }

    HashMap<String, List<String>> mapUE = new HashMap<>();
}
