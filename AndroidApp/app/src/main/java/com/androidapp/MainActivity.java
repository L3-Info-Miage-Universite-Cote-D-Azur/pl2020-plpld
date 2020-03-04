package com.androidapp;

import androidx.annotation.StringDef;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Button;
import com.androidapp.vue.*;
import com.androidapp.reseau.*;
import com.androidapp.controleur.*;
import android.view.View.OnClickListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


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
    private List<String> ListeUE = Arrays.asList("Bases de l'informatique", "Introduction à l'informatique par le web", "Système 1 : Unix et progra shell", "Programmation imperative", "Structures de données et programmation C", "Bases de données", "Outils formels de l'informatique", "Algorithmique 1", "Réseaux et télécommunication", "Système 2: mécanismes internes des systèmes d'exploitation", "Introduction aux systèmes intelligents", "Technologies du web");

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

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new RecyclerViewAdapter(getListData());
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
        monIdentité = new Identité("AndroidApp");
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
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
        connexion.envoyerMessage(Net.CONNEXION, monIdentité);
    }

    private List<Model> getListData() {
        mModelList = new ArrayList<>();
        for(String UE: ListeUE) {
            mModelList.add(new Model(UE));
        }
        return mModelList;
    }

    public List<Matiere> selection() {
        return mAdapter.selection();
    }
}
