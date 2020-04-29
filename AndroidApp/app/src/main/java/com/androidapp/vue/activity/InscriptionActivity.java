package com.androidapp.vue.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.Dialogs.ConnexionDialogs;
import com.androidapp.Fichiers.GestionnaireDeFlux;
import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import constantes.Net;
import metier.Etudiant;

/**
 * Classe qui gère l'inscription d'un nouvel étudiant, si confirmée, cette inscription est ensuite enregistrée par le serveur
 */

public class InscriptionActivity extends AppCompatActivity {

    GestionnaireDeFlux gestionnaireDeFlux;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        Connexion.CONNEXION.démarrerÉcoute();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        final boolean[] saisieCorrecte = new boolean[1];
        /**
         *  Le champ du nom de l'étudiant
         */
        final EditText nom = findViewById(R.id.nom);

        /**
         *  Le champ du prénom de l'étudiant
         */
        final EditText prénom = findViewById(R.id.prénom);
        /**
         *  Le champ du numéro de l'étudiant
         */
        final EditText numEtudiant = findViewById(R.id.numEtudiant);
        /**
         *  Le champ de la date de naissance de l'étudiant
         */
        final EditText dateNaissance = findViewById(R.id.naissance);
        /**
         *  Le champ du mot de passe choisi par l'étudiant
         */
        final EditText mdp = findViewById(R.id.mdp);

        /**
         *  Bouton valider
         */
        findViewById(R.id.buttonValiderInscription).setOnClickListener(new View.OnClickListener() {
            @Override

            /**
             *  Ici, on vérifie que le format des différentes informations entrée par l'utilisateur sont valides.
             *  Si la saisie est correcte, l'étudiant est crée puis envoyé au serveur qui traitera la requête du client.
             *  Le serveur enregistre l'étudiant et ses possibles choix d'UE.
             */
            public void onClick(View v) {
                saisieCorrecte[0] = true;
                if (nom.getText().toString().matches("[^a-zA-Z -]*")) {
                    nom.setError("Votre nom (écrit en lettre) est obligatoire");
                    saisieCorrecte[0] = false;
                }
                if (prénom.getText().toString().matches("[^a-zA-Z -]*")) {
                    prénom.setError("Votre prénom (écrit en lettre) est obligatoire");
                    saisieCorrecte[0] = false;
                }
                if (numEtudiant.getText().toString().matches("\\W*")) {
                    numEtudiant.setError("Votre numéro étudiant doit contenir des nombres et/ou lettres");
                    saisieCorrecte[0] = false;
                }
                String[] naissance = dateNaissance.getText().toString().split("/");
                if(naissance.length!=3) {
                    dateNaissance.setError("Votre date de naissance doit être de la forme jour/mois/année");
                    saisieCorrecte[0] =false;
                }
                if(mdp.length()<6) {
                    mdp.setError("Votre mot de passe doit contenir au moins 6 caractères");
                    saisieCorrecte[0] =false;
                }

                if(saisieCorrecte[0]) {

                    // Création de l'étudiant
                    final Etudiant etu = new Etudiant(nom.getText().toString(), prénom.getText().toString(),
                            numEtudiant.getText().toString(), LocalDate.of(Integer.parseInt(naissance[2]), Integer.parseInt(naissance[1]), Integer.parseInt(naissance[0])), mdp.getText().toString());
                    Connexion.CONNEXION.envoyerMessage(Net.NV_ETU, etu);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(Connexion.CONNEXION.isInscriptionAutorisee() == true){
                        Log.d("BOOLEAN","SAISIE CORREC");
                        saisieCorrecte[0] = true;
                        ConnexionDialogs connexionDialogs2 = new ConnexionDialogs();
                        connexionDialogs2.onCreateDialog(savedInstanceState,InscriptionActivity.this,true,"ETU");
                        startActivity(new Intent(InscriptionActivity.this, EcranAccueil.class));


                    }
                    else{

                        Log.d("BOOLEAN","SAISIE INCORRECT");
                        ConnexionDialogs connexionDialogs = new ConnexionDialogs();
                        connexionDialogs.onCreateDialog(savedInstanceState,InscriptionActivity.this,false,"ETU");
                        saisieCorrecte[0] = false;


                    }}}



        });
    }
}