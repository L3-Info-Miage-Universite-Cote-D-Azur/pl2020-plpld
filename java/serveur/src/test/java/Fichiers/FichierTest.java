package Fichiers;

import static org.junit.Assert.*;
import metier.Etudiant;
import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FichierTest {

    private GestionnaireDeFichiers fichiers;
    private HashMap<String, List<String>> hashMapS1;
    private HashMap<String, List<String>> hashMapPrerequis;
    private Map<String, Map<Integer, List<String>>> mapPredefini;
    private HashMap<String, List<String>> constructionPrerequis;
    private List<Map<String, List<String>>> listeToutUE;
    private Etudiant etudiant;
    private String semestreTest;
    private String prerequisTest;
    private String predefiniTest;
    private String baseDonnee;
    private String ecrireFichierTest;
    private String ecrireInfoEtuTest;
    private String ecrireDansFichierListeTest;
    private String baseDonneEtuTest;

    @Before
    public void setup() {
        fichiers = new GestionnaireDeFichiers();
        etudiant = new Etudiant("Simpson", "Homer", "ad123456", LocalDate.of(1986, 04, 30), "MDP_Simpson");
        semestreTest = "SemestreTest.txt";
        prerequisTest = "PrerequisTest.txt";
        predefiniTest = "PredefiniTest.txt";
        baseDonnee = "src/test/resources/BaseDonneeTest.txt";
        ecrireFichierTest = "src/test/resources/EcrireFichierTest.txt";
        ecrireInfoEtuTest = "src/test/resources/EcrireInfoEtuTest.txt";
        ecrireDansFichierListeTest = "src/test/resources/EcrireDansFichierListeTest.txt";
        baseDonneEtuTest = "src/test/resources/BaseDonneeEtuTest.txt";
    }


    @Test
    public void lireFichierSemestreTest() {
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
    public void lireFichierPrerequisTest() {
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
    public void lireFichierPredefiniTest() {
        // On vérifie un fichier type parcours predefini
        mapPredefini = fichiers.lireFichierPredefini(predefiniTest);

        // La taille est 1 car il y a que le parcours d'informatique
        assertEquals(mapPredefini.size(), 1);

        // On vérifie que le parcours est bien Informatique
        assertTrue(mapPredefini.containsKey("Informatique"));

        // Semestre 1
        assertEquals(mapPredefini.get("Informatique").get(1).get(0), "Bases de l'informatique");
        assertEquals(mapPredefini.get("Informatique").get(1).get(1), "Introduction à l'informatique par le web");

        // Semestre 2
        assertEquals(mapPredefini.get("Informatique").get(2).get(0), "System 1. Unix et progra shell");
        assertEquals(mapPredefini.get("Informatique").get(2).get(1), "Programmation impérative");

        // Semestre 3
        assertEquals(mapPredefini.get("Informatique").get(3).get(0), "Structures de données et programmation C");
        assertEquals(mapPredefini.get("Informatique").get(3).get(1), "Bases de données");
        assertEquals(mapPredefini.get("Informatique").get(3).get(2), "Outils formels pour l'informatique");
        assertEquals(mapPredefini.get("Informatique").get(3).get(3), "Algo & prog avec R");

        // Semestre 4
        assertEquals(mapPredefini.get("Informatique").get(4).get(0), "Algorithmique 1");
        assertEquals(mapPredefini.get("Informatique").get(4).get(1), "Réseaux et télécommunication");
        assertEquals(mapPredefini.get("Informatique").get(4).get(2), "Systèmes 2 : Mécanisme internes des systèmes d'exploitation");
        assertEquals(mapPredefini.get("Informatique").get(4).get(3), "Introduction aux systèmes intelligents");
        assertEquals(mapPredefini.get("Informatique").get(4).get(4), "Technologie du Web");
    }


    @Test
    public void lireToutTest() {
        // On vérifie que la méthode "lireTout" construit bien une liste avec les 4 semestre (les 4 fichier pour le test sont identique, donc les 4 elements de la liste doivent eux aussi être identique)
        listeToutUE = fichiers.lireTout(semestreTest, semestreTest, semestreTest, semestreTest);

        // On vérifie que les matières correspondent bien pour le premier semestre (index 0 de la liste)
        assertEquals(listeToutUE.get(0).get("MIASHS"), new ArrayList<>() {{ add("Economie-Gestion S1"); add("Intro R");}});
        assertEquals(listeToutUE.get(0).get("Géographie"), new ArrayList<>() {{ add("Decouverte 1"); add("Decouverte 2"); add("Disciplinaire 1"); add("Découverte 4");}});
        assertEquals(listeToutUE.get(0).get("Science de la vie"), new ArrayList<>() {{ add("Physiologie. Neurologie. Enzymologie. Methodologie"); add("Diversité du Vivant");}});
        assertEquals(listeToutUE.get(0).get("Informatique"), new ArrayList<>() {{ add("Bases de l'informatique"); add("Introduction à l'informatique par le web"); add("Structures de données et programmation C"); add("Bases de donnée"); add("System 1. Unix et progra shell"); add("Programmation impérative");}});
        assertEquals(listeToutUE.get(0).get("Mathématiques"), new ArrayList<>() {{ add("Fondements 1"); add("Méthodes : approche continue"); add("Complements 1"); add("Méthodes : Mathématiques et ingénierie"); add("Méthodes : approche géométrique"); }});
        assertEquals(listeToutUE.get(0).get("Chimie"), new ArrayList<>() {{ add("Structure Microscopique de la Matière"); add("Réactions et reactivites chimiques");}});
        assertEquals(listeToutUE.get(0).get("Science de la Terre"), new ArrayList<>() {{ add("Structure et dynamique de la terre"); add("Atmosphère; Océan; Climats");}});
        assertEquals(listeToutUE.get(0).get("CLE 1D (Continuum Licence Enseignement)"), new ArrayList<>() {{ add("Enseignements fondamentaux à l'école primaire 1"); }});
        assertEquals(listeToutUE.get(0).get("Physique"), new ArrayList<>() {{ add("Mécanique 1"); }});
        assertEquals(listeToutUE.get(0).get("UE facultative"), new ArrayList<>() {{ add("Projet FabLab"); }});
        assertEquals(listeToutUE.get(0).get("Electronique"), new ArrayList<>() {{ add("Electronique numerique - Bases"); add("Electronique analogique"); add("Communication sans fil");}});

        // Les 4 semestres sont identique car ont a mis le même fichier
        assertEquals(listeToutUE.get(0), listeToutUE.get(1));
        assertEquals(listeToutUE.get(1), listeToutUE.get(2));
        assertEquals(listeToutUE.get(2), listeToutUE.get(3));
    }


    @Test
    public void constructionPrerequisTest() {
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
    public void ecrireDansFichierTest() throws IOException {
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
    public void EnregistrerInfoEtudiantTest() throws IOException {
        //On supprime le fichier dans lequel on va écrire (s'il existe)
        File fichierEcrit = new File(ecrireInfoEtuTest);
        fichierEcrit.delete();

        fichiers.EnregistrerInfoEtudiant(ecrireInfoEtuTest, etudiant);
        BufferedReader br;
        br = new BufferedReader(new FileReader(ecrireInfoEtuTest));
        String line = br.readLine();

        // Verification du numéro étudiant
        assertEquals(line, etudiant.getNumEtudiant());

        // Verification du nom
        line = br.readLine();
        assertEquals(line, etudiant.getNom());

        // Verification du prenom
        line = br.readLine();
        assertEquals(line, etudiant.getPrenom());

        // Verification de la date de naissance
        line = br.readLine();
        assertEquals(line, etudiant.getDateNaissance().toString());

        // Verification du mot de passe
        line = br.readLine();
        assertEquals(line, etudiant.getMotDePasse());

        br.close();
    }


    @Test
    public void getEtudiantTest() {
        // On vérifie si l'étudiant existe bien dans le fichier "BaseDonneEtuTest.txt"
        Etudiant etu;
        etu = fichiers.getEtudiant(baseDonneEtuTest, "ad123456");

        // On test avec des valeurs contenus dans le fichier "BaseDonneeEtuTest.txt"
        assertEquals(etu.getNumEtudiant(), "ad123456");
        assertEquals(etu.getNom(), "Simpson");
        assertEquals(etu.getPrenom(), "Homer");
        assertEquals(etu.getDateNaissance(), LocalDate.of(1986, 04, 30));
        assertEquals(etu.getMotDePasse(), "MDP_Simpson");
    }


    @Test
    public void EcrireDansFichierListeTest() throws IOException {
        //On supprime le fichier dans lequel on va écrire (s'il existe)
        File fichierEcrit = new File(ecrireDansFichierListeTest);
        fichierEcrit.delete();

        // On remplis la liste avec 100 lignes de valeurs "EtudiantTest n° 1", "EtudiantTest n° 2", etc...
        ArrayList<String> liste = new ArrayList<String>() {{
            for (int i = 0; i < 100; i++) {
                add("EtudiantTest n° " + i);
            }
        }};

        fichiers.EcrireDansFichierListe(ecrireDansFichierListeTest, liste);

        // On parcours le fichier et on vérifie ce qui a été écrit précédemment
        BufferedReader br;
        br = new BufferedReader(new FileReader(ecrireDansFichierListeTest));
        String line = br.readLine();
        for (int i = 0; i <100; i++) {
            assertEquals(line, "EtudiantTest n° " + i);
            assertNotEquals(line , "EtudiantTest n° " + (i + 1));
            assertNotEquals(line , "EtudiantTest n° " + (i - 1));
            line = br.readLine();
        }
        br.close();
    }


    @Test
    public void trouverEtudiantTest() {
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


    @Test
    public void etuDejaInscritTest() {
        //On test avec les vrai numéro d'étudiant
        assertTrue(fichiers.etuDejaInscrit(baseDonnee,"nk123456"));
        assertTrue(fichiers.etuDejaInscrit(baseDonnee,"pp444444"));
        assertTrue(fichiers.etuDejaInscrit(baseDonnee,"ad000000"));
        assertTrue(fichiers.etuDejaInscrit(baseDonnee,"gt206999"));
        assertTrue(fichiers.etuDejaInscrit(baseDonnee,"jp111111"));

        //On change un ou plusieurs caractères
        assertFalse(fichiers.etuDejaInscrit(baseDonnee,"nk123450"));
        assertFalse(fichiers.etuDejaInscrit(baseDonnee,"pp444449"));
        assertFalse(fichiers.etuDejaInscrit(baseDonnee,"ad000111"));
        assertFalse(fichiers.etuDejaInscrit(baseDonnee,"gt206000"));
        assertFalse(fichiers.etuDejaInscrit(baseDonnee,"jp222222"));
    }
}