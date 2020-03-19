package com.androidapp.vue.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.androidapp.reseau.Connexion;
import static com.androidapp.controleur.EcouteurDeBouton.selectionUE;
import com.androidapp.vue.Vue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import metier.Matiere;

public class Semestre2Activity extends Semestre1Activity implements Vue {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numSemestre = 2;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void changementSemestre() {
        Intent intent=getIntent();
        final String sname=intent.getStringExtra("matièresChoisisS1");

        Intent intent2=new Intent(Semestre2Activity.this, Semestre3Activity.class);
        intent2.putExtra("matièresChoisisS1",sname);
        intent2.putExtra("matièresChoisisS2", Connexion.s);

        startActivity(intent2);
    }
}