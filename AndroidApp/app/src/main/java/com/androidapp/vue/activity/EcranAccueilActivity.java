package com.androidapp.vue.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.Fichiers.GestionnaireDeFlux;
import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import java.io.IOException;

import constantes.Net;

public class EcranAccueilActivity extends AppCompatActivity {


    GestionnaireDeFlux gestionnaireDeFlux;

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ecranaccueil);

        gestionnaireDeFlux = new GestionnaireDeFlux(this);
        if(!gestionnaireDeFlux.fileExist(Net.FICHIER_PREREQUIS) || gestionnaireDeFlux.getFileLength(Net.FICHIER_PREREQUIS) == 0) {
            gestionnaireDeFlux.ecrireMapDansFichier(Connexion.CONNEXION.getMapPrerequisBrut(), Net.FICHIER_PREREQUIS);
        }

        else
        {
            try {
                Log.d("MAP",String.valueOf(gestionnaireDeFlux.getFileLength(Net.FICHIER_PREREQUIS)));
                Log.d("MAP", String.valueOf(Connexion.CONNEXION.getMapPrerequisBrut().size()));
                Log.d("MAP", gestionnaireDeFlux.getMapFromFile(Net.FICHIER_PREREQUIS).toString());
                Log.d("MAP",Connexion.CONNEXION.getMapPrerequisBrut().toString());

                if(gestionnaireDeFlux.getMapFromFile(Net.FICHIER_PREREQUIS).equals(Connexion.CONNEXION.getMapPrerequisBrut()))
                {
                    Log.d("FICHIER", "fichier non modifié");
                }
                else {
                    Log.d("FICHIER", "fichier modifié");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Bouton déconnexion
         */
        findViewById(R.id.btndeconnexion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EcranAccueilActivity.this, HomeActivity.class));
            }
        });

        findViewById(R.id.btnparcours).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder2 = new AlertDialog.Builder(EcranAccueilActivity.this);
                builder2.setTitle(R.string.parcourspredefini)
                        .setItems(R.array.parcours, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                /**
                                 *  Choix du parcours prédéfini de l'étudiant, il doit appuyer sur le parcours de son choix.
                                 *  Le choix est envoyé au serveur qui interprète cette information et renvoie le parcours correspondant au client.
                                 *  L'étudiant aura alors un parcours déjà fait, pour les premiers semestres du moins.
                                 */
                                switch (which){
                                    case 0:
                                        Connexion.CONNEXION.predefini = "Mathématiques";
                                        startActivity(new Intent(EcranAccueilActivity.this, MainActivity.class));
                                        break;
                                    case 1 :
                                        Connexion.CONNEXION.predefini = "Science de la vie";
                                        startActivity(new Intent(EcranAccueilActivity.this, MainActivity.class));
                                        break;
                                    case 2 :
                                        Connexion.CONNEXION.predefini = "Physique";
                                        startActivity(new Intent(EcranAccueilActivity.this, MainActivity.class));
                                        break;
                                    case 3 :
                                        Connexion.CONNEXION.predefini = "Chimie";
                                        startActivity(new Intent(EcranAccueilActivity.this, MainActivity.class));
                                        break;
                                    case 4 :
                                        Connexion.CONNEXION.predefini = "Informatique";
                                        startActivity(new Intent(EcranAccueilActivity.this, MainActivity.class));
                                        break;
                                    case 5 :
                                        Connexion.CONNEXION.predefini = "Informatique (L1)";
                                        startActivity(new Intent(EcranAccueilActivity.this, MainActivity.class));
                                        break;
                                    case 6 :
                                        Connexion.CONNEXION.predefini = "Mathématiques (L1)";
                                        startActivity(new Intent(EcranAccueilActivity.this, MainActivity.class));
                                        break;
                                }
                            }});

                /**
                 *  Sinon, l'étudiant opte pour un parcours personnalisé, ou il doit choisir de lui même toutes ses UE pour les 4 semestres
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(EcranAccueilActivity.this);
                builder.setTitle(R.string.choix)
                        .setItems(R.array.choix, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch(which){
                                    case  0 :
                                        startActivity(new Intent(EcranAccueilActivity.this, MainActivity.class));
                                        //  Connexion.CONNEXION.envoyerMessage(Net.NV_ETU, etu);
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

            }

        });
    }
}
