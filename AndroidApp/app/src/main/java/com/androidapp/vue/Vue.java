package com.androidapp.vue;

import com.androidapp.reseau.Connexion;
import com.androidapp.reseau.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    /**
     *  Permet la transition d'un semestre à un autre
     */
    void changementSemestre();

    /**
     *  Permet de revenir sur sa séléction d'un semestre déjà validé
     * @param semestre le numéro du semestre auquel on souhaite retourné
     */
    void retourArriere(int semestre);

    /**
     *  Permet la gestion des pérequisrécéption depuis le serveur
     * @param Prerequis la liste de prérequis envoyées par le serveur
     */
    void receptionUE(List<Map<String, List<String>>> ListOfMaps);
}

