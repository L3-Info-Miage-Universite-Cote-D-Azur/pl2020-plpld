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

import static com.androidapp.vue.activity.HomeActivity.connexion;

public class RecapActivity extends AppCompatActivity implements Vue {
    private TextView semestre1,semestre2,semestre3,semestre4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);
        initVue();

        final Intent intent = getIntent();

        findViewById(R.id.buttonConfirmation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connexion.envoyerMessage2(Net.CONFIRMATION,new Identité(intent.getStringExtra("matièresChoisisS1")));
                connexion.envoyerMessage2(Net.CONFIRMATION,new Identité(intent.getStringExtra("matièresChoisisS2")));
                connexion.envoyerMessage2(Net.CONFIRMATION,new Identité(intent.getStringExtra("matièresChoisisS3")));
                connexion.envoyerMessage2(Net.CONFIRMATION,new Identité(intent.getStringExtra("matièresChoisisS4")));
                startActivity(new Intent(RecapActivity.this, HomeActivity.class));

            }
        });


        String sname=intent.getStringExtra("matièresChoisisS1").replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim();
        String sname2=intent.getStringExtra("matièresChoisisS2").replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim();
        String sname3=intent.getStringExtra("matièresChoisisS3").replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim();
        String sname4=intent.getStringExtra("matièresChoisisS4").replace(", ", "\n")
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


    }

    public void initVue()
    {
        EcouteurDeBouton ecouteur = new EcouteurDeBouton(this, connexion);
        findViewById(R.id.buttonConfirmation).setOnClickListener(ecouteur);
        connexion.démarrerÉcoute();
    }
    @Override
    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    public List<Matiere> selection() {
        return null;
    }

    @Override
    public void changementSemestre() {

    }

    @Override
    public void retourArriere(int semestre) {

    }
}
