package com.androidapp.vue.activity;
import android.content.Intent;
import android.os.Bundle;
import com.androidapp.vue.Vue;

public class Semestre4Activity extends Semestre1Activity implements Vue {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numSemestre = 4;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void changementSemestre() {
        startActivity(new Intent(Semestre4Activity.this, RecapActivity.class));
    }
}