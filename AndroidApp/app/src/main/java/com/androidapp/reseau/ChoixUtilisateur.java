package com.androidapp.reseau;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 *  Implémentation du choix de l'utilisateur.
 *  Objet convertible en JSON pour que le serveur puisse recevoir l'information
 *
 */
public class ChoixUtilisateur implements ToJSON {
    /**
     *  La liste des matières qui sont choisies par l'utilisateur
     */
    private List<Matiere> Choix;

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
        for (String UE : new ArrayList<String>(Arrays.asList(S.split(",")))) {
            Choix.add(new Matiere(UE));
        }
        ;
    }

    /**
     *  Retourne la liste des matières sélectionnées par l'utilisateur
     */
    public List<Matiere> getChoix() {
        return Choix;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject identité = new JSONObject();
        try {
            identité.put("liste choisie", getChoix().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return identité;
    }

    @Override
    public String toString() {
        return Choix.toString();
    }
}