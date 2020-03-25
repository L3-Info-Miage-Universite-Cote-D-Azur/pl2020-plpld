package com.androidapp.vue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.R;

import java.time.LocalDate;

import metier.Etudiant;

public class InscriptionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        final EditText nom = findViewById(R.id.nom);
        final EditText prénom = findViewById(R.id.prénom);
        final EditText numEtudiant = findViewById(R.id.numEtudiant);
        final EditText dateNaissance = findViewById(R.id.naissance);
        final EditText mdp = findViewById(R.id.mdp);

        findViewById(R.id.buttonValiderInscription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] naissance = dateNaissance.getText().toString().split("/");
                if(naissance.length != 3) {
                    Log.d("Erreur format", "Format de la date de naissance incorrect");
                    //TODO 25-03-2020 : Création d'une exception en cas de saisie invalide
                }
                Etudiant etudiant = new Etudiant(nom.getText().toString(), prénom.getText().toString(), numEtudiant.getText().toString(), LocalDate.of(Integer.parseInt(naissance[2]), Integer.parseInt(naissance[1]), Integer.parseInt(naissance[0])), mdp.getText().toString());
                startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                }
        });
    }
}