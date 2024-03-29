package com.androidapp.vue.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.androidapp.Dialogs.ConnexionDialogs;
import com.androidapp.Fichiers.GestionnaireDeFlux;
import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import constantes.Net;
import metier.Identité;

/**
 * Classe HomeActivity, qui gère l'écran d'accueil
 */
public class HomeActivity extends AppCompatActivity {

    private Button openInputPopupDialogButton = null;
    private ListView userDataListView = null;
    private View popupInputDialogView = null;
    private AutoCompleteTextView userNameEditText = null;
    private EditText passwordEditText = null;
    private Button saveUserDataButton = null;
    private Button cancelUserDataButton = null;
    private GestionnaireDeFlux  gestionnaireDeFlux;
    private Bundle savedInstanceState;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        this.savedInstanceState = savedInstanceState;
        Connexion.CONNEXION.démarrerÉcoute();
        Connexion.CONNEXION.envoyerMessage(Net.CONNEXION, new Identité("AndroidApp"));
        Connexion.CONNEXION.envoyerMessage(Net.PREREQUIS_BRUT, new Identité("AndroidApp"));
        gestionnaireDeFlux = new GestionnaireDeFlux(this);
        if (gestionnaireDeFlux.fileExist(Net.LOGS) && gestionnaireDeFlux.getFileLength(Net.LOGS) != 0) {
            String tmp = gestionnaireDeFlux.readFromFile(Net.LOGS);
            String[] logs = tmp.split("\n", 3);

            Log.d("LOGS", Arrays.toString(logs));

            Connexion.CONNEXION.envoyerMessage(Net.NV_CONNEXION, new Identité(logs[1] + " " + logs[2]));
            popupConnexion();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        popupConnexion();
        Connexion.CONNEXION.predefini = "Personnalisé";
    }


    public void popupConnexion() {
        final ProgressDialog dialog = ProgressDialog.show(HomeActivity.this, "", "En attente d'une réponse serveur...", true);
        final Handler handler = new Handler();
        dialog.show();
        handler.postDelayed(new Runnable() {
            public void run() {
                if (Connexion.CONNEXION.getConnexionAutorisee() != null) {
                    dialog.dismiss();
                    initVue(savedInstanceState);
                } else {
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            if (Connexion.CONNEXION.getConnexionAutorisee() != null) {
                                initVue(savedInstanceState);
                            }
                            else {
                                displayMsg("Pour assurer le fonctionnement de l'application une connexion au serveur est requise. Relancez l'application avec une connexion au serveur.");
                                Connexion.CONNEXION.setConnexionAutorisee(Boolean.FALSE);
                            }
                            dialog.dismiss();
                        }
                    }, 6000);
                }
            }
        }, 500);

        findViewById(R.id.btninscription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, InscriptionActivity.class));
            }
        });
        findViewById(R.id.btnmdpoublie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ResetPasswordActivity.class));
            }
        });
        /**
         * Bouton consultation
         */
        findViewById(R.id.btnconsultation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, ConsultationActivity.class));
            }
        });
        setTitle("Connexion étudiant");
        initMainActivityControls();
        openInputPopupDialogButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
                alertDialogBuilder.setTitle("Connexion étudiant");
                alertDialogBuilder.setIcon(R.drawable.ic_launcher_background);
                alertDialogBuilder.setCancelable(false);
                initPopupViewControls();
                alertDialogBuilder.setView(popupInputDialogView);

                final AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                saveUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Connexion.CONNEXION.démarrerÉcoute();
                        final String userName = userNameEditText.getText().toString();
                        final String password = passwordEditText.getText().toString();

                        String[] titleArr = { "Numéro étudiant", "Mot de passe"};
                        String[] dataArr = {userName, password};

                        ArrayList<Map<String,Object>> itemDataList = new ArrayList<Map<String,Object>>();;

                        int titleLen = titleArr.length;
                        for(int i =0; i < titleLen; i++) {
                            Map<String,Object> listItemMap = new HashMap<String,Object>();
                            listItemMap.put("title", titleArr[i]);
                            listItemMap.put("data", dataArr[i]);
                            itemDataList.add(listItemMap);
                        }

                        SimpleAdapter simpleAdapter = new SimpleAdapter(HomeActivity.this,itemDataList,android.R.layout.simple_list_item_2,
                                new String[]{"title","data"},new int[]{android.R.id.text1,android.R.id.text2});

                        userDataListView.setAdapter(simpleAdapter);

                        alertDialog.cancel();




                        Connexion.CONNEXION.envoyerMessage(Net.NV_CONNEXION, new Identité(userName + " " + password));
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(Connexion.CONNEXION.getConnexionAutorisee()){
                            ConnexionDialogs connexionDialogs2 = new ConnexionDialogs();
                            connexionDialogs2.onCreateDialog(savedInstanceState,HomeActivity.this,true);
                            gestionnaireDeFlux.ecrireDansFichier(userName + "\n" + password,Net.LOGS);

                            Intent intent=new Intent(HomeActivity.this, EcranAccueilActivity.class);
                            startActivity(intent);
                        }
                        else{
                            ConnexionDialogs connexionDialogs = new ConnexionDialogs();
                            connexionDialogs.onCreateDialog(savedInstanceState,HomeActivity.this,false);

                        }
                        //  startActivity(new Intent(InscriptionActivity.this, MainActivity.class));
                    }



                });

                cancelUserDataButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });
            }
        });
    }

    /**
     * Méthode pour affiché un message
     *
     * @param str Le message qui va être affiché
     */
    public void displayMsg(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    private void initVue(final Bundle savedInstanceState) {
        if(Connexion.CONNEXION.getConnexionAutorisee()) {
            ConnexionDialogs connexionDialogs2 = new ConnexionDialogs();
            connexionDialogs2.onCreateDialog(savedInstanceState, HomeActivity.this, true);
            Intent intent = new Intent(HomeActivity.this, EcranAccueilActivity.class);
            startActivity(intent);
        }

        if (getIntent().hasExtra("Erreur")) {
            popupErreur(getIntent().getStringExtra("Erreur"));
        }
    }

    private void initMainActivityControls(){
        if(openInputPopupDialogButton == null){
            openInputPopupDialogButton = findViewById(R.id.btnpar);
        }
        if(userDataListView == null){
            userDataListView = findViewById(R.id.listview_user_data);
        }
    }

    private void initPopupViewControls(){
        LayoutInflater layoutInflater = LayoutInflater.from(HomeActivity.this);

        popupInputDialogView = layoutInflater.inflate(R.layout.popup_input_dialog, null);

        userNameEditText = popupInputDialogView.findViewById(R.id.userName);

        Log.d("Num etudiants", Connexion.CONNEXION.getNumEtudiants().toString());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, Connexion.CONNEXION.getNumEtudiants());
        userNameEditText.setAdapter(adapter);

        passwordEditText = popupInputDialogView.findViewById(R.id.password);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_connexion);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);
        initEditTextUserDataInPopupDialog();
    }

    private void initEditTextUserDataInPopupDialog(){
        List<String> userDataList = getExistUserDataInListView(userDataListView);
        if(userDataList.size() == 2){
            String userName = userDataList.get(0);
            String password = userDataList.get(1);
            if(userNameEditText != null) {
                userNameEditText.setText(userName);
            }
            if(passwordEditText != null){
                passwordEditText.setText(password);
            }
        }
    }

    private List<String> getExistUserDataInListView(ListView listView){
        List<String> ret = new ArrayList<>();
        if(listView != null){
            ListAdapter listAdapter = listView.getAdapter();
            if(listAdapter != null) {
                int itemCount = listAdapter.getCount();
                for (int i = 0; i < itemCount; i++) {
                    Object itemObject = listAdapter.getItem(i);
                    HashMap<String, String> itemMap = (HashMap<String, String>)itemObject;
                    Set<String> keySet = itemMap.keySet();
                    Iterator<String> iterator = keySet.iterator();
                    String key = iterator.next();
                    String value = itemMap.get(key);
                    ret.add(value);
                }
            }
        }
        return ret;
    }

    private void popupErreur(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erreur");
        builder.setMessage(message);
        builder.setCancelable(true);

        builder.setNeutralButton(
                "Compris",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }
}