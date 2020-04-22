package com.androidapp.vue.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import java.time.LocalDate;

import constantes.Net;
import metier.Etudiant;
import metier.Identité;

import static constantes.Net.CHIMIE;
import static constantes.Net.HISTOIRE;
import static constantes.Net.INFORMATIQUE;
import static constantes.Net.MATHS;
import static constantes.Net.PHYSIQUE;
import static constantes.Net.SVT;

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
                    final Etudiant etu = new Etudiant(nom.getText().toString(), prénom.getText().toString(),
                            numEtudiant.getText().toString(), LocalDate.of(Integer.parseInt(naissance[2]), Integer.parseInt(naissance[1]), Integer.parseInt(naissance[0])), mdp.getText().toString());
                    Connexion.CONNEXION.envoyerMessage(Net.NV_ETU, etu);

                    final AlertDialog.Builder builder2 = new AlertDialog.Builder(InscriptionActivity.this);
                    builder2.setTitle(R.string.parcourspredefini)
                            .setItems(R.array.parcours, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which)
                                    {
                                        case 0:
                                            Connexion.CONNEXION.predefini = "Mathématiques";
                                            startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                                            break;
                                        case 1 :
                                            Connexion.CONNEXION.predefini = "SVT";
                                            startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                                            break;
                                        case 2 :
                                            Connexion.CONNEXION.predefini = "Physique";
                                            startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                                            break;
                                        case 3 :
                                            Connexion.CONNEXION.predefini = "Chimie";
                                            startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                                            break;
                                        case 4 :
                                            Connexion.CONNEXION.predefini = "Informatique";
                                            startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                                            break;
                                        case 5 :
                                            Connexion.CONNEXION.predefini = "Informatique (L1)";
                                            startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                                            break;
                                    }
                                }});

                    AlertDialog.Builder builder = new AlertDialog.Builder(InscriptionActivity.this);
                    builder.setTitle(R.string.choix)
                            .setItems(R.array.choix, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch(which)
                                    {
                                        case  0 :
                                            startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                                            Connexion.CONNEXION.envoyerMessage(Net.NV_ETU, etu);
                                            break;
                                        case 1 :
                                            builder2.create();
                                            builder2.show();
                                            break;
                                    }
                                }
                            });
                    builder.create();
                    builder.show();
                  //  startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                }
                }
        });
    }
}