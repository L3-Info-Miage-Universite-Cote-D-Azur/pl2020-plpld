package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;

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
import constantes.Net;
import metier.*;

import java.util.*;

import static com.androidapp.vue.activity.HomeActivity.connexion;

public class MainActivity extends AppCompatActivity implements Vue {
    public static final String AUTOCONNECT = "AUTOCONNECT";
    protected Graphe graphe;
    private Button bouton;
    private boolean autoconnect = true;
    private Map<String, List<String>> UECollection = new HashMap<>();
    private ExpandableListView expListView;
    private ExpandableListAdapter adapter;
    private  int numSemestre = 1;
    private Map<Integer, List<Matiere>> selectionUE = new HashMap<>();
    private final Connexion mSocket=connexion;
    private Vue vue=this;

    private SearchView mySearchView;

    @Override
    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVue();
    }

    @Override
    protected void onResume() {
        super.onResume();
        bouton = findViewById(R.id.buttonValider);
        if (autoconnect) {
            initVue();
        }
    }

    private void initVue() {
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
        UECollection = new HashMap<>();
        EcouteurDeBouton ecouteur = new EcouteurDeBouton(this, connexion);
        findViewById(R.id.buttonValider).setOnClickListener(ecouteur);
        findViewById(R.id.s1).setOnClickListener(ecouteur);
        findViewById(R.id.s2).setOnClickListener(ecouteur);
        findViewById(R.id.s3).setOnClickListener(ecouteur);
        findViewById(R.id.s4).setOnClickListener(ecouteur);
        connexion.démarrerÉcoute();

        ListView mListView = findViewById(R.id.list);

        StepsProgressAdapter stepsAdapter = new StepsProgressAdapter(this, 0, numSemestre-1);
        stepsAdapter.addAll("View " + numSemestre);
        mListView.setAdapter(stepsAdapter);
        while(connexion.ListOfMaps.size()!=numSemestre || connexion.MapPrerequis.size()==0) continue;
        Log.d("PREREQUIS", connexion.MapPrerequis.toString());
        graphe = new Graphe(connexion.MapPrerequis);
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

    public List<Matiere> selection() {
        return adapter.selection(new Matiere("S" + numSemestre));
    }

    public void changementSemestre() {
        selectionUE.put(numSemestre, new ChoixUtilisateur(selection()).getChoixS());
        numSemestre++;
        initVue();
        if(numSemestre==4){
            bouton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mSocket.envoyerMessage2(Net.VALIDATION, new ChoixUtilisateur(vue.selection()));
                    vue.displayMsg("Votre choix a été transmis au serveur");
                    selectionUE.put(numSemestre, new ChoixUtilisateur(selection()).getChoixS());
                    Intent intent=new Intent(MainActivity.this, RecapActivity.class);
                    intent.putExtra("matièresChoisisS1", selectionUE.get(1).toString());
                    intent.putExtra("matièresChoisisS2", selectionUE.get(2).toString());
                    intent.putExtra("matièresChoisisS3",  selectionUE.get(3).toString());
                    intent.putExtra("matièresChoisisS4",  selectionUE.get(4).toString());
                    startActivity(intent);
                }
            });
        }
    }

    private List<Matiere> UEvalidees() {
        List<Matiere> validees = new ArrayList<>();
        for(List<Matiere> UE: selectionUE.values()) {
            validees.addAll(UE);
        }
        return validees;
    }

    private void receptionUE() {
        List<String> selectionnable = graphe.selectionnable(UEvalidees()); //Utilisation du graphe pour connaître la liste des UE selectionnables ce semestre après avoir validée les UE renvoyées par la méthode UEvalidees
        for (String discipline : connexion.ListOfMaps.get(connexion.ListOfMaps.size() - 1).keySet()) {
            List<String> ListeUE = connexion.ListOfMaps.get(connexion.ListOfMaps.size() - 1).get(discipline);
            List<String> Supression = new ArrayList<>(); //Liste des UE à supprimer
            for (String UE : ListeUE) {
                if (!selectionnable.contains(UE))
                    Supression.add(UE);
            }
                ListeUE.removeAll(Supression);
            if(ListeUE.size()!=0)
                UECollection.put(discipline, ListeUE);
            }
        }

        public void retourArriere(int semestre) {
        /*
            if (semestre < numSemestre) {
                numSemestre = semestre;
                if (semestre <= 3) {
                    matièresChoisisS3 = new ArrayList<>();
                }
                if (semestre <= 2) {
                    matièresChoisisS2 = new ArrayList<>();
                }
                if (semestre == 1) {
                    matièresChoisisS1 = new ArrayList<>();
                }
                selectionUE = new ArrayList<>();
                initVue();
            } /*/
            if(semestre==numSemestre) {
                displayMsg("Vous êtes déjà en train d'effectuer votre selection pour le semestre n°" + semestre);
            }
            else {
                displayMsg("Pour passer au semestre suivant cliquez sur valider");
            }
        }

    }