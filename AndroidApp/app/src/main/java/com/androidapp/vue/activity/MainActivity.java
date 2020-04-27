package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Selection;
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
import com.androidapp.reseau.Connexion;
import com.androidapp.vue.Vue;
import com.androidapp.vue.adapter.*;
import constantes.Net;
import metier.*;
import java.util.*;

/**
 *   Classe MainActivity, qui gère la selection des UE du parcours de l'étudiant.
 *   Chaque UE selectionnée est envoyée au serveur, son parcours est ensuite enregistré
 */



public class MainActivity extends AppCompatActivity implements Vue ,SearchView.OnQueryTextListener,SearchView.OnCloseListener {

    /**
     * Objet Graphe  qui servira à implémenter les algorithmes de graphes pour le bon fonctionnement des prerequis
     */

    protected Graphe graphe;

    /**
     * Variable bouton de validation
     */

    private Button bouton;

    /**
     * autoconnexion
     */

    private boolean autoconnect = true;

    /**
     * Map UECollection, qui servira de structure de données pour les matières de chaque semestre
     */

    private Map<String, List<String>> UECollection = new HashMap<>();

    /**
     * variable ExpandableListView et ExpandableListAdapter, pour l'affichaque à l'utilisateur final
     */
    private ExpandableListView expListView;
    private ExpandableListAdapter expListAdapter;

    private boolean inQuery = false;

    private ExpandableListAdapter adapter;

