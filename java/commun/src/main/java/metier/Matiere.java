package metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  Implémentation d'une matière'.
 *  Objet convertible en JSON pour que le serveur puisse recevoir l'information
 */
public class Matiere implements ToJSON {
    private String nom;

    /**
     * Constructeur sans paramètre.
     * La matière prend un nom par défaut.
     */
    public Matiere() {
        this("nom par défaut");
    }

    /**
     * Constructeur prenant le nom de la matière comme paramètre
     * @param nom Nom de la matière
     */
    public Matiere(String nom) {
        this.nom = nom;
    }

    /**
     * @return Le nom de la matière
     */
    public String getNom() {
        return nom;
    }

    /**
     * Permet de changer le nom de la matière.
     * @param nom Nom de la matière
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Convertis la matière en JSONObject
     * @return JSONObject
     */
    @Override
    public JSONObject toJSON() {
        JSONObject identité = new JSONObject();
        try {
            identité.put("nom", getNom());
        } catch (JSONException e) {
            e.printStackTrace();}
        return identité;
    }

    @Override
    public String toString() {
        return this.getNom();
    }
}

