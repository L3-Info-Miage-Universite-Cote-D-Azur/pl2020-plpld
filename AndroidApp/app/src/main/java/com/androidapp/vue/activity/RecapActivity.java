package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapp.R;
import com.androidapp.controleur.EcouteurDeBouton;
import com.androidapp.reseau.Connexion;
import com.androidapp.vue.Vue;

import java.util.HashMap;
import java.util.List;

import constantes.Net;
import metier.Identité;
import metier.Matiere;

public class RecapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);
        initVue();

        final Intent intent = getIntent();


      /*  Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT   , intent.getStringExtra("matièresChoisisS1") + intent.getStringExtra("matièresChoisisS2")
                + intent.getStringExtra("matièresChoisisS3") + intent.getStringExtra("matièresChoisisS4"));
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(RecapActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }*/



        findViewById(R.id.buttonConfirmation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Connexion.CONNEXION.envoyerMessage2(Net.CONFIRMATION,new Identité(Connexion.CONNEXION.selectionUE.get(1).toString()));
                Connexion.CONNEXION.envoyerMessage2(Net.CONFIRMATION,new Identité(Connexion.CONNEXION.selectionUE.get(2).toString()));
                Connexion.CONNEXION.envoyerMessage2(Net.CONFIRMATION,new Identité(Connexion.CONNEXION.selectionUE.get(3).toString()));
                Connexion.CONNEXION.envoyerMessage2(Net.CONFIRMATION,new Identité(Connexion.CONNEXION.selectionUE.get(4).toString()));

                startActivity(new Intent(RecapActivity.this, HomeActivity.class));
            }
        });


        ((TextView) findViewById(R.id.semestre1)).setText(Connexion.CONNEXION.selectionUE.get(1).toString().replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim());

        ((TextView) findViewById(R.id.semestre2)).setText(Connexion.CONNEXION.selectionUE.get(2).toString().replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim());

        ((TextView) findViewById(R.id.semestre3)).setText(Connexion.CONNEXION.selectionUE.get(3).toString().replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim());

        ((TextView) findViewById(R.id.semestre4)).setText(Connexion.CONNEXION.selectionUE.get(4).toString().replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim());

        findViewById(R.id.buttonPartager).setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v)
            {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        " Bonjour  [Nom du destinataire] ! " +
                        "     \n\n Voici mon choix de parcours :" +
                        "        \n  \n  Semestre 1 \n \n" +
                        Connexion.CONNEXION.selectionUE.get(1)  +
                                "\n \n  Semestre 2 \n \n "
                        + Connexion.CONNEXION.selectionUE.get(2) +
                                 "   \n  \n  Semestre 3 \n \n"
                        +  Connexion.CONNEXION.selectionUE.get(3) +
                             "   \n  \n  Semestre 4 \n \n"
                        + Connexion.CONNEXION.selectionUE.get(4));
                sendIntent.setType("text/plain");
                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
            }});

        /**
         * Todo Iteration 8 : Modifier le retour arrière pour tomber sur le semestre 4
         */
        findViewById(R.id.RetourArriereRecap).setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v)
            {
                Intent Intent = new Intent(RecapActivity.this, MainActivity.class);
                int semestre = 4;
                intent.putExtra("semestre", semestre);
                startActivity(Intent);
            }});

    }

    public void initVue()
    {
        EcouteurDeBouton ecouteur = new EcouteurDeBouton();
        findViewById(R.id.buttonConfirmation).setOnClickListener(ecouteur);
        Connexion.CONNEXION.démarrerÉcoute();
    }
}
