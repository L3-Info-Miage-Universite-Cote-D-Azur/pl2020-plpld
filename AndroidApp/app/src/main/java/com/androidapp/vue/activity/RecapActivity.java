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
    private TextView semestre1,semestre2,semestre3,semestre4;

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


                Connexion.CONNEXION.envoyerMessage2(Net.CONFIRMATION,new Identité(intent.getStringExtra("matièresChoisisS1")));
                Connexion.CONNEXION.envoyerMessage2(Net.CONFIRMATION,new Identité(intent.getStringExtra("matièresChoisisS2")));
                Connexion.CONNEXION.envoyerMessage2(Net.CONFIRMATION,new Identité(intent.getStringExtra("matièresChoisisS3")));
                Connexion.CONNEXION.envoyerMessage2(Net.CONFIRMATION,new Identité(intent.getStringExtra("matièresChoisisS4")));
               
                startActivity(new Intent(RecapActivity.this, HomeActivity.class));

            }
        });


        final String sname=intent.getStringExtra("matièresChoisisS1").replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim();
        final String sname2=intent.getStringExtra("matièresChoisisS2").replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim();
        final String sname3=intent.getStringExtra("matièresChoisisS3").replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim();
        final String sname4=intent.getStringExtra("matièresChoisisS4").replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim();

        semestre1=(TextView)findViewById(R.id.semestre1);
        semestre1.setText(sname);

        semestre2=(TextView)findViewById(R.id.semestre2);
        semestre2.setText(sname2);

        semestre3=(TextView)findViewById(R.id.semestre3);
        semestre3.setText(sname3);

        semestre4=(TextView)findViewById(R.id.semestre4);
        semestre4.setText(sname4);

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
                        sname  +
                                "\n \n  Semestre 2 \n \n "
                        + sname2 +
                                 "   \n  \n  Semestre 3 \n \n"
                        +  sname3 +
                             "   \n  \n  Semestre 4 \n \n"
                        + sname4);


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
