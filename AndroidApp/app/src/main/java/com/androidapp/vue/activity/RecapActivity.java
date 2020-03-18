package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.androidapp.R;
public class RecapActivity extends AppCompatActivity {
    private TextView semestre1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);

        Intent intent=getIntent();
        String sname=intent.getStringExtra("matièresChoisisS1");
        semestre1=(TextView)findViewById(R.id.semestre1);
        semestre1.setText(sname);
    }
}
