package com.androidapp.vue.activity;

import android.content.Intent;
import android.os.Bundle;
import com.androidapp.vue.Vue;

public class Semestre2Activity extends Semestre1Activity implements Vue {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        numSemestre = 2;
        super.onCreate(savedInstanceState);
    }

    @Override
    public void changementSemestre() {
        startActivity(new Intent(Semestre2Activity.this, Semestre3Activity.class));
    }
}
