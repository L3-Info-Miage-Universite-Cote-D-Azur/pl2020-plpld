package com.androidapp.exception;

import android.content.DialogInterface;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.androidapp.vue.activity.HomeActivity;

public class ConnexionServeurException extends Exception{
    long tempsAttente;
    AppCompatActivity context;

    public ConnexionServeurException(long tempsAttente, AppCompatActivity context, int pid) {
        this.tempsAttente = tempsAttente;
        this.context = context;
        Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
        Intent Home = new Intent(context, HomeActivity.class);
        Home.putExtra("Erreur", "La connexion au serveur a échoué malgré " + tempsAttente/1000 + " secondes d'attente. Vérifiez l'état du serveur et réessayez.");
        context.startActivity(Home);
        android.os.Process.killProcess(pid);
    }
}