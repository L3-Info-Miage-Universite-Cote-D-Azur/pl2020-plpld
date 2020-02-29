package com.androidapp;

import com.androidapp.reseau.Identité;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IdentitéTest {

    private Identité identiteDefault;
    private Identité identite1;
    private Identité identite2;

    @Before
    public void setup() {
        //Initialisation
        identiteDefault = new Identité();
        identite1 = new Identité("Android APP 1");
        identite2 = new Identité("Android APP 2");
    }

    @Test
    public void nom() {
        //Test avec le nom d'origine
        assertEquals("Android APP 1", identite1.getNom().toString());
        assertEquals("Android APP 2", identite2.getNom().toString());
        assertEquals("nom par défaut", identiteDefault.getNom().toString());

        identite1.setNom("super Appli Android 1");
        identite2.setNom("super Appli Android 2");
        identiteDefault.setNom("super Appli par défaut");

        //Test avec un nom modifié grace a une méthode de la classe
        assertEquals("super Appli Android 1", identite1.getNom().toString());
        assertEquals("super Appli Android 2", identite2.getNom().toString());
        assertEquals("super Appli par défaut", identiteDefault.getNom().toString());
    }
}
