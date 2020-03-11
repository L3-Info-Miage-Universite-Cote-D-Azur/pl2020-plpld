package com.androidapp.controleur;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.reseau.*;
import com.androidapp.R;
import com.androidapp.vue.*;

import constantes.Net;
import metier.ChoixUtilisateur;
import metier.ToJSON;

public class EcouteurDeBouton extends AppCompatActivity implements View.OnClickListener {

    private final Connexion mSocket;
    private final Vue vue;
    private boolean validation = false;


    public EcouteurDeBouton(Vue v, Connexion mSocket) {
        this.vue = v;
        this.mSocket = mSocket;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonValider:
                Log.d("POUR MONTRER", "EcouteurDeBouton : bouton valider cliqué");
                if (vue.selection()==null)
                    vue.displayMsg("Votre validation a été transmise au serveur");
                else {
                    if(validation == false)
                    {
                        mSocket.envoyerMessage(Net.VALIDATION, (ToJSON) new ChoixUtilisateur(vue.selection()));
                        vue.displayMsg("Votre choix a été transmis au serveur");
                        validation = true;
                    }
                    else {
                        vue.displayMsg("Vous avez déjà validé votre choix");
                    }
                }
                break;
        }
    }
}