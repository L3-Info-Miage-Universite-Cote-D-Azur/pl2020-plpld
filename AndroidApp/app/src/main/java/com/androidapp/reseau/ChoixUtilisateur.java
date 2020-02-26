package com.androidapp.reseau;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class ChoixUtilisateur implements ToJSON {
    private List<Matiere> Choix;

    public ChoixUtilisateur(List<Matiere> UserChoice) {
        Choix = UserChoice;
    }

    public ChoixUtilisateur(String S) {
        Choix = new ArrayList<Matiere>();
        S = S.replace("[", "");
        S = S.replace("]", "");
        for (String UE : new ArrayList<String>(Arrays.asList(S.split(",")))) {
            Choix.add(new Matiere(UE));
        }
        ;
    }

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