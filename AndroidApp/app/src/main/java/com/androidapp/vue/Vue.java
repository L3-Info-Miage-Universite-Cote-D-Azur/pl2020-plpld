package com.androidapp.vue;

import com.androidapp.reseau.Connexion;
import com.androidapp.reseau.*;

import java.util.List;

import metier.Matiere;

/**
 *  Vue de l'écran
 *
 */
public interface Vue {
    /**
     *  Affiche un message passé en paramètre à l'écran
     * @param str Le message qui va être affiché
     */
    void displayMsg(String str);

    /**
     *  Retourne la selection des UEs de l'utilisateur
     */
    List<Matiere> selection();

    void changementSemestre();
}

