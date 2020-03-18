package com.androidapp.vue.activity;

import android.content.Intent;
import android.os.Bundle;
import com.androidapp.vue.Vue;

public class Semestre3Activity extends Semestre1Activity implements Vue {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numSemestre = 3;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void changementSemestre() {
        Intent intent=getIntent();
        final String sname=intent.getStringExtra("matièresChoisisS1");

        Intent intent2=new Intent(Semestre3Activity.this, Semestre4Activity.class);
        intent2.putExtra("matièresChoisisS1",sname);
        startActivity(intent2);
    }
}