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
    private TextView semestre1,semestre2,semestre3,semestre4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recap);

        Intent intent=getIntent();
        String sname=intent.getStringExtra("matièresChoisisS1");
        String sname2=intent.getStringExtra("matièresChoisisS2");
        String sname3=intent.getStringExtra("matièresChoisisS3");
        String sname4=intent.getStringExtra("matièresChoisisS4");

        semestre1=(TextView)findViewById(R.id.semestre1);
        semestre1.setText(sname);

        semestre2=(TextView)findViewById(R.id.semestre2);
        semestre2.setText(sname2);

        semestre3=(TextView)findViewById(R.id.semestre3);
        semestre3.setText(sname3);

        semestre4=(TextView)findViewById(R.id.semestre4);
        semestre4.setText(sname4);


    }
}
