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
                Etudiant etudiant = new Etudiant(nom.getText().toString(), prénom.getText().toString(), numEtudiant.getText().toString(), dateNaissance.getText().toString(), mdp.getText().toString());
                startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                }
        });
    }
}