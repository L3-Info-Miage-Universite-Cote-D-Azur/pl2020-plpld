package com.androidapp.controleur;

import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.androidapp.reseau.*;
import com.androidapp.R;
import com.androidapp.vue.*;
import constantes.Net;
import metier.ChoixUtilisateur;

/**
 *  Classe EcouteurDeBouton, qui gère les différents cas quand l'utilisateur appuie sur un bouton en fonction de l'état de l'application.
 */
public class EcouteurDeBouton extends AppCompatActivity implements View.OnClickListener {

    private Vue vue;

    public EcouteurDeBouton() {
    }

    public EcouteurDeBouton(Vue v) {
        this.vue = v;
    }

    @Override


    public void onClick(View v) {
        switch(v.getId()) {

            /**
             *  Gestion du bouton valider pour chaque semestre, si l'utilisateur sélectionne un nombre d'UE
             *  dans le bon intervalle,sa selection est transmise au serveur et le semestre change
             */
            case R.id.buttonValider:
                if (vue.selection().size()-1 < 3) {
                    vue.displayMsg("Veuillez choisir au minimum 3 matières");
                }
                else if (vue.selection().size()-1 >= 8) {
                    vue.displayMsg("Veuillez choisir au maximum 7 matières");
                }
                else {
                    Connexion.CONNEXION.envoyerMessage2(Net.VALIDATION, new ChoixUtilisateur(vue.selection()));
                    vue.displayMsg("Votre choix a été transmis au serveur");
                    vue.changementSemestre();
                }
                break;
            /**
             *  Gestion du retour en arrière pour chaque semestre
             */
            case R.id.s1:
                vue.retourArriere(1);
                break;

            case R.id.s2:
                vue.retourArriere(2);
                break;

            case R.id.s3:
                vue.retourArriere(3);
                break;

            case R.id.s4:
                vue.retourArriere(4);
                break;

        }
    }
}