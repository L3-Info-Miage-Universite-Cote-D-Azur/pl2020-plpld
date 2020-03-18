package com.androidapp.reseau;
import java.net.URISyntaxException;

import constantes.Net;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import metier.ToJSON;

import com.androidapp.controleur.*;
import com.androidapp.vue.*;

public class Connexion {
    private Socket mSocket;
    public static String s;
    public void écouterRéseau() {
        try {
            mSocket = IO.socket("http://10.0.2.2:10101");
            EcouteurDeReseau net = new EcouteurDeReseau();
            mSocket.on(Net.UE, net);

            mSocket.on(Net.VALIDATION, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    s=(String) args[0];
                }
            });
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