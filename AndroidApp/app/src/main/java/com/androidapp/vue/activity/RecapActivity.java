package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
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


        /**
         *  Bouton partager
         */
        findViewById(R.id.buttonPartager).setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v)
            {

                // Le parcours  est transformé en texte, puis disponible au partage pour plusieurs applications
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
            }});

        /**
         * Todo Iteration 8 : Modifier le retour arrière pour tomber sur le semestre 4
         */
        findViewById(R.id.RetourArriereRecap).setOnClickListener(new View.OnClickListener() {
            @Override
            public  void onClick(View v)
            {
                Intent Intent = new Intent(RecapActivity.this, MainActivity.class);
                int semestre = 4;
                intent.putExtra("semestre", semestre);
                startActivity(Intent);
            }});

    }

    public void initVue()
    {
        EcouteurDeBouton ecouteur = new EcouteurDeBouton();
        findViewById(R.id.buttonConfirmation).setOnClickListener(ecouteur);
        Connexion.CONNEXION.démarrerÉcoute();
    }
}
