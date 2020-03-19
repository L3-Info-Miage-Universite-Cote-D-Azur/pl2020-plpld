package com.androidapp.vue.activity;
import android.content.Intent;
import android.os.Bundle;

import com.androidapp.reseau.Connexion;
import com.androidapp.vue.Vue;

public class Semestre4Activity extends Semestre1Activity implements Vue {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numSemestre = 4;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void changementSemestre() {
        Intent intent=getIntent();
        final String sname=intent.getStringExtra("matièresChoisisS1");
        final String sname2=intent.getStringExtra("matièresChoisisS2");
        final String sname3=intent.getStringExtra("matièresChoisisS3");


        Intent intent2=new Intent(Semestre4Activity.this, RecapActivity.class);
        intent2.putExtra("matièresChoisisS1",sname);
        intent2.putExtra("matièresChoisisS2",sname2);
        intent2.putExtra("matièresChoisisS3",sname3);
        intent2.putExtra("matièresChoisisS4", Connexion.s);

        startActivity(intent2);
    }
}