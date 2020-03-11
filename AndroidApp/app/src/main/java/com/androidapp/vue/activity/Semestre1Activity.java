package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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


public class Semestre1Activity extends AppCompatActivity implements Vue {
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
    private final String[] views = {"View 1"};
    private Button btns2;

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
        setContentView(R.layout.activity_main);
        
        monIdentité = new Identité("AndroidApp");
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);

        ListView mListView = findViewById(R.id.list);

        StepsProgressAdapter stepsAdapter = new StepsProgressAdapter(this, 0, 0);
        stepsAdapter.addAll(views);
        mListView.setAdapter(stepsAdapter);

        final Context context=this.getBaseContext();

        this.createGroupList();
        this.createCollection();

        expListView = (ExpandableListView) findViewById(R.id.UE_list);
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

    private void createGroupList() {
        groupList = new ArrayList<String>();
        String[] strArray = {"Informatique","Mathématiques","Chimie","Electronique","Géographie",
                             "MIASHS","Physique","Science de la Terre","Science de la vie",
                             "CLE 1D (Continuum Licence Enseignement)","UE facultative"};


        for (String s : strArray) {groupList.add(s);}

    }

    private void createCollection() {
        String[] Informatique = {"Bases de l'informatique", "Introduction à l'informatique par le web", "Structures de données et programmation C", "Bases de données", "Outils formels de l'informatique"};
        String[] Math = { "Fondements 1", "Méthodes : approche continue", "Complements 1"};
        String[] Chimie = { "Structure Microscopique de la Matiere", "Chimie des Solutions", "Chimie organique", "Matériaux 1"};
        String[] Electronique = { "Electronique numerique - Bases", "Automatique : une introduction", "Système embarqué", "Physique des capteurs "};
        String[] Geographie = { "Decouverte 1", "Disciplinaire 1", "Appofondissement hors géographie 1"};
        String[] MIASHS = { "Economie-Gestion S1", "Intro R"};
        String[] Physique = { "Mecanique 1", "Electromagnétisme 1", "Thermodynamique 1", "Outils et Méthodes 1"};
        String[] SDT = { "Decouverte des sciences de la terre", "Atmosphère, Océan, Climats ", "Le temps en Géosciences", "Physique de la Terre", "Materiaux terrestres"};
        String[] SDV = { "Org. Mecanismes Moleculaires Cellules Eucaryotes", "Genetique. Evolution. Origine Vie et Biodiversite"};
        String[] CLE = { "Enseignements fondamentaux à l'école primaire 1"};
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
                case "Science de la terre":
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
}
