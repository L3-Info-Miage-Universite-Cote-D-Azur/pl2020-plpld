package com.androidapp.vue.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.R;
import com.androidapp.controleur.EcouteurDeBouton;
import com.androidapp.reseau.Connexion;
import com.androidapp.vue.Vue;
import com.androidapp.vue.adapter.ExpandableListAdapter;
import com.androidapp.vue.adapter.StepsProgressAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import constantes.Net;
import metier.Identité;
import metier.Matiere;
import metier.ToJSON;

public class Semestre2Activity extends AppCompatActivity implements Vue {
    public static final String AUTOCONNECT = "AUTOCONNECT";
    public static Connexion connexion;
    private Button bouton;
    private Identité monIdentité;
    private boolean autoconnect = true;
    List<String> groupList;
    List<String> childList;
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
        ListView mListView = findViewById(R.id.list);

        StepsProgressAdapter stepsAdapter = new StepsProgressAdapter(this, 1, 1);
        stepsAdapter.addAll(new String[]{"View 2"});
        mListView.setAdapter(stepsAdapter);

        createGroupList();
        createCollection();

        expListView = findViewById(R.id.UE_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, groupList, UECollection);
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
        if (autoconnect) {
            setConnexion(connexion);
        }
        bouton = findViewById(R.id.buttonValider);
        if (autoconnect) {
            initVue();
        }
    }

    protected void initVue() {
        bouton = findViewById(R.id.buttonValider);
        EcouteurDeBouton ecouteur = new EcouteurDeBouton(this, connexion);
        // création de l'écouteur (le controleur)
        bouton.setOnClickListener(ecouteur);

        connexion.démarrerÉcoute();
    }

    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("MIASHS");
        groupList.add("Géographie");
        groupList.add("Science de la vie");
        groupList.add("Informatique");
        groupList.add("Mathématiques");
        groupList.add("Chimie");
        groupList.add("Science de la Terre");
        groupList.add("CLE 1D (Continuum Licence Enseignement)");
        groupList.add("Physique");
        groupList.add("UE facultative");
        groupList.add("Electronique");

    }
    private void createCollection() {
        String[] Informatique = {"System 1. Unix et progra shell", "Programmation impérative"};
        String[] Math = { "Fondements 2", "Méthodes : approche discrète", "Complements 2"};
        String[] Chimie = {"Réactions et reactivites chimiques ","Thermodynamique chimique / Options"};
        String[] Electronique = { "Electronique analogique", "Communication sans fil"};
        String[] Geographie = { "Découverte 3" ,"Découverte 4", "Disciplinaire 2"};
        String[] MIASHS = { "Economie-Gestion S2"};
        String[] Physique = { "Optique 1", "Mécanique 2"};
        String[] SDT = { "Structure et dynamique de la terre", "Atmosphère, Océan, Climats"};
        String[] SDV = { "Physiologie. Neurologie. Enzymologie. Methodologie", "Diversité du Vivant"};
        String[] CLE = { "Enseignements fondamentaux à l'école primaire 2", "Méthodologie du concours et didactique - Géométrie", "Méthodologie du concours et didactique - Physique-Chimie"};
        String[] Facultative = { "Projet FabLab"};

        UECollection = new LinkedHashMap<String, List<String>>();


        for (String discipline : groupList) {
            switch(discipline) {
                case "Informatique":
                    loadChild(Informatique);
                    break;
                case "Mathématiques":
                    loadChild(Math);
                    break;
                case "Chimie":
                    loadChild(Chimie);
                    break;
                case "Electronique":
                    loadChild(Electronique);
                    break;
                case "Géographie":
                    loadChild(Geographie);
                    break;
                case "MIASHS":
                    loadChild(MIASHS);
                    break;
                case "Physique":
                    loadChild(Physique);
                    break;
                case "Science de la Terre":
                    loadChild(SDT);
                    break;
                case "Science de la vie":
                    loadChild(SDV);
                    break;
                case "CLE 1D (Continuum Licence Enseignement)":
                    loadChild(CLE);
                    break;
                case "UE facultative":
                    loadChild(Facultative);
                    break;
                default:
                    loadChild(new String[]{"Erreur de chargement"});
            }

            UECollection.put(discipline, childList);
        }
    }

    private void loadChild(String[] discipline) {
        childList = new ArrayList<String>();
        for (String model : discipline)
            childList.add(model);
    }

    public List<Matiere> selection() {
        return adapter.selection(new Matiere("S2"));
    }

    public void changementSemestre() {
        final Context context=this.getBaseContext();
        Toast.makeText(context,"Semestre 3",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Semestre2Activity.this, Semestre3Activity.class));
    }
}
