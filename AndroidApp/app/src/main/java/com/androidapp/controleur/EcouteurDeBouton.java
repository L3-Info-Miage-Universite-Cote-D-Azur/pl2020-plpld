package com.androidapp.controleur;
import android.util.Log;
import android.view.View;

import com.androidapp.*;
import com.androidapp.R;
import com.androidapp.reseau.Connexion;

import io.socket.client.Socket;

public class EcouteurDeBouton {
    private final Connexion mSocket;

    public EcouteurDeBouton(Connexion mSocket){

        this.mSocket = mSocket;
    }
    }

