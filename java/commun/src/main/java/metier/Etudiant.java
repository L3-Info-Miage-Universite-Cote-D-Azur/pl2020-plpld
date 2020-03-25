package metier;

import java.time.LocalDate;

public class Etudiant {
    final String nom;
    final String prénom;
    final String numEtudiant;
    final LocalDate dateNaissance;
    String motDePasse;
    Etudiant(String nom, String prénom, String numEtudiant, LocalDate dateNaissance, String motDePasse) {
        this.nom = nom;
        this.prénom = prénom;
        this.numEtudiant = numEtudiant;
        this.dateNaissance = dateNaissance;
        this.motDePasse = motDePasse;
    }

    public String getNom() {
        return nom;
    }

    public String getPrénom() {
        return prénom;
    }

    public String getNumEtudiant() {
        return numEtudiant;
    }

    public void setMotDePasse(String mdp) {
        motDePasse = mdp;
    }
}