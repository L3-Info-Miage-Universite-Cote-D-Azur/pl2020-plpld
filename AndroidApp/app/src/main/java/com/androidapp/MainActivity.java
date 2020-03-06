package com.androidapp;

import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Button;
import com.androidapp.vue.*;
import com.androidapp.reseau.*;
import com.androidapp.controleur.*;

import constantes.Net;
import metier.Identité;
import metier.Matiere;
import metier.ToJSON;

import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements Vue {
    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public static final String AUTOCONNECT = "AUTOCONNECT";
    private Button bouton;
    private Identité monIdentité;
    private boolean autoconnect = true;
    private List<Model> mModelList;
    private RecyclerView mRecyclerView;
    private RecyclerViewAdapter mAdapter;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> UECollection;
    ExpandableListView expListView;

    public Connexion getConnexion() {
        return connexion;
    }

    public void setConnexion(Connexion connexion) {
        this.connexion = connexion;
        this.connexion.écouterRéseau();
    }

    static Connexion connexion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        monIdentité = new Identité("AndroidApp");
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);


        createGroupList();

        createCollection();

        expListView = (ExpandableListView) findViewById(R.id.UE_list);
        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, UECollection);
        expListView.setAdapter(expListAdapter);

        //setGroupIndicatorToRight();

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
    }

    private void createCollection() {
        // preparing laptops collection(child)
        String[] Informatique = {"Bases de l'informatique", "Introduction à l'informatique par le web", "Système 1 : Unix et progra shell", "Programmation imperative", "Structures de données et programmation C", "Bases de données", "Outils formels de l'informatique", "Algorithmique 1", "Réseaux et télécommunication", "Système 2: mécanismes internes des systèmes d'exploitation", "Introduction aux systèmes intelligents", "Technologies du web" };
        String[] Math = { "Algèbre", "Analyse"};

        UECollection = new LinkedHashMap<String, List<String>>();


        for (String discipline : groupList) {
            if (discipline.equals("Informatique")) {
                loadChild(Informatique);
            } else if (discipline.equals("Mathématiques")) {
                loadChild(Math);
            }
            else {
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

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
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
        return new ArrayList<Matiere>();
    }
}
