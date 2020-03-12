package com.androidapp.vue.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidapp.R;
import com.androidapp.vue.Vue;
import com.androidapp.vue.adapter.ExpandableListAdapter;
import com.androidapp.vue.adapter.StepsProgressAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import metier.Identité;
import metier.Matiere;

public class Semestre2Activity extends Semestre1Activity implements Vue {
    private Identité monIdentité;
    private boolean autoconnect = true;
    private Button bsimp;
    private Button brecap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semestres);

        monIdentité = new Identité("AndroidApp");
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);

        ListView mListView = findViewById(R.id.list);

        StepsProgressAdapter stepsAdapter = new StepsProgressAdapter(this, 1, 1);
        stepsAdapter.addAll(new String[]{"View 2"});
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
        groupList.add("Informatique");
        groupList.add("Mathématiques");
        groupList.add("Chimie");
        groupList.add("Electronique");
        groupList.add("Géographie");
        groupList.add("MIASHS");
        groupList.add("Physique");
        groupList.add("Science de la terre");
        groupList.add("Science de la vie");
        groupList.add("CLE 1D (Continuum Licence Enseignement)");
        groupList.add("UE facultative");
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

    public List<Matiere> selection() {
        return adapter.selection(new Matiere("S2"));
    }

    public void changementSemestre() {
        final Context context=this.getBaseContext();
        Toast.makeText(context,"Semestre 3",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Semestre2Activity.this, Semestre3Activity.class));
    }
}