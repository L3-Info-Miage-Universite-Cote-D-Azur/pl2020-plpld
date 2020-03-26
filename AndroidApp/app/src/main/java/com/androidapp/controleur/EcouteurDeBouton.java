package com.androidapp.controleur;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.reseau.*;
import com.androidapp.R;
import com.androidapp.vue.*;

import java.util.ArrayList;
import java.util.List;

import constantes.Net;
import metier.ChoixUtilisateur;
import metier.Matiere;
import metier.ToJSON;

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
                if(vue.selection().size() < 3 || vue.selection().size() > 7){
                    vue.displayMsg(" Erreur ");
                }
                else{
                    mSocket.envoyerMessage2(Net.VALIDATION, new ChoixUtilisateur(vue.selection()));
                    vue.displayMsg("Votre choix a été transmis au serveur");
                    vue.changementSemestre();
                }
                break;
        }
    }
}