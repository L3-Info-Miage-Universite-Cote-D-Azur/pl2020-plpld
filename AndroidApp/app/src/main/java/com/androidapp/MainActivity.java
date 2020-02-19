package com.androidapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;
import com.androidapp.vue.*;
import com.androidapp.reseau.*;
import com.androidapp.controleur.*;

public class MainActivity extends AppCompatActivity implements Vue{
    public void displayMsg(String str){
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public static final String AUTOCONNECT = "AUTOCONNECT";
    private Button bouton;


    private Identité monIdentité ;
    private boolean autoconnect =  true;

    public Connexion getConnexion() {
        return connexion;
    }

    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
        this.connexion.écouterRéseau();
    }

    private Connexion connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // création de l'interface graphique
        setContentView(R.layout.activity_main);
        monIdentité = new Identité("Super Appli");
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (connexion != null) connexion.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (autoconnect) {
            setConnexion(new Connexion(this));
        }
        bouton = findViewById(R.id.button);
        if (autoconnect) {
            initVue();
        }
    }

    protected void initVue() {
        EcouteurDeBouton ecouteur = new EcouteurDeBouton(this, connexion);
        // création de l'écouteur (le controleur)
        bouton.setOnClickListener(ecouteur);
        connexion.démarrerÉcoute();
        connexion.envoyerMessage(Net.CONNEXION, monIdentité);
    }

}