    /**
     * numéro du semestre qui est actuellement affiché
     */
    private int numSemestre = 1;
    /**
     * Liste des UE de chaque semestre
     */
    private List<Map<String, List<String>>> ListeUE = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         *  On set la vue principale à l'activity de la classe (this) et le client fait une requête au serveur  pour recevoir les matières du premier semestre
         */
        Connexion.CONNEXION.setMainVue(this);
        Connexion.CONNEXION.envoyerMessage2(Net.ENVOIE_S1, new Identité("S1"));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        initVue();
    }

    /**
     * Méthode pour affiché un message
     *
     * @param str Le message qui va être affiché
     */
    @Override
    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * On assigne notre bouton au bouton valider sur l'écran, si on est connecté, on initialise la vue
     */
    @Override
    protected void onResume() {
        super.onResume();
        bouton = findViewById(R.id.buttonValider);
        if (autoconnect) {
            initVue();
        }
    }

    /**
     * Initialise la vue du semestre actuel
     */
    private void initVue() {

        /**
         *   Si l'étudiant souhaite faire un parcours prédefini,
         *   on envoie une requête au serveur pour récupérer les parcours prédéfini
         */
        if (!Connexion.CONNEXION.predefini.equals("Personnalisé")) {
            ListeUE = new ArrayList<>();
            Connexion.CONNEXION.envoyerMessage2(Net.ENVOIE_PREDEFINI, new Identité("PREDEFINI"));
        }

        final ProgressDialog dialog = ProgressDialog.show(MainActivity.this, "", "En attente d'une réponse serveur...", true);
        autoconnect = getIntent().getBooleanExtra(Net.AUTOCONNECT, true);
        UECollection = new HashMap<>();
        EcouteurDeBouton ecouteur = new EcouteurDeBouton(this);
        findViewById(R.id.buttonValider).setOnClickListener(ecouteur);

        findViewById(R.id.s1).setOnClickListener(ecouteur);
        findViewById(R.id.s2).setOnClickListener(ecouteur);
        findViewById(R.id.s3).setOnClickListener(ecouteur);
        findViewById(R.id.s4).setOnClickListener(ecouteur);
        Connexion.CONNEXION.démarrerÉcoute();

        ListView mListView = findViewById(R.id.list);

        StepsProgressAdapter stepsAdapter = new StepsProgressAdapter(this, 0, numSemestre - 1);
        stepsAdapter.addAll("View " + numSemestre);
        mListView.setAdapter(stepsAdapter);
        dialog.show();
        final Handler handler = new Handler();
        Log.d("ListeUESize", String.valueOf(ListeUE.size()));
        handler.postDelayed(new Runnable() {
            public void run() {
                if (ListeUE.size() >= numSemestre) {
                    dialog.dismiss();
                } else {
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (ListeUE.size() >= numSemestre || Connexion.CONNEXION.getSelectionUE().containsKey(numSemestre)) {
                                dialog.dismiss();
                            } else {
                                displayMsg("Erreur serveur");
                                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                            }
                        }
                    }, 6000);
                }
            }
        }, 500);
    }

    /**
     * Cette méthode retourne la selection de l'étudiant pour le semestre actuel
     *
     * @return une Liste
     */
    @Override
    public List<String> selection() {
        return adapter.selection("S" + numSemestre);
    }


    /**
     * Cette méthode s'occupe du changement de semestre.
     * Si le semestre actuel est le dernier, un message de validation est envoyé au serveur.
     * Puis le recapitulatif est affiché à l'étudiant
     */
    @Override
    public void changementSemestre() {

        Connexion.CONNEXION.getSelectionUE().put(numSemestre, new ChoixUtilisateur(selection()).getChoixS());
        numSemestre++;
        initVue();


        if (numSemestre == 4) {
            bouton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Connexion.CONNEXION.getSelectionUE().put(numSemestre, new ChoixUtilisateur(selection()).getChoixS());
                    Connexion.CONNEXION.envoyerMessage2(Net.VALIDATION, new ChoixUtilisateur(vue.selection()));
                    vue.displayMsg("Votre choix a été transmis au serveur");
                    Connexion.CONNEXION.getSelectionUE().put(numSemestre, new ChoixUtilisateur(selection()).getChoixS());
                    startActivity(new Intent(MainActivity.this, RecapActivity.class));
                }
            });
        }
    }

    /**
     * Méthode qui gère le retour en arrière du semestre.
     * Si le semestre selectionné est plus grand que le semestre actuel un message est affiché.
     *
     * @param semestre le numéro du semestre auquel on souhaite retourné
     */
    @Override
    public void retourArriere(int semestre) {
        if (semestre < numSemestre) {
            numSemestre = semestre;
            initVue();
            receptionUE();
        } else if (semestre == numSemestre) {
            displayMsg("Vous êtes déjà en train d'effectuer votre selection pour le semestre n°" + semestre);
        } else {
            displayMsg("Pour passer au semestre suivant cliquez sur valider");
        }
    }

    /**
     * Méthode qui récupère toutes les UE selectionnées par l'étudiant pour chaque semestre et les ajoute dans une liste
     *
     * @return la liste des ue de l'étudiant, son choix final
     */
    private List<String> UEvalidees() {
        List<String> validees = new ArrayList<>();
        for (int i = 1; i < numSemestre; i++) {
            validees.addAll(Connexion.CONNEXION.getSelectionUE().get(i));
        }
        return validees;
    }

    /**
     * Receptionne la liste des UE envoyées par le serveur, et les ajoute dans une liste.
     * receptionUE() est ensuite appellée
     *
     * @param UE la liste des UE envoyées par le serveur
     */
    public void receptionUE(Map<String, List<String>> UE) {
        Log.d("Liste des prérequis : ", Connexion.CONNEXION.getMapPrerequis().toString());
        Log.d("NUM SEMESTRE : ", String.valueOf(numSemestre));
        ListeUE.add(UE);
        receptionUE();
    }

    /**
     * Cette méthode gère les UE affichées à l'étudiant en fonction des prerequis.
     * Le graphe est donc utilisé afin de bien supprimer les UE innaccessibles en fonction du choix de l'étudiant
     */
    private void receptionUE() {
        Log.d("Liste des UE du semestre : ", ListeUE.get(numSemestre - 1).toString());
        graphe = new Graphe(Connexion.CONNEXION.getMapPrerequis());
        if (Connexion.CONNEXION.getMapPrerequis().size() == 0)
            graphe = new Graphe(ListeUE.get(numSemestre - 1));
        List<String> selectionnable = graphe.selectionnable(UEvalidees()); //Utilisation du graphe pour connaître la liste des UE selectionnables ce semestre après avoir validée les UE renvoyées par la méthode UEvalidees
        Log.d("Liste des UE validées : ", UEvalidees().toString());
        for (String discipline : ListeUE.get(numSemestre - 1).keySet()) {
            List<String> List = ListeUE.get(numSemestre - 1).get(discipline);
            List<String> Supression = new ArrayList<>(); //Liste des UE à supprimer
            for (String UE : List) {
                if (!selectionnable.contains(UE))
                    Supression.add(UE);
            }
            List.removeAll(Supression);
            if (List.size() != 0)
                UECollection.put(discipline, List);
        }
        expList();
    }

    /**
     * Receptionne toute la liste des UE
     *
     * @param ListeUE la liste des UE envoyées par le serveur
     */
    public void receptionTout(List<Map<String, List<String>>> ListeUE) {
        this.ListeUE = ListeUE;
    }

    /**
     * Gère la liste exp d'android, principalement pour l'affichage final à l'utilisateur
     */
    private void expList() {
        expListView = findViewById(R.id.UE_list);
        expListAdapter = new ExpandableListAdapter(this, new ArrayList<>(UECollection.keySet()), UECollection);
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
        //only one group can be expanded at one time (the previous one is collapsed )
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int previousItem = -1;

            @Override
            public void onGroupExpand(int groupPosition) {
                if (!inQuery) {
                    if (groupPosition != previousItem)
                        expListView.collapseGroup(previousItem);
                    previousItem = groupPosition;
                }
            }

        });
    }



    /**
     *  Cette méthode receptionne la liste des parcours prédefini donnée en paramètre.
      * @param Predefini la liste des parcours prédéfnis envoyées par le serveur
     */
    public void receptionPredefini(Map<String, Map<Integer, List<String>>> Predefini) {
        if(Connexion.CONNEXION.predefini.equals("Personnalisé")) return;
        Log.d("Parcours prédéfini séléctionné : ", Connexion.CONNEXION.predefini);
        Log.d("Liste des parcours prédéfinis : ", Predefini.toString());
        while (Connexion.CONNEXION.getMapPredefini().get(Connexion.CONNEXION.predefini).containsKey(numSemestre)) {
                Connexion.CONNEXION.getSelectionUE().put(numSemestre, Connexion.CONNEXION.getMapPredefini().get(Connexion.CONNEXION.predefini).get(numSemestre));
                numSemestre++;
            }
            if(numSemestre==5)
                startActivity(new Intent(MainActivity.this, RecapActivity.class));
            Connexion.CONNEXION.predefini = "Personnalisé";
            Log.d("Numéro du semestre", String.valueOf(numSemestre));
            new Thread()
            {
                public void run()
                {
                    MainActivity.this.runOnUiThread(new Runnable()
                    {
                        public void run()
                        {
                            initVue();
                        }
                    });
                }
            }.start();
    }

    //Pour la barre de recherche:
    private Vue vue=this;
    private MenuItem searchItem;
    private SearchManager searchManager;
    private android.widget.SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo
                (searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        searchView.requestFocus();
        searchView.setQueryHint("Chercher une UE");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will automatically handle clicks on the Home/Up button, so long as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void expandAll() {
        int count = adapter.getGroupCount();
        for (int i = 0; i < count; i++) {
            expListView.expandGroup(i);
        } //end for (int i = 0; i < count; i++)
    }

    private boolean query(String newText){
        inQuery=!newText.isEmpty();
        expListAdapter.filterData(newText);
        expandAll();
        return false;
    }

    /**
     * The user is attempting to close the SearchView.
     *
     * @return true if the listener wants to override the default behavior of clearing the
     * text field and dismissing it, false otherwise.
     */
    @Override
    public boolean onClose() {
        return query("");
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
        return query(query);
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
        return query(newText);
    }
    
}