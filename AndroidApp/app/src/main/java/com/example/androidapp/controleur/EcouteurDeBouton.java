package com.example.androidapp.controleur;
import android.util.Log;
import android.view.View;

import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.reseau.Connexion;

import constantes.Net;
import io.socket.client.Socket;

public class EcouteurDeBouton {
    private final Connexion mSocket;

    public EcouteurDeBouton(Connexion mSocket){

        this.mSocket = mSocket;
    }
    }

