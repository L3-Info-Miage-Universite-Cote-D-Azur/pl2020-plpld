package com.androidapp.controleur;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.reseau.*;
import com.androidapp.R;
import com.androidapp.vue.*;
import com.androidapp.vue.activity.MainActivity;
import com.androidapp.vue.activity.PairActivity;

import constantes.Net;
import metier.ChoixUtilisateur;
import metier.ToJSON;

public class EcouteurDeBouton extends AppCompatActivity implements View.OnClickListener {

    private final Connexion mSocket;
    private final Vue vue;


    public EcouteurDeBouton(Vue v, Connexion mSocket) {
        this.vue = v;
        this.mSocket = mSocket;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonValider:
                Log.d("POUR MONTRER", "EcouteurDeBouton : bouton valider cliqué");
                mSocket.envoyerMessage(Net.VALIDATION, (ToJSON) new ChoixUtilisateur(vue.selection()));
                vue.displayMsg("Votre choix a été transmis au serveur");
                vue.changementSemestre();
                break;
        }
    }
}