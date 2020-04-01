package com.androidapp.vue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import java.time.LocalDate;
import java.time.Month;
import java.util.regex.Pattern;

import constantes.Net;
import metier.Etudiant;

public class InscriptionActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Connexion.CONNEXION.démarrerÉcoute();
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
                boolean saisieCorrecte = true;
                if (nom.getText().toString().matches("[^a-zA-Z -]*")) {
                    nom.setError("Votre nom (écrit en lettre) est obligatoire");
                    saisieCorrecte = false;
                }
                if (prénom.getText().toString().matches("[^a-zA-Z -]*")) {
                    prénom.setError("Votre prénom (écrit en lettre) est obligatoire");
                    saisieCorrecte = false;
                }
                if (numEtudiant.getText().toString().matches("\\W*")) {
                    numEtudiant.setError("Votre numéro étudiant doit contenir des nombres et/ou lettres");
                    saisieCorrecte = false;
                }
                String[] naissance = dateNaissance.getText().toString().split("/");
                if(naissance.length!=3) {
                    dateNaissance.setError("Votre date de naissance doit être de la forme jour/mois/année");
                    saisieCorrecte=false;
                }
                if(mdp.length()<6) {
                    mdp.setError("Votre mot de passe doit contenir au moins 6 caractères");
                    saisieCorrecte=false;
                }
                if(saisieCorrecte) {
                    Etudiant etu = new Etudiant(nom.getText().toString(), prénom.getText().toString(),
                            numEtudiant.getText().toString(), LocalDate.of(Integer.parseInt(naissance[2]), Integer.parseInt(naissance[1]), Integer.parseInt(naissance[0])), mdp.getText().toString());

                    Connexion.CONNEXION.envoyerMessage(Net.NV_ETU, etu);

                    startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                }
                }
        });
    }
}