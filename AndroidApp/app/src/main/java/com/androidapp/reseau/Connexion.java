package com.androidapp.reseau;
import java.net.URISyntaxException;

import constantes.Net;
import io.socket.client.IO;
import io.socket.client.Socket;
import metier.ToJSON;

import com.androidapp.controleur.*;
import com.androidapp.vue.*;

public class Connexion {


    private Vue vue;

    public Connexion(Vue vue) {
        setVue(vue);
    }

    private Socket mSocket;


    public void écouterRéseau() {
        try {
            mSocket = IO.socket("http://10.0.2.2:10101");

            EcouteurDeReseau net = new EcouteurDeReseau(getVue());

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

    public void setVue(Vue vue) {
        try {
            mSocket = IO.socket("http://10.0.2.2:10101");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        this.vue = vue;
    }

    public Vue getVue() {
        return vue;
    }

    public void disconnect() {
        if (mSocket != null) mSocket.disconnect();
    }

    public void envoyer(String msg) {
        mSocket.emit(msg);
    }
}