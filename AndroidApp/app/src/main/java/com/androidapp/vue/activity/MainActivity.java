package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import android.widget.Button;

import com.androidapp.R;
import com.androidapp.controleur.*;
import com.androidapp.exception.ConnexionServeurException;
import com.androidapp.reseau.Connexion;
import com.androidapp.vue.Vue;
import com.androidapp.vue.adapter.*;
import constantes.Net;
import metier.*;

import java.util.*;

public class MainActivity extends AppCompatActivity implements Vue ,SearchView.OnQueryTextListener,SearchView.OnCloseListener {
    public static final String AUTOCONNECT = "AUTOCONNECT";
    protected Graphe graphe;
    private Button bouton;
    private boolean autoconnect = true;
    private Map<String, List<String>> UECollection = new HashMap<>();
    private ExpandableListView expListView;
    private ExpandableListAdapter adapter;
    private  int numSemestre = 1;
    private Map<Integer, List<Matiere>> selectionUE = new HashMap<>();
    private List<Map<String, List<String>>> ListeUE = new ArrayList<>();

    private Vue vue=this;
    private MenuItem searchItem;
    private SearchManager searchManager;
    private android.widget.SearchView searchView;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setSearchableInfo
                (searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();

        return true;
    }


    @Override
    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Connexion.CONNEXION.setMainVue(this);
        Connexion.CONNEXION.envoyerMessage(Net.CONNEXION, new Identité("AndroidApp"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

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
        EcouteurDeBouton ecouteur = new EcouteurDeBouton(this);
        findViewById(R.id.buttonValider).setOnClickListener(ecouteur);
        findViewById(R.id.s1).setOnClickListener(ecouteur);
        findViewById(R.id.s2).setOnClickListener(ecouteur);
        findViewById(R.id.s3).setOnClickListener(ecouteur);
        findViewById(R.id.s4).setOnClickListener(ecouteur);
        Connexion.CONNEXION.démarrerÉcoute();

        ListView mListView = findViewById(R.id.list);

        StepsProgressAdapter stepsAdapter = new StepsProgressAdapter(this, 0, numSemestre-1);
        stepsAdapter.addAll("View " + numSemestre);
        mListView.setAdapter(stepsAdapter);
        /*
        long tempsDepart=System.currentTimeMillis();
        while(Connexion.CONNEXION.ListOfMaps.size()<numSemestre || Connexion.CONNEXION.MapPrerequis.size()==0) {
            if(System.currentTimeMillis()-tempsDepart>6000) {
                Log.d("Erreur", "Pas de réponse du serveur");
                try {
                    throw new ConnexionServeurException(6000, this, android.os.Process.myPid());
                } catch (ConnexionServeurException e) {
                    e.printStackTrace();
                }
            }
        } */
    }

    @Override
    public List<Matiere> selection() {
        return adapter.selection(new Matiere("S" + numSemestre));
    }

    @Override
    public void changementSemestre() {
        selectionUE.put(numSemestre, new ChoixUtilisateur(selection()).getChoixS());
        numSemestre++;
        initVue();
        if(numSemestre==4){
            bouton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectionUE.put(numSemestre, new ChoixUtilisateur(selection()).getChoixS());
                    Connexion.CONNEXION.envoyerMessage2(Net.VALIDATION, new ChoixUtilisateur(vue.selection()));
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

    @Override
    public void retourArriere(int semestre) {
        if (semestre < numSemestre) {
            numSemestre = semestre;
            initVue();
            receptionUE();
        }
        else if(semestre==numSemestre) {
            displayMsg("Vous êtes déjà en train d'effectuer votre selection pour le semestre n°" + semestre);
        }
        else {
            displayMsg("Pour passer au semestre suivant cliquez sur valider");
        }
    }

    private List<Matiere> UEvalidees() {
        List<Matiere> validees = new ArrayList<>();
        for(int i=1; i<numSemestre; i++) {
            validees.addAll(selectionUE.get(i));
        }
        return validees;
    }


    public void receptionUE(Map<String, List<String>> UE) {
        ListeUE.add(UE);
        receptionUE();
    }

    public void receptionUE() {
        Log.d("PREREQUIS", ListeUE.get(numSemestre-1).toString());
        graphe = new Graphe(Connexion.CONNEXION.MapPrerequis);
        List<String> selectionnable = graphe.selectionnable(UEvalidees()); //Utilisation du graphe pour connaître la liste des UE selectionnables ce semestre après avoir validée les UE renvoyées par la méthode UEvalidees
        for (String discipline : ListeUE.get(numSemestre-1).keySet()) {
            List<String> Supression = new ArrayList<>(); //Liste des UE à supprimer
            for (String UE : ListeUE.get(numSemestre-1).get(discipline)) {
                if (!selectionnable.contains(UE))
                    Supression.add(UE);
            }
            ListeUE.removeAll(Supression);
            if(ListeUE.size()!=0)
                UECollection.put(discipline, ListeUE.get(numSemestre-1).get(discipline));
        }
        expList();
        }

        private void expList() {
            expListView = findViewById(R.id.UE_list);
            final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, new ArrayList<>(UECollection.keySet()), UECollection);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d("UECOLLECTION", UECollection.toString());
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
            });
        }

    private void expandAll() {
        int count = adapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expListView.expandGroup(i);
        } //end for (int i = 0; i < count; i++)
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * The user is attempting to close the SearchView.
     *
     * @return true if the listener wants to override the default behavior of clearing the
     * text field and dismissing it, false otherwise.
     */
    @Override
    public boolean onClose() {
        adapter.filterData("");
        expandAll();
        return false;
    }

    /**
     * Called when the user submits the query. This could be due to a key press on the
     * keyboard or due to pressing a submit button.
     * The listener can override the standard behavior by returning true
     * to indicate that it has handled the submit request. Otherwise return false to
     * let the SearchView handle the submission by launching any associated intent.
     *
     * @param query the query text that is to be submitted
     * @return true if the query has been handled by the listener, false to let the
     * SearchView perform the default action.
     */
    @Override
    public boolean onQueryTextSubmit(String query) {
        adapter.filterData(query);
        expandAll();
        return false;
    }

    /**
     * Called when the query text is changed by the user.
     *
     * @param newText the new content of the query text field.
     * @return false if the SearchView should perform the default action of showing any
     * suggestions if available, true if the action was handled by the listener.
     */
    @Override
    public boolean onQueryTextChange(String newText) {
        adapter.filterData(newText);
        expandAll();
        return false;
    }
}