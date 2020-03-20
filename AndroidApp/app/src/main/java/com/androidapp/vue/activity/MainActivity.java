package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;

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

import static com.androidapp.vue.activity.HomeActivity.connexion;


public class MainActivity extends AppCompatActivity implements Vue {
    public static final String AUTOCONNECT = "AUTOCONNECT";
    protected Graphe graphe;
    private Button bouton;
    private boolean autoconnect = true;
    private Map<String, List<String>> UECollection = new HashMap<>();
    private ExpandableListView expListView;
    private ExpandableListAdapter adapter;
    private int numSemestre = 1;
    private List<Matiere> selectionUE = new ArrayList<>();

    @Override
    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.semestres);
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
        bouton = findViewById(R.id.buttonValider);
        UECollection = new HashMap<>();
        EcouteurDeBouton ecouteur = new EcouteurDeBouton(this, connexion);
        bouton.setOnClickListener(ecouteur);
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
        selectionUE.addAll(new ChoixUtilisateur(selection()).getChoixS());
        numSemestre++;
        initVue();
        //intent.putExtra("matièresChoisisS1", Connexion.s);
    }

    private List<Matiere> UEvalidees() {
        return selectionUE;
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
                UECollection.put(discipline, ListeUE);
            }
        }
    }