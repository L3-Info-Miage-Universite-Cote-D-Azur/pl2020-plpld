package com.androidapp.reseau;

import com.androidapp.controleur.*;


import java.net.URISyntaxException;

import java.net.URISyntaxException;

import com.androidapp.reseau.Net;
import io.socket.client.IO;
import io.socket.client.Socket;
import com.androidapp.reseau.ToJSON;

public class Connexion {




    private Socket mSocket;


    public void écouterRéseau() {
        try {
            mSocket = IO.socket("http://10.0.2.2:10101");

            EcouteurDeReseau net = new EcouteurDeReseau();

            mSocket.on(Net.VALEUR_CPT, net);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void démarrerÉcoute() {
        mSocket.connect();

    }

    public void envoyerMessage(String msg, ToJSON obj) {

        mSocket.emit(msg, obj);
    }




    public void disconnect() {
        if (mSocket != null) mSocket.disconnect();
    }

    public void envoyer(String msg) {
        mSocket.emit(msg);
    }
}