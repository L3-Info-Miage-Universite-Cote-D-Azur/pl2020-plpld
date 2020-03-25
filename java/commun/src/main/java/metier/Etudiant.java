package metier;

import java.time.LocalDate;

public class Etudiant {
    final private String nom;
    final private String prénom;
    final private String numEtudiant;
    final private LocalDate dateNaissance;
    private String motDePasse;
    public Etudiant(String nom, String prénom, String numEtudiant, LocalDate dateNaissance, String motDePasse) {
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