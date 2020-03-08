package com.androidapp.vue;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.androidapp.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import metier.Identité;

public class PairActivity extends MainActivity {
    private Identité monIdentité;
    private boolean autoconnect = true;
    private Button bsimp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pair);

        monIdentité = new Identité("AndroidApp");
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);

        final Context context=this.getBaseContext();

        createGroupList();
        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.UE_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, UECollection);
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

        bsimp=(Button)findViewById(R.id.simp);

        bsimp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,"semestre impaire",Toast.LENGTH_LONG).show();
                Intent intent=new Intent(PairActivity.this,MainActivity.class);
                startActivity(intent);
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
        String[] Informatique = {"Bases de l'informatique 2", "Introduction à l'informatique par le web 2", "Structures de données et programmation C 2 ", "Bases de données", "Outils formels de l'informatique"};
        String[] Math = { "Fondements 2 ","Méthodes : approche discrète","Complements 2","Analyse","Probabilités et Introduction à la Statistiques","Algèbre"," Résolution numérique des systèmes d'équations linéaires et non-linéaires","Méthodes : approche aléatoire"};
        String[] Chimie = {"Reactions et reactivites chimiques ","Thermodynamique chimique / Options ","Vision macroscopique des molécules,Matériaux 2","Chimie Organique Fonctionnelle II", "Bloc de Chimie Expérimentale"};
        String[] Electronique = { "Electronique analogique","Communication sans fil"  ,"Système optimisé en énergie"  ,"Electronique analogique avancée", "Architecture des processeurs",  "Systèmes embarqués II"};
        String[] Geographie = { "Decouverte 4" ,"Decouverte 3", "Disciplinaire 2", "Disciplinaire 6" ,"Disciplinaire 7" ,"Disciplinaire 8" ,"Approfondissement hors géographie 2"};
        String[] MIASHS = { "Economie-Gestion S1", "Intro R"};
        String[] Physique = { "Optique 1","Mécanique 2",  "Electromagnétisme 2 ","Mécanique 3 ","Outils et Méthodes 2"};
        String[] SDT = { "Structure et dynamique de la terre" ,"Atmosphère, Océan, Climats ","Géologie Structurale et Tectonique","Formation et Evolution des Bassins Sédimentaires","Géomécanique,Du paysage à la carte"};
        String[] SDV = { "Physiologie. Neurologie. Enzymologie. Methodologie","Diversite du Vivant"};
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
}
