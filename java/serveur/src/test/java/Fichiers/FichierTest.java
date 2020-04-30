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
    private HashMap<String,String> descriptionUE;
    private Etudiant etudiant;
    private String semestreTest;
    private String prerequisTest;
    private String predefiniTest;
    private String baseDonnee;
    private String ecrireFichierTest;
    private String ecrireInfoEtuTest;
    private String ecrireDansFichierListeTest;
    private String baseDonneEtuTest;
    private String descrictionUETest;

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
        descrictionUETest = "DescriptionUETest.txt";
    }

    /**
     * On vérifie que la méthode "lireFichier" créer bien une HashMap<String, List<String>>
     *     avec un fichier de type semestre (de test) passé en paramètre
     */
    @Test
    public void lireFichierTest_TypeSemestre() {
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


    /**
     * On vérifie que la méthode "lireFichier" créer bien une HashMap<String, List<String>>
     *     avec un fichier de type prérequis (de test) passé en paramètre
     */
    @Test
    public void lireFichierTest_TypePrerequis() {
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

    /**
     * On vérifie que la méthode "lireFichierPredefini" créer bien une Map<String, Map<Integer, List<String>>>
     *     avec un fichier de parcours predefini (de test) en paramètre.
     */
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


    /**
     * On vérifie que la méthode "lireTout" créer bien une List<Map<String, List<String>>>
     *     avec les fichiers de semesres (de test) passé en paramètre.
     */
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


    /**
     * On vérifie la bonne construction des prérequis avec un fichier de prérequis de test.
     */
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


    /**
     * On vérifie que la méthode "ecrireDansFichier" écrit bien dans un fichier de test le numéro d'étudiant,
     * et le mot de passe passé en paramètre.
     * @throws IOException
     */
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


    /**
     * On vérifie que la méthode "EnregistrerInfoEtudiant" enregistre bien les informations d'un étudiant
     * dans un fichier de test. Les informations d'un étudiant sont son numéro d'étudiant, son nom,
     * son prénom, sa date de naissance, et son mot de passe.
     * @throws IOException
     */
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

    /**
     * On vérifie si l'étudiant existe bien dans le fichier de test "BaseDonneEtuTest.txt"
     */
    @Test
    public void getEtudiantTest() {
        Etudiant etu;
        etu = fichiers.getEtudiant(baseDonneEtuTest, "ad123456");

        // On test avec le premier etudiant contenus dans le fichier "BaseDonneeEtuTest.txt"
        assertEquals(etu.getNumEtudiant(), "ad123456");
        assertEquals(etu.getNom(), "Simpson");
        assertEquals(etu.getPrenom(), "Homer");
        assertEquals(etu.getDateNaissance(), LocalDate.of(1986, 04, 30));
        assertEquals(etu.getMotDePasse(), "MDP_Simpson");

        etu = fichiers.getEtudiant(baseDonneEtuTest, "ad111111");
        // On test avec le deuxième etudiant contenus dans le fichier "BaseDonneeEtuTest.txt"
        assertEquals(etu.getNumEtudiant(), "ad111111");
        assertEquals(etu.getNom(), "nomTest2");
        assertEquals(etu.getPrenom(), "prenomTest2");
        assertEquals(etu.getDateNaissance(), LocalDate.of(2000, 01, 01));
        assertEquals(etu.getMotDePasse(), "MDP2");
    }


    /**
     * On vérifie que la méthode "EcrireDansFichierListe" écrit bien la liste passé en paramètre
     * dans un fichier de test.
     * @throws IOException
     */
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


    /**
     * On vérifie si on arrive a trouver un étudiant dans la base de données de test avec
     * son numéro d'etudiant et son mot de passe
     */
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


    /**
     * On vérifie si le numéro d'étudiant est enregistré dans la base de données de test
     * (i.e. si l'étudiant est inscrit ou non)
     *
     */
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


    /**
     * On vérifie que la liste des étudiants obtenue grace a la méthode "etuInscrits" est correcte
     */
    @Test
    public void etuInscritsTest() {
        List<Etudiant> liste = fichiers.etuInscrits(baseDonneEtuTest);
        // Premier étudiant
        assertEquals(liste.get(0).getNumEtudiant(), "ad123456");
        assertEquals(liste.get(0).getNom(), "Simpson");
        assertEquals(liste.get(0).getPrenom(), "Homer");
        assertEquals(liste.get(0).getDateNaissance(), LocalDate.of(1986, 04, 30));
        assertEquals(liste.get(0).getMotDePasse(), "MDP_Simpson");
        // Deuxième étudiant
        assertEquals(liste.get(1).getNumEtudiant(), "ad111111");
        assertEquals(liste.get(1).getNom(), "nomTest2");
        assertEquals(liste.get(1).getPrenom(), "prenomTest2");
        assertEquals(liste.get(1).getDateNaissance(), LocalDate.of(2000, 01, 01));
        assertEquals(liste.get(1).getMotDePasse(), "MDP2");
        // Troisième étudiant
        assertEquals(liste.get(2).getNumEtudiant(), "ad000000");
        assertEquals(liste.get(2).getNom(), "nomTest3");
        assertEquals(liste.get(2).getPrenom(), "prenomTest3");
        assertEquals(liste.get(2).getDateNaissance(), LocalDate.of(1800, 12, 12));
        assertEquals(liste.get(2).getMotDePasse(), "MDP3");
    }


    /**
     * On vérifie que la méthode "selectionUE" créer bien une Liste<String> des UE choisie par l'étudiant
     * passer en paramètre.
     * Pour réaliser ce test nous avons crée un objet Etudiant en l'initialisant avec le numéro d'étudiant :
     * "src/test/resources/ListeUEetudiantTest", car les listes des UEs des étudiants ont un nom sous la forme
     * numéroEtudiant.txt (la méthode "selectionUE" se charge de rajouter le ".txt"). De ce fait le fichier pointé
     * est "ListeUEetudiantTest.txt" car on rentre son chemin comme numéro d'étudiant.
     */
    @Test
    public void selectionUETest() {
        Etudiant etudiant = new Etudiant("src/test/resources/ListeUEetudiantTest");
        List<String> liste = fichiers.selectionUE(etudiant);
        // On vérifie les matière dans le fichier
        assertEquals(liste.get(0), "Bases de l'informatique");
        assertEquals(liste.get(1), "Introduction à l'informatique par le web");
        assertEquals(liste.get(2), "Structures de données et programmation C");
        assertEquals(liste.get(3), "Fondements 1");
        assertEquals(liste.get(4), "Méthodes : approche continue");
        assertEquals(liste.get(5), "Economie-Gestion S1");
        assertEquals(liste.get(6), "Intro R");
        assertEquals(liste.get(7), "Mécanique 1");
    }


    /**
     * On vérifie la bonne description des UEs contenue dans HashMap<String,String> que crée la méthode "descriptionUE".
     */
    @Test
    public void descriptionUETest() {
        // Données de test
        String informatique = "Cours de découverte de la science informatique à travers de thématiques concrètes issues du web.\n" +
                "Calendrier\n" +
                "Le cours se déroule sur 9 semaines et comprend :\n" +
                "9 séances en amphi\n" +
                "9 séances de TD\n" +
                "9 séances de TP\n" +
                "Contenu du cours :\n" +
                "L’objectif de ce cours est de donner un aperçu de ce à quoi peut ressembler la science informatique, tant d’un point de vue pratique que théorique.\n" +
                "Quelques thématiques et objectifs du cours :\n" +
                "couleurs et traitement d’images (basique)\n" +
                "premiers pas en langage HTML et CSS\n" +
                "notions de matériel informatique\n" +
                "introduction aux graphes\n" +
                "remise à niveau pour code élémentaire (for, if)\n" +
                "cryptographie et sécurité (élémentaires)\n" +
                "Les notions vues en cours sont travaillées en TD et mises en pratique en TP sur ordinateur.\n" +
                "Remarque\n" +
                "Ce cours est destiné à l’ouverture à la science informatique. Le cours SPUF10 (Bases de l’informatique) est davantage recommandé pour les étudiants se sachant destinés à une licence informatique. Il est impossible de choisir simultanément ce cours et Bases de l’informatique (SPUF10), le choix est exclusif.\n" +
                "Modalités de contrôle des connaissances\n" +
                "un contrôle continu (CC)\n" +
                "évaluation des TP (TP)\n" +
                "un contrôle continu terminal (CCT)\n" +
                "la note finale est max(0,4 CC + 0,2 TP + 0,4 CCT, CCT)\n";

        String electro = "Responsable: David Mary Cours : 15 h TD : 24h\n" +
                "Charges électriques, Champ électrique, Potentiel électrique, forces conservatives, Théorème de Gauss, Electrodynamique\n";

        String math = "Responsable: Pascal Baldi Cours : 20 h TD : 30 h\n" +
                "Ondes électromagnétiques, sources, propagation, interactions lumière-matière, propriétés des matériaux, anisotropie .\n";

        String materiaux = "4 ECTS    CM 20/ TD 25\n";

        // Création du HashMap
        descriptionUE = fichiers.descriptionUE(descrictionUETest);

        // Vérification du HashMap
        assertEquals(descriptionUE.get("Matériaux II : Chimie systématique"), materiaux);
        assertEquals(descriptionUE.get("Mathématiques pour la Physique 1"), math);
        assertEquals(descriptionUE.get("Electromagnétisme 1"), electro);
        assertEquals(descriptionUE.get("Introduction à l'informatique par le web"), informatique);
    }







}