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
import java.util.concurrent.TimeUnit;

import constantes.Net;
import metier.Identité;
import metier.Matiere;
import metier.ToJSON;

import static com.androidapp.controleur.EcouteurDeReseau.ListOfMaps;

public class Semestre3Activity extends AppCompatActivity implements Vue {
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

        StepsProgressAdapter stepsAdapter = new StepsProgressAdapter(this, 0, 2);
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

   /* private void createGroupList() {
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

        String[] Informatique = {"Bases de l'informatique", "Introduction à l'informatique par le web", "Structures de données et programmation C", "Bases de donnée", "Outils formels pour l'informatique"};
        String[] Math = { "Fondements 1", "Méthodes : approche continue", "Complements 1", "Fondements 3", "Compléments d'Analyse", "Compléments d'Algèbre", "Méthodes : Mathématiques et ingénierie", "Méthodes : approche géométrique"};
        String[] Chimie = {"Structure Microscopique de la Matière", "Chimie des solutions", "Chimie organique", "Matériaux 1"};
        String[] Electronique = { "Electronique numerique - Bases", "Automatique : une introduction", "Système embarqué", "Physique des capteurs"};
        String[] Geographie = { "Decouverte 1" ,"Decouverte 2", "Disciplinaire 1", "Disciplinaire 3", "Disciplinaire 4", "Disciplinaire 5", "Approfondissement hors géographie 1"};
        String[] MIASHS = { "Economie-Gestion S1", "Economie-Gestion S3", "Intro R"};
        String[] Physique = {"Mécanique 1", "Electromagnétisme 1", "Thermodynamique 1", "Outils et Méthodes 1"};
        String[] SDT = { "Découverte des sciences de la terre", "Atmosphère, Océan, Climats", "Le temps en Géosciences", "Physique de la Terre", "Matériaux terrestres"};
        String[] SDV = { "Org. Mécanismes Moléculaires Cellules Eucaryotes", "Génétique. Evolution. Origine Vie et Biodiversité"};
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
*/
   public void receptionUE() {
           try {
               TimeUnit.SECONDS.sleep(1);
                   UECollection = ListOfMaps.get(ListOfMaps.size()-1);
           }
           catch (InterruptedException e) {
               e.printStackTrace();
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
        return adapter.selection(new Matiere("S3"));
    }

    public void changementSemestre() {
        final Context context=this.getBaseContext();
        Toast.makeText(context,"Semestre 4",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(Semestre3Activity.this, Semestre4Activity.class));
    }
}
