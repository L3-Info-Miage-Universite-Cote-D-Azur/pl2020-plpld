package com.androidapp.vue;

import com.androidapp.reseau.Connexion;
import com.androidapp.reseau.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    List<String> selection();

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
     *  Permet la gestion de la récéption des UE depuis le serveur
     * @param UE la liste des UE envoyées par le serveur
     */
    void receptionUE(Map<String, List<String>> UE);

    /**
     *  Permet la gestion de la récéption des parcours prédéfinis depuis le serveur
     * @param Predefini la liste des parcours prédéfnis envoyées par le serveur
     */
    void receptionPredefini(Map<String, Map<Integer, List<String>>> Predefini);

    /**
     *  Permet la gestion de la récéption des UE dans le cas d'un parcours prédéfini
     * @param ListeUE la liste des UE envoyées par le serveur
     */
    void receptionTout(List<Map<String, List<String>>> ListeUE);
}

