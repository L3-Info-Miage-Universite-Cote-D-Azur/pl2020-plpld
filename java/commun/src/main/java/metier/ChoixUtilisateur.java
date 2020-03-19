package metier;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  Implémentation du choix de l'utilisateur.
 *  Objet convertible en JSON pour que le serveur puisse recevoir l'information
 */
public class ChoixUtilisateur implements ToJSON {
    private List<Matiere> Choix;
    private int numSemestre;

    /**
     *  Constructeur prenant en paramètre une liste de matière.
     *  Le champ Choix sera initialisé avec ce paramètre
     * @param UserChoice  Liste de matière
     */
    public ChoixUtilisateur(List<Matiere> UserChoice) {
        Choix = UserChoice;
    }

    /**
     * Constructeur prenant en paramètre un string
     *  La chaine de caractère est analysée puis décomposée en UE afin de créer une nouvelle liste
     */
    public ChoixUtilisateur(String S) {
        Choix = new ArrayList<Matiere>();
        S = S.replace("[", "");
        S = S.replace("]", "");
        for(String UE: new ArrayList<String>(Arrays.asList(S.split(",")))) {
            Choix.add(new Matiere(UE));
        };
        numSemestre();
    }

    /**
     *  Retourne la liste des matières sélectionnées par l'utilisateur
     * @return liste des matieres
     */
    public List<Matiere> getChoix() {
        return Choix;
    }

    /**
     * Transforme la liste en objet JSON
     * @return JSONObject de la liste
     */
    @Override
    public JSONObject toJSON() {
        JSONObject liste = new JSONObject();
        try {
            liste.put("liste choisie", getChoix().toString());
        } catch (JSONException e) {
            e.printStackTrace();}
        return liste;
    }

    @Override
    public String toString() {
        return Choix.toString();
    }

    /**
     * Permet d'obtenir le numero du semestre correspondant a la liste des matieres
     */
    private void numSemestre() {
        numSemestre = Choix.get(0).getNom().charAt(1)-48;
        Choix.remove(0);
    }

    /**
     * @return le numero du semestre
     */
    public int getNumSemestre() {return numSemestre;}
}