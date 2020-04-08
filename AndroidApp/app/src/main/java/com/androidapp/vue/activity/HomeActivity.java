package com.androidapp.vue.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.androidapp.Dialogs.ConnexionDialogs;
import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import constantes.Net;
import metier.Etudiant;
import metier.Identité;
import metier.ToJSON;

public class HomeActivity extends AppCompatActivity {
    private Button openInputPopupDialogButton = null;
    private ListView userDataListView = null;
    private View popupInputDialogView = null;
    private EditText userNameEditText = null;
    private EditText passwordEditText = null;
    private Button saveUserDataButton = null;
    private Button cancelUserDataButton = null;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Connexion.CONNEXION.écouterRéseau();
        Connexion.CONNEXION.envoyerMessage(Net.CONNEXION, new Identité("AndroidApp"));
        if (getIntent().hasExtra("Erreur")) {
            popupErreur(getIntent().getStringExtra("Erreur"));
        }
        findViewById(R.id.btninscription).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, InscriptionActivity.class);
                startActivity(intent);
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
                        if(Connexion.CONNEXION.getConnexionAutorisee())
                                {
                                    ConnexionDialogs connexionDialogs2 = new ConnexionDialogs();
                                    connexionDialogs2.onCreateDialog(savedInstanceState,HomeActivity.this,true);


                                    Intent intent=new Intent(HomeActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    }
                                else
                                {
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

    private void initMainActivityControls()
    {
        if(openInputPopupDialogButton == null)
        {
            openInputPopupDialogButton = findViewById(R.id.btnpar);
        }
        if(userDataListView == null)
        {
            userDataListView = findViewById(R.id.listview_user_data);
        }
    }

    private void initPopupViewControls()
    {
        LayoutInflater layoutInflater = LayoutInflater.from(HomeActivity.this);

        popupInputDialogView = layoutInflater.inflate(R.layout.popup_input_dialog, null);

        userNameEditText = popupInputDialogView.findViewById(R.id.userName);
        passwordEditText = popupInputDialogView.findViewById(R.id.password);
        saveUserDataButton = popupInputDialogView.findViewById(R.id.button_connexion);
        cancelUserDataButton = popupInputDialogView.findViewById(R.id.button_cancel_user_data);
        initEditTextUserDataInPopupDialog();
    }

    private void initEditTextUserDataInPopupDialog()
    {
        List<String> userDataList = getExistUserDataInListView(userDataListView);
        if(userDataList.size() == 2)
        {
            String userName = userDataList.get(0);
            String password = userDataList.get(1);
            if(userNameEditText != null) {
                userNameEditText.setText(userName);
            }
            if(passwordEditText != null)
            {
                passwordEditText.setText(password);
            }
        }
    }

    private List<String> getExistUserDataInListView(ListView listView)
    {
        List<String> ret = new ArrayList<>();
        if(listView != null)
        {
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
