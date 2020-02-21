package metier;

import org.json.JSONException;
import org.json.JSONObject;

public class Matiere implements ToJSON {
    private String nom;

    public Matiere() {
        this("nom par défaut");
    }

    public Matiere(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject identité = new JSONObject();
        try {
            identité.put("nom", getNom());
        } catch (JSONException e) {
            e.printStackTrace();}
        return identité;
    }

    public String toString() {
        return this.getNom();
    }
}

