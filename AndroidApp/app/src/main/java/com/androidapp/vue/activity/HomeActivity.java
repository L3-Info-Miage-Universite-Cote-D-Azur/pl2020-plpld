package com.androidapp.vue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import constantes.Net;
import metier.Identité;

public class HomeActivity extends AppCompatActivity {
    public static Connexion connexion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        connexion = new Connexion();
        connexion.écouterRéseau();
        connexion.envoyerMessage(Net.CONNEXION, new Identité("AndroidApp"));

        Button button=findViewById(R.id.btnpar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this, Semestre1Activity.class);
                startActivity(intent);
            }
        });
    }
}
