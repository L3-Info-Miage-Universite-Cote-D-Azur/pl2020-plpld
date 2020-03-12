package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import com.androidapp.R;
import com.androidapp.reseau.*;
import com.androidapp.controleur.*;
import com.androidapp.vue.Vue;
import com.androidapp.vue.adapter.*;

import constantes.Net;
import metier.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.androidapp.controleur.EcouteurDeReseau.ListOfMaps;


public class Semestre1Activity extends AppCompatActivity implements Vue {
    public static final String AUTOCONNECT = "AUTOCONNECT";
    public static Connexion connexion;
    private Button bouton;
    private Identité monIdentité;
    private boolean autoconnect = true;
    Map<String, List<String>> UECollection;
    ExpandableListView expListView;
    ExpandableListAdapter adapter;
    @Override
    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
        this.connexion.écouterRéseau();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semestres);

        connexion = new Connexion(this);
        setConnexion(connexion);
        monIdentité = new Identité("AndroidApp");
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
        bouton = findViewById(R.id.buttonValider);
        initVue();
        ListView mListView = findViewById(R.id.list);

        StepsProgressAdapter stepsAdapter = new StepsProgressAdapter(this, 0, 0);
        stepsAdapter.addAll("View 1");
        mListView.setAdapter(stepsAdapter);
        receptionUE();

        expListView = findViewById(R.id.UE_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, new ArrayList<>(UECollection.keySet()), UECollection);
        expListView.setAdapter(expListAdapter);
        adapter = expListAdapter;
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                final String selected = (String) expListAdapter.getChild(
                        groupPosition, childPosition);
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();
                return true;
            }
        });
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
            setConnexion(connexion);
        }
        bouton = findViewById(R.id.buttonValider);
        if (autoconnect) {
            initVue();
        }
    }

    protected void initVue() {
        EcouteurDeBouton ecouteur = new EcouteurDeBouton(this, connexion);
        // création de l'écouteur (le controleur)
        bouton.setOnClickListener(ecouteur);

        connexion.démarrerÉcoute();
        connexion.envoyerMessage(Net.CONNEXION, (ToJSON) monIdentité);
    }

    public List<Matiere> selection() {
        return adapter.selection(new Matiere("S1"));
    }

    public void changementSemestre() {
        final Context context=this.getBaseContext();
        Toast.makeText(context,"Semestre 2",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Semestre1Activity.this, Semestre2Activity.class));
    }

    public void receptionUE() {
        if(ListOfMaps.size()==1) {
            UECollection = ListOfMaps.get(0);
        }
        else {
            try {
                TimeUnit.SECONDS.sleep(1);
                if(ListOfMaps.size()==1) {
                    UECollection = ListOfMaps.get(0);
                }
                else {
                    Log.d("Timeout", "Le serveur ne répond pas");
                    //Créer une exception personnalisée qui dit que le serveur n'a pas envoyé la liste de maps
                }
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
