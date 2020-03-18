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
        Intent intent=getIntent();
        final String sname=intent.getStringExtra("matièresChoisisS1");

        Intent intent2=new Intent(Semestre2Activity.this, Semestre3Activity.class);
        intent2.putExtra("matièresChoisisS1",sname);
        startActivity(intent2);
    }
}
