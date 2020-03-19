package metier;

import org.json.JSONException;
import org.json.JSONObject;

/**
 *  Implémentation de l'identité de l'utilisateur.
 *  Objet convertible en JSON pour que le serveur puisse recevoir l'information
 */
public class Identité implements ToJSON {
    private String nom;

    /**
     * Constructeur sans paramètre.
     * Lutilisateur prend donc une identité par défaut.
     */
    public Identité() {
        this("nom par défaut");
    }

    /**
     * Constructeur prenant le nom de l'utilisateur comme paramètre.
     * @param nom Nom de l'utilisateur
     */
    public Identité(String nom) {
        this.nom = nom;
    }

    /**
     * @return Le nom de l'utilisateur
     */
    public String getNom() {
        return nom;
    }

    /**
     * Permet de modifier le nom de l'utilisateur.
     * @param nom Nouveau nom de l'utilisateur
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Transforme l'identité de l'utilisateur en objet JSON
     * @return JSONObject de l'identité
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

    public String toString() {
        return this.getNom();
    }
}
