package com.androidapp.vue.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.view.View;
import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import constantes.Net;
import metier.Etudiant;
import metier.Identité;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText numEtudiant;
    private EditText dateNaissance;
    private Button btnResetPassword;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);
        Connexion.CONNEXION.démarrerÉcoute();

        numEtudiant = (EditText) findViewById(R.id.edt_reset_numEtudiant);
        dateNaissance = findViewById(R.id.edt_reset_dateNaissance);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = numEtudiant.getText().toString().trim();
                String date = dateNaissance.getText().toString().trim();
                Connexion.CONNEXION.setEtudiant(null);

                if (TextUtils.isEmpty(num) || TextUtils.isEmpty(date)) {
                    Toast.makeText(getApplicationContext(), "Merci d'entrer votre numéro étudiant et votre date de naissance.", Toast.LENGTH_SHORT).show();
                    return;
                }

                Connexion.CONNEXION.envoyerMessage(Net.RESET_PASSWORD, new Etudiant(num, LocalDate.parse(date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))));
                final ProgressDialog dialog = ProgressDialog.show(ResetPasswordActivity.this, "","En attente d'une réponse serveur..." , true);
                dialog.show();
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        if(Objects.nonNull(Connexion.CONNEXION.getEtudiant())) {
                            dialog.dismiss();
                            if(Connexion.CONNEXION.getEtudiant().getNumEtudiant().equals("Combinaison invalide"))
                                displayMsg("La combinaison numEtudiant/date de naissance entrée n'est pas correcte.");
                            else
                                popup("Votre mot de passe est " + Connexion.CONNEXION.getEtudiant().getMotDePasse());
                            return;
                        }
                        else {
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    dialog.dismiss();
                                    if(Objects.nonNull(Connexion.CONNEXION.getEtudiant())) {
                                        if (Connexion.CONNEXION.getEtudiant().getNumEtudiant().equals("Combinaison invalide")) {
                                            displayMsg("La combinaison numEtudiant/date de naissance est incorrecte.");
                                        } else {
                                            popup("Votre mot de passe est " + Connexion.CONNEXION.getEtudiant().getMotDePasse());
                                        }
                                    }
                                    else {
                                        displayMsg("Connexion au serveur impossible.");
                                        dialog.dismiss();
                                    }
                                }
                            }, 6000);
                        }
                    }
                }, 500);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void popup(String str) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Mot de passe oublié");
        builder.setMessage(str);
        builder.setPositiveButton("OK", null);
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(final DialogInterface arg0) {
                finish();
            }
        });
    }

}
