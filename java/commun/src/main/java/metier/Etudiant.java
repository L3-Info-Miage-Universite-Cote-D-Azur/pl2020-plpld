package metier;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.json.JSONException;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Etudiant implements ToJSON {
    private String nom;
    private String prenom;
    private String numEtudiant ;
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dateNaissance;
    private String motDePasse;

    public Etudiant(String numEtudiant) {
        this.numEtudiant = numEtudiant;
    }

    public Etudiant(String numEtudiant, LocalDate dateNaissance) {
        this.numEtudiant = numEtudiant;
        this.dateNaissance = dateNaissance;
    }


    public Etudiant(String nom, String prenom, String numEtudiant, LocalDate dateNaissance, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.numEtudiant = numEtudiant;
        this.dateNaissance = dateNaissance;
        this.motDePasse = motDePasse;
    }

    public Etudiant(String nom, String prenom, String numEtudiant, String dateNaissance, String motDePasse) {
        this.nom = nom;
        this.prenom = prenom;
        this.numEtudiant = numEtudiant;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.dateNaissance = LocalDate.parse(dateNaissance, formatter);
        this.motDePasse = motDePasse;
    }

    public Etudiant() {}

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumEtudiant() {
        return numEtudiant;
    }

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }


    public String getMotDePasse() {
        return motDePasse;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNumEtudiant(String numEtudiant) {
        this.numEtudiant = numEtudiant;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setMotDePasse(String mdp) {
        motDePasse = mdp;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject Etudiant = new JSONObject();
        try{
            Etudiant.put("nom",getNom());
            Etudiant.put("prenom",getPrenom());
            Etudiant.put("numEtudiant",getNumEtudiant());
            Etudiant.put("dateNaissance",getDateNaissance().toString());
            Etudiant.put("motDePasse",getMotDePasse());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Etudiant;
    }

    public String toString() {
        return this.getNumEtudiant();
    }
}