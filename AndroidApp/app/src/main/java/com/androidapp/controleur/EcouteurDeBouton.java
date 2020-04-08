package com.androidapp.controleur;

import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.androidapp.reseau.*;
import com.androidapp.R;
import com.androidapp.vue.*;
import constantes.Net;
import metier.ChoixUtilisateur;

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
            case R.id.buttonValider:
                Log.d("POUR MONTRER", "EcouteurDeBouton : bouton valider cliqué");
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

            case R.id.s1:
                Log.d("Retour arrière", "Retour au semestre 1");
                vue.retourArriere(1);
                break;

            case R.id.s2:
                Log.d("Retour arrière", "Retour au semestre 2");
                vue.retourArriere(2);
                break;

            case R.id.s3:
                Log.d("Retour arrière", "Retour au semestre 3");
                vue.retourArriere(3);
                break;

            case R.id.s4:
                Log.d("Retour arrière", "Retour au semestre 4");
                vue.retourArriere(4);
                break;

            case R.id.buttonConfirmation :
                vue.displayMsg("bouton cliqué");
                break;

        }
    }
}