package com.androidapp;

import com.androidapp.reseau.Matiere;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatiereTest {

    private Matiere matiereDefault = new Matiere();
    private Matiere matierePhysique = new Matiere("Physique");
    private Matiere matiereMath = new Matiere("Mathématique");
    private Matiere matiereInformatique = new Matiere("Informatique");


    @Test
    public void nom() {
        assertEquals("nom par défaut", matiereDefault.getNom().toString());
        assertEquals("Physique", matierePhysique.getNom().toString());
        assertEquals("Mathématique", matiereMath.getNom().toString());
        assertEquals("Informatique", matiereInformatique.getNom().toString());

        matiereInformatique.setNom("Info");
        matierePhysique.setNom("Phys");
        matiereMath.setNom("Math");
        matiereDefault.setNom("Electronique");

        assertEquals("Electronique", matiereDefault.getNom().toString());
        assertEquals("Phys", matierePhysique.getNom().toString());
        assertEquals("Math", matiereMath.getNom().toString());
        assertEquals("Info", matiereInformatique.getNom().toString());
    }

}
