package Fichiers;

import static constantes.Net.*;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FichierTest {

    HashMap<String, List<String>> hashMapS1;
    HashMap<String, List<String>> hashMapPrerequis;
    GestionnaireDeFichiers fichiers;

    @Before
    public void setup() {
        fichiers = new GestionnaireDeFichiers();
    }

    // On vérifie le fichier des matières du semestre 1
    @Test
    public void testS1() {
        hashMapS1 = fichiers.lireFichier(S1);

        assertEquals(hashMapS1.get("Informatique").get(0), "Bases de l'informatique");
        assertEquals(hashMapS1.get("Informatique").get(1), "Introduction à l'informatique par le web");

        assertEquals(hashMapS1.get("Mathématiques").get(0), "Fondements 1");
        assertEquals(hashMapS1.get("Mathématiques").get(1), "Méthodes : approche continue");
        assertEquals(hashMapS1.get("Mathématiques").get(2), "Complements 1");

        assertEquals(hashMapS1.get("Chimie").get(0), "Structure Microscopique de la Matière");

        assertEquals(hashMapS1.get("Electronique").get(0), "Electronique numerique - Bases");

        assertEquals(hashMapS1.get("Electronique").get(0), "Electronique numerique - Bases");

        assertEquals(hashMapS1.get("Géographie").get(0), "Decouverte 1");
        assertEquals(hashMapS1.get("Géographie").get(1), "Decouverte 2");
        assertEquals(hashMapS1.get("Géographie").get(2), "Disciplinaire 1");

        assertEquals(hashMapS1.get("MIASHS").get(0), "Economie-Gestion S1");

        assertEquals(hashMapS1.get("Physique").get(0), "Mécanique 1");

        assertEquals(hashMapS1.get("Science de la Terre").get(0), "Découverte des sciences de la terre");

        assertEquals(hashMapS1.get("Science de la vie").get(0), "Org. Mécanismes Moléculaires Cellules Eucaryotes");
        assertEquals(hashMapS1.get("Science de la vie").get(1), "Génétique. Evolution. Origine Vie et Biodiversité");

        assertEquals(hashMapS1.get("CLE 1D (Continuum Licence Enseignement)").get(0), "Enseignements fondamentaux à l'école primaire 1");

        // UE facultative a aucune matière, alors c'est une ArrayListe vide
        assertEquals(hashMapS1.get("UE facultative"), new ArrayList<>());
    }

    // On vérifie le fichier des prérequis
    @Test
    public void testPrerequis() {
        hashMapPrerequis = fichiers.lireFichier(FICHIER_PREREQUIS);

        for (Map.Entry<String, List<String>> e : hashMapPrerequis.entrySet()) {
            switch (e.getKey()) {
                case "Fondements 2" :
                    assertEquals(hashMapPrerequis.get("Fondements 2").get(0), "Fondements 1");
                case "Complements 2" :
                    assertEquals(hashMapPrerequis.get("Complements 2").get(0), "Complements 1");
                case "Découverte 3" :
                    assertEquals(hashMapPrerequis.get("Découverte 3").get(0), "Decouverte 2");
                case "Découverte 4" :
                    assertEquals(hashMapPrerequis.get("Découverte 4").get(0), "Decouverte 3");
                case "Disciplinaire 2" :
                    assertEquals(hashMapPrerequis.get("Disciplinaire 2").get(0), "Disciplinaire 1");
                case "Economie-Gestion S2" :
                    assertEquals(hashMapPrerequis.get("Economie-Gestion S2").get(0), "Economie-Gestion S1");
                case "Mécanique 2" :
                    assertEquals(hashMapPrerequis.get("Mécanique 2").get(0), "Mécanique 1");
                case "Enseignements fondamentaux à l'école primaire 2" :
                    assertEquals(hashMapPrerequis.get("Enseignements fondamentaux à l'école primaire 2").get(0), "Enseignements fondamentaux à l'école primaire 1");
                default :
                    assertEquals(hashMapPrerequis.get(e.getValue()), null);
            }
        }
    }
}