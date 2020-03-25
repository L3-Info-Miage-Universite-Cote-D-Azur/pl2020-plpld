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
    private List<Matiere> selectionUE = new ArrayList<>();
    List<String> matièresChoisisS1=new ArrayList<>();
    List<String> matièresChoisisS2=new ArrayList<>();
    List<String> matièresChoisisS3=new ArrayList<>();
    private final Connexion mSocket=connexion;
    private Vue vue=this;

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
        switch (numSemestre){
            case 1:
                for (int i=0;i<selectionUE.size();i++){
                    matièresChoisisS1.add(selectionUE.get(i).toString());
                }
                break;
            case 2:
                for (int i=matièresChoisisS1.size();i<selectionUE.size();i++){
                    matièresChoisisS2.add(selectionUE.get(i).toString());
                }
                break;
            case 3:
                for (int i=matièresChoisisS1.size()+matièresChoisisS2.size();i<selectionUE.size();i++){
                    matièresChoisisS3.add(selectionUE.get(i).toString());
                }
                break;
            default:
                break;
        }

        numSemestre++;

        initVue();
        final String smatièresChoisisS1 = matièresChoisisS1.toString()
                .replace(", ", "\n")  //remove the commas
                .replace("[", "")  //remove the right bracket
                .replace("]", "")  //remove the left bracket
                .trim();           //remove trailing spaces from partially initialized arrays
        final String smatièresChoisisS2 = matièresChoisisS2.toString()
                .replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim();
        final String smatièresChoisisS3 = matièresChoisisS3.toString()
                .replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim();

        if(numSemestre==4){
            bouton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String smatièresChoisisS4 = (new ChoixUtilisateur(vue.selection())).getChoixS().toString()
                            .replace(", ", "\n")
                            .replace("[", "")
                            .replace("]", "")
                            .trim();
                    mSocket.envoyerMessage(Net.VALIDATION, new ChoixUtilisateur(vue.selection()));
                    vue.displayMsg("Votre choix a été transmis au serveur");
                    Intent intent=new Intent(MainActivity.this, RecapActivity.class);
                    intent.putExtra("matièresChoisisS1", smatièresChoisisS1);
                    intent.putExtra("matièresChoisisS2", smatièresChoisisS2);
                    intent.putExtra("matièresChoisisS3",  smatièresChoisisS3);
                    //intent.putExtra("matièresChoisisS4",  (new ChoixUtilisateur(vue.selection())).getChoixS().toString());
                    intent.putExtra("matièresChoisisS4",  smatièresChoisisS4);
                    startActivity(intent);
                }
            });
        }
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
            if(ListeUE.size()!=0)
                UECollection.put(discipline, ListeUE);
            }
        }
    }