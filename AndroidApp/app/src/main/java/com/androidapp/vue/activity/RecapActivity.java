package com.androidapp.vue.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.androidapp.R;
import com.androidapp.controleur.EcouteurDeBouton;
import com.androidapp.reseau.Connexion;
import java.util.ArrayList;
import java.util.List;
import constantes.Net;
import metier.ChoixUtilisateur;

/**
 *  Classe RecapActivity, qui sert à gérer l'écran de récapitulatif du parcours.
 *  Dans cet écran, il est possible de confirmer son parcours, de le modifier, ou bien encore de le partager
 */
public class RecapActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);

        Toolbar toolbar = findViewById(R.id.toolbarRecap);
        setSupportActionBar(toolbar);

        initVue();

        final Intent intent = getIntent();

        /**
         *  Bouton de confirmation de parcours
         */

        findViewById(R.id.buttonConfirmation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // On crée une liste temporaire où l'on stocke les choix de chaque semestre

                List<String> tmpList = new ArrayList<>();
                for(int i = 1; i < 5;i++)
                    tmpList.add(Connexion.CONNEXION.getSelectionUE().get(i).toString());

                // La liste est envoyée au serveur puis stockée dans une base de donnée
                Connexion.CONNEXION.envoyerMessage2(Net.CONFIRMATION,new ChoixUtilisateur(tmpList));

                // L'utilisateur est renvoyé sur l'écran d'accueil
                startActivity(new Intent(RecapActivity.this, HomeActivity.class));
            }
        });


        /**
         *  Ici, on désérialise les map reçues
         */
        ((TextView) findViewById(R.id.semestre1)).setText(Connexion.CONNEXION.getSelectionUE().get(1).toString().replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim());

        ((TextView) findViewById(R.id.semestre2)).setText(Connexion.CONNEXION.getSelectionUE().get(2).toString().replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim());

        ((TextView) findViewById(R.id.semestre3)).setText(Connexion.CONNEXION.getSelectionUE().get(3).toString().replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim());

        ((TextView) findViewById(R.id.semestre4)).setText(Connexion.CONNEXION.getSelectionUE().get(4).toString().replace(", ", "\n")
                .replace("[", "")
                .replace("]", "")
                .trim());

        findViewById(R.id.RetourArriereRecap).setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v)
            {
                Intent openMainActivity = new Intent(RecapActivity.this, MainActivity.class);
                openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityIfNeeded(openMainActivity, 0);
            }});

    }

    public void initVue() {
        EcouteurDeBouton ecouteur = new EcouteurDeBouton();
        findViewById(R.id.buttonConfirmation).setOnClickListener(ecouteur);
        Connexion.CONNEXION.démarrerÉcoute();
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     * <p>This is only called once, the first time the options menu is
     * displayed.  To update the menu every time it is displayed, see
     * {@link #onPrepareOptionsMenu}.
     *
     * <p>The default implementation populates the menu with standard system
     * menu items.  These are placed in the {@link Menu#CATEGORY_SYSTEM} group so that
     * they will be correctly ordered with application-defined menu items.
     * Deriving classes should always call through to the base implementation.
     *
     * <p>You can safely hold on to <var>menu</var> (and any items created
     * from it), making modifications to it as desired, until the next
     * time onCreateOptionsMenu() is called.
     *
     * <p>When you add items to the menu, you can implement the Activity's
     * {@link #onOptionsItemSelected} method to handle them there.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed;
     * if you return false it will not be shown.
     * @see #onPrepareOptionsMenu
     * @see #onOptionsItemSelected
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recap,menu);
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * This hook is called whenever an item in your options menu is selected.
     * The default implementation simply returns false to have the normal
     * processing happen (calling the item's Runnable or sending a message to
     * its Handler as appropriate).  You can use this method for any items
     * for which you would like to do processing without those other
     * facilities.
     *
     * <p>Derived classes should call through to the base class for it to
     * perform the default menu handling.</p>
     *
     * @param item The menu item that was selected.
     * @return boolean Return false to allow normal menu processing to
     * proceed, true to consume it here.
     * @see #onCreateOptionsMenu
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                " Bonjour  [Nom du destinataire] ! " +
                        "     \n\n Voici mon choix de parcours :" +
                        "        \n  \n  Semestre 1 \n \n" +
                        Connexion.CONNEXION.getSelectionUE().get(1)  +
                        "\n \n  Semestre 2 \n \n "
                        + Connexion.CONNEXION.getSelectionUE().get(2) +
                        "   \n  \n  Semestre 3 \n \n"
                        +  Connexion.CONNEXION.getSelectionUE().get(3) +
                        "   \n  \n  Semestre 4 \n \n"
                        + Connexion.CONNEXION.getSelectionUE().get(4));
        sendIntent.setType("text/plain");
        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);

        return super.onOptionsItemSelected(item);
    }
}
