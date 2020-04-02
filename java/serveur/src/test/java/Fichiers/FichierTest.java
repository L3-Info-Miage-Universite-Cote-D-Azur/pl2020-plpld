package Fichiers;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FichierTest {

    private GestionnaireDeFichiers fichiers;
    private HashMap<String, List<String>> hashMapS1;
    private HashMap<String, List<String>> hashMapPrerequis;
    private HashMap<String, List<String>> constructionPrerequis;
    private String semestreTest;
    private String prerequisTest;
    private String baseDonnee;
    private String ecrireFichierTest;

    @Before
    public void setup() {
        fichiers = new GestionnaireDeFichiers();
        semestreTest = "SemestreTest.txt";
        prerequisTest = "PrerequisTest.txt";
        baseDonnee = "src/test/resources/BaseDonneeTest.txt";
        ecrireFichierTest = "src/test/resources/EcrireFichierTest.txt";
    }


    @Test
    public void lireFichierSemestre() {
        // On vérifie un fichier type semestre
        hashMapS1 = fichiers.lireFichier(semestreTest);

        assertEquals(hashMapS1.get("Informatique").get(0), "Bases de l'informatique");
        assertEquals(hashMapS1.get("Informatique").get(1), "Introduction à l'informatique par le web");
        assertEquals(hashMapS1.get("Informatique").get(2), "Structures de données et programmation C");
        assertEquals(hashMapS1.get("Informatique").get(3), "Bases de donnée");
        assertEquals(hashMapS1.get("Informatique").get(4), "System 1. Unix et progra shell");
        assertEquals(hashMapS1.get("Informatique").get(5), "Programmation impérative");

        assertEquals(hashMapS1.get("Mathématiques").get(0), "Fondements 1");
        assertEquals(hashMapS1.get("Mathématiques").get(1), "Méthodes : approche continue");
        assertEquals(hashMapS1.get("Mathématiques").get(2), "Complements 1");
        assertEquals(hashMapS1.get("Mathématiques").get(3), "Méthodes : Mathématiques et ingénierie");
        assertEquals(hashMapS1.get("Mathématiques").get(4), "Méthodes : approche géométrique");

        assertEquals(hashMapS1.get("Chimie").get(0), "Structure Microscopique de la Matière");
        assertEquals(hashMapS1.get("Chimie").get(1), "Réactions et reactivites chimiques");

        assertEquals(hashMapS1.get("Electronique").get(0), "Electronique numerique - Bases");
        assertEquals(hashMapS1.get("Electronique").get(1), "Electronique analogique");
        assertEquals(hashMapS1.get("Electronique").get(2), "Communication sans fil");

        assertEquals(hashMapS1.get("Géographie").get(0), "Decouverte 1");
        assertEquals(hashMapS1.get("Géographie").get(1), "Decouverte 2");
        assertEquals(hashMapS1.get("Géographie").get(2), "Disciplinaire 1");
        assertEquals(hashMapS1.get("Géographie").get(3), "Découverte 4");

        assertEquals(hashMapS1.get("MIASHS").get(0), "Economie-Gestion S1");
        assertEquals(hashMapS1.get("MIASHS").get(1), "Intro R");

        assertEquals(hashMapS1.get("Physique").get(0), "Mécanique 1");

        assertEquals(hashMapS1.get("Science de la Terre").get(0), "Structure et dynamique de la terre");
        assertEquals(hashMapS1.get("Science de la Terre").get(1), "Atmosphère; Océan; Climats");

        assertEquals(hashMapS1.get("Science de la vie").get(0), "Physiologie. Neurologie. Enzymologie. Methodologie");
        assertEquals(hashMapS1.get("Science de la vie").get(1), "Diversité du Vivant");

        assertEquals(hashMapS1.get("CLE 1D (Continuum Licence Enseignement)").get(0), "Enseignements fondamentaux à l'école primaire 1");

        assertEquals(hashMapS1.get("UE facultative").get(0), "Projet FabLab");
    }


    @Test
    public void lireFichierPrerequis() {
        // On vérifie un fichier type prerequis
        hashMapPrerequis = fichiers.lireFichier(prerequisTest);

        assertEquals(hashMapPrerequis.get("Fondements 2").get(0), "Fondements 1");
        assertEquals(hashMapPrerequis.get("Complements 2").get(0), "Complements 1");
        assertEquals(hashMapPrerequis.get("Découverte 3").get(0), "Decouverte 2");
        assertEquals(hashMapPrerequis.get("Découverte 4").get(0), "Decouverte 3");
        assertEquals(hashMapPrerequis.get("Disciplinaire 2").get(0), "Disciplinaire 1");
        assertEquals(hashMapPrerequis.get("Economie-Gestion S2").get(0), "Economie-Gestion S1");
        assertEquals(hashMapPrerequis.get("Mécanique 2").get(0), "Mécanique 1");
        assertEquals(hashMapPrerequis.get("Enseignements fondamentaux à l'école primaire 2").get(0), "Enseignements fondamentaux à l'école primaire 1");
    }


    @Test
    public void constructionPrerequis() {
        //On construit les prérequis (les matières en double apparaissent qu'une seule fois)
        constructionPrerequis = fichiers.constructionPrerequis(semestreTest,semestreTest,semestreTest,semestreTest, prerequisTest);

        //On vérifie les matières sans prérequis (la valeur est une liste vide)
        assertEquals(constructionPrerequis.get("Communication sans fil"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Decouverte 1"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Decouverte 2"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Fondements 1"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Méthodes : Mathématiques et ingénierie"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Structure et dynamique de la terre"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Atmosphère; Océan; Climats"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Disciplinaire 1"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Intro R"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Bases de l'informatique"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Electronique analogique"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Complements 1"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Physiologie. Neurologie. Enzymologie. Methodologie"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Méthodes : approche continue"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Structures de données et programmation C"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("System 1. Unix et progra shell"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Mécanique 1"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Bases de donnée"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Programmation impérative"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Enseignements fondamentaux à l'école primaire 1"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Economie-Gestion S1"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Méthodes : approche géométrique"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Structure Microscopique de la Matière"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Introduction à l'informatique par le web"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Diversité du Vivant"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Réactions et reactivites chimiques"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Projet FabLab"), new ArrayList<>());
        assertEquals(constructionPrerequis.get("Electronique numerique - Bases"), new ArrayList<>());

        //On vérifie les matières avec prérequis
        assertEquals(constructionPrerequis.get("Fondements 2"), new ArrayList<>() {{add("Fondements 1");}});
        assertEquals(constructionPrerequis.get("Disciplinaire 2"), new ArrayList<>(){{add("Disciplinaire 1");}});
        assertEquals(constructionPrerequis.get("Complements 2"), new ArrayList<>(){{add("Complements 1");}});
        assertEquals(constructionPrerequis.get("Mécanique 2"), new ArrayList<>(){{add("Mécanique 1");}});
        assertEquals(constructionPrerequis.get("Découverte 3"), new ArrayList<>(){{add("Decouverte 2");}});
        assertEquals(constructionPrerequis.get("Découverte 4"), new ArrayList<>(){{add("Decouverte 3");}});
        assertEquals(constructionPrerequis.get("Enseignements fondamentaux à l'école primaire 2"), new ArrayList<>(){{add("Enseignements fondamentaux à l'école primaire 1");}});
        assertEquals(constructionPrerequis.get("Economie-Gestion S2"), new ArrayList<>(){{add("Economie-Gestion S1");}});

        //Une matières qui n'est pas dans le HashMap apparait comme NULL (et non comme une liste vide)
        assertEquals(constructionPrerequis.get("Matières"), null);
        assertNotEquals(constructionPrerequis.get("Matières"), new ArrayList<>());
    }


    @Test
    public void ecrireDansFichier() throws IOException {
        //On supprime le fichier dans lequel on va écrire (s'il existe)
        File fichierEcrit = new File(ecrireFichierTest);
        fichierEcrit.delete();

        //On écrit dans le fichier plusieurs numéro d'étudiant test
        fichiers.EcrireDansFichier(ecrireFichierTest, "pp444444 1234");
        fichiers.EcrireDansFichier(ecrireFichierTest, "jp111111 admin");
        fichiers.EcrireDansFichier(ecrireFichierTest, "nk123456 bonjourCeciEstUnTest");
        fichiers.EcrireDansFichier(ecrireFichierTest, "hc474709 LeLoupEstDansLaBergerie");
        fichiers.EcrireDansFichier(ecrireFichierTest, "hu465585 Code%Key/(Value_R+98-p)=C'estMonCode");
        fichiers.EcrireDansFichier(ecrireFichierTest, "gt206999 VoiciMonCode565@00#7775");
        fichiers.EcrireDansFichier(ecrireFichierTest, "jl546546 0000");
        fichiers.EcrireDansFichier(ecrireFichierTest, "ad000000 password");

        //On test les numéro d'étudiant écrit précédemment
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"pp444444 1234"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"jp111111 admin"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"nk123456 bonjourCeciEstUnTest"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"hc474709 LeLoupEstDansLaBergerie"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"hu465585 Code%Key/(Value_R+98-p)=C'estMonCode"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"gt206999 VoiciMonCode565@00#7775"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"jl546546 0000"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"ad000000 password"));
    }


    @Test
    public void trouverEtudiant() {
        //On test avec les vrai identifiants
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"pp444444 1234"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"jp111111 admin"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"nk123456 bonjourCeciEstUnTest"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"hc474709 LeLoupEstDansLaBergerie"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"hu465585 Code%Key/(Value_R+98-p)=C'estMonCode"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"gt206999 VoiciMonCode565@00#7775"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"jl546546 0000"));
        assertTrue(fichiers.trouverEtudiant(baseDonnee,"ad000000 password"));

        //On change un ou plusieurs caractères
        assertFalse(fichiers.trouverEtudiant(baseDonnee,"ad000001password"));
        assertFalse(fichiers.trouverEtudiant(baseDonnee,"jp111111 adminn"));
        assertFalse(fichiers.trouverEtudiant(baseDonnee,"nk123456 bonjourCeciEstUnTestIncorrect"));
        assertFalse(fichiers.trouverEtudiant(baseDonnee,"hc474709 LeLoupN'EstPasDansLaBergerie"));
        assertFalse(fichiers.trouverEtudiant(baseDonnee,"gt206999 VoiciMonCode565@00#7775__"));
        assertFalse(fichiers.trouverEtudiant(baseDonnee,"pp444 1234"));
        assertFalse(fichiers.trouverEtudiant(baseDonnee,"jl546546 000078687"));
        assertFalse(fichiers.trouverEtudiant(baseDonnee,"MMMM465585 Code%Key/(Value_R+98-p)=C'estMonCode"));
    }
}