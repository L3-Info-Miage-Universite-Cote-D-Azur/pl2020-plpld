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
import com.androidapp.reseau.Connexion;
import com.androidapp.vue.Vue;
import com.androidapp.vue.adapter.*;
import metier.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

//import static com.androidapp.controleur.EcouteurDeReseau.ListOfMaps;
import static com.androidapp.vue.activity.HomeActivity.connexion;


public class Semestre1Activity extends AppCompatActivity implements Vue {
    public static final String AUTOCONNECT = "AUTOCONNECT";
    protected Graphe graphe;
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

     // graphe = new Graphe(connexion.MapPrerequis); //TODO (dès que possible): Mettre en paramètre la map des prérequis donnés par le serveur

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

    public void changementSemestre() {
        Intent intent=new Intent(Semestre1Activity.this, Semestre2Activity.class);
        intent.putExtra("matièresChoisisS1", Connexion.s);
        startActivity(intent);
    }

    public void receptionUE() {
            try {
                TimeUnit.SECONDS.sleep(2); // TODO: 14/03/2020 A améliorer : on aimerait pouvoir agir dès que le serveur répond plutôt que d'attendre une durée fixe
                UECollection = connexion.ListOfMaps.get(connexion.ListOfMaps.size()-1);


                //TODO: 18/03/2020 : décommenter ce qui suit dès qu'on aura une transmission de la liste des prérequis par le serveur
                /*
                for(String discipline : ListOfMaps.get(ListOfMaps.size()-1).keySet()) {
                    List<String> ListeUE = ListOfMaps.get(ListOfMaps.size()-1).get(discipline);
                    for(String UE: ListeUE) {
                        if(!graphe.selectionnable("Origine").contains(UE)) //TODO: 18/03/2020 Remplacer "Origine" par la liste des UE séléctionnées précédemment par l'étudiant
                            ListeUE.remove(UE);
                    }
                    ListOfMaps.get(ListOfMaps.size()-1).put(discipline, ListeUE);
                } */
            }
             catch (InterruptedException e) {
                 // TODO: 13/03/2020  Créer une exception personnalisée qui dit que le serveur n'a pas envoyé la liste de maps
                e.printStackTrace();
            }
        }
    }

