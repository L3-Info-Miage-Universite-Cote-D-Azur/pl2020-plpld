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
        startActivity(new Intent(Semestre3Activity.this, Semestre4Activity.class));
    }
}