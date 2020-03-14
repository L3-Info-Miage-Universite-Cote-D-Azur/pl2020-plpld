package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Button;

import com.androidapp.R;
import com.androidapp.controleur.*;
import com.androidapp.vue.Vue;
import com.androidapp.vue.adapter.*;
import metier.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.androidapp.controleur.EcouteurDeReseau.ListOfMaps;
import static com.androidapp.vue.activity.HomeActivity.connexion;


public class Semestre1Activity extends AppCompatActivity implements Vue {
    public static final String AUTOCONNECT = "AUTOCONNECT";
    private Button bouton;
    private boolean autoconnect = true;
    protected Map<String, List<String>> UECollection;
    protected ExpandableListView expListView;
    protected ExpandableListAdapter adapter;
    protected int numSemestre = 1;
    @Override
    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semestres);

        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
        bouton = findViewById(R.id.buttonValider);
        initVue();
        ListView mListView = findViewById(R.id.list);

        StepsProgressAdapter stepsAdapter = new StepsProgressAdapter(this, 0, numSemestre-1);
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
    protected void onResume() {
        super.onResume();
        bouton = findViewById(R.id.buttonValider);
        if (autoconnect) {
            initVue();
        }
    }

    protected void initVue() {
        EcouteurDeBouton ecouteur = new EcouteurDeBouton(this, connexion);
        bouton.setOnClickListener(ecouteur);
        connexion.démarrerÉcoute();
    }

    public List<Matiere> selection() {
        return adapter.selection(new Matiere("S" + numSemestre));
    }

    public void changementSemestre() { startActivity(new Intent(Semestre1Activity.this, Semestre2Activity.class)); }

    public void receptionUE() {
            try {
                TimeUnit.SECONDS.sleep(2); // TODO: 14/03/2020 A améliorer : on aimerait pouvoir agir dès que le serveur répond plutôt que d'attendre une durée fixe
                UECollection = ListOfMaps.get(ListOfMaps.size()-1);
            }
             catch (InterruptedException e) {
                 // TODO: 13/03/2020  Créer une exception personnalisée qui dit que le serveur n'a pas envoyé la liste de maps
                e.printStackTrace();
            }
        }
    }

