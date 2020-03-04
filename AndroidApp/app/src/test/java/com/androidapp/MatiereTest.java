package com.androidapp;

import com.androidapp.reseau.Matiere;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatiereTest {

    private Matiere matiereDefault;
    private Matiere matierePhysique;
    private Matiere matiereMath;
    private Matiere matiereInformatique;

    @Before
    public void setup() {
        //Initialisation
        matiereDefault = new Matiere();
        matierePhysique = new Matiere("Physique");
        matiereMath = new Matiere("Mathématique");
        matiereInformatique = new Matiere("Informatique");
    }

    @Test
    public void nom() {
        //Test avec le nom d'origine
        assertEquals("nom par défaut", matiereDefault.getNom().toString());
        assertEquals("Physique", matierePhysique.getNom().toString());
        assertEquals("Mathématique", matiereMath.getNom().toString());
        assertEquals("Informatique", matiereInformatique.getNom().toString());

        matiereInformatique.setNom("Info");
        matierePhysique.setNom("Phys");
        matiereMath.setNom("Math");
        matiereDefault.setNom("Electronique");

        //Test avec un nom modifié grace a une méthode de la classe
        assertEquals("Electronique", matiereDefault.getNom().toString());
        assertEquals("Phys", matierePhysique.getNom().toString());
        assertEquals("Math", matiereMath.getNom().toString());
        assertEquals("Info", matiereInformatique.getNom().toString());
    }

}
