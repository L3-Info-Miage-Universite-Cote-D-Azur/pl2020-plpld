package com.androidapp;

import com.androidapp.reseau.Identité;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class IdentitéTest {

    private Identité identiteDefault = new Identité();
    private Identité identite1 = new Identité("Android APP 1");
    private Identité identite2 = new Identité("Android APP 2");


    @Test
    public void nom() {
        assertEquals("Android APP 1", identite1.getNom().toString());
        assertEquals("Android APP 2", identite2.getNom().toString());
        assertEquals("nom par défaut", identiteDefault.getNom().toString());

        identite1.setNom("super Appli Android 1");
        identite2.setNom("super Appli Android 2");
        identiteDefault.setNom("super Appli par défaut");

        assertEquals("super Appli Android 1", identite1.getNom().toString());
        assertEquals("super Appli Android 2", identite2.getNom().toString());
        assertEquals("super Appli par défaut", identiteDefault.getNom().toString());
    }
}
