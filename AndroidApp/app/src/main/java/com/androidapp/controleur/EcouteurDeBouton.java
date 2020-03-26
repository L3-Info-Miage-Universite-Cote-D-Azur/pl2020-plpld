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
    private final Connexion mSocket;
    private Vue vue;

    public EcouteurDeBouton(Vue v, Connexion mSocket) {
        this.vue = v;
        this.mSocket = mSocket;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.buttonValider:
                Log.d("POUR MONTRER", "EcouteurDeBouton : bouton valider cliqué");
                if (vue.selection().size()-1 < 3) {
                    vue.displayMsg("Veuillez choisir aux minimums 3 matières");
                }
                else if (vue.selection().size()-1 >= 8) {
                    vue.displayMsg("Veuillez choisir aux maximums 7 matières");
                }
                else {
                    mSocket.envoyerMessage2(Net.VALIDATION, new ChoixUtilisateur(vue.selection()));
                    vue.displayMsg("Votre choix a été transmis au serveur");
                    vue.changementSemestre();
                }
                break;
        }
    }
}