package com.androidapp.reseau;
import android.util.Log;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import constantes.Net;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import metier.ToJSON;

import com.androidapp.controleur.*;
import com.androidapp.vue.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum Connexion {
    CONNEXION;
    private Socket mSocket;
    private Boolean ConnexionAutorisee = false;
    private List<Map<String, List<String>>> ListOfMaps = new ArrayList<Map<String,List<String>>>();
    private Vue mainVue;
    public Map<String,List<String>> MapPrerequis = new HashMap<>();
    private String s;
    public void écouterRéseau() {
        try {
            mSocket = IO.socket("http://10.0.2.2:10101");
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }

        mSocket.on(Net.UE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String, List<String>> map = null;
                try {
                    map = objectMapper.readValue(args[0].toString(), new TypeReference<Map<String, List<String>>>() {
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ListOfMaps.add(map);
                if(mainVue!=null)
                mainVue.receptionUE(map);
            }
        });

        mSocket.on(Net.VALIDATION, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                s=(String)args[0];
            }
        });

        mSocket.on(Net.NV_CONNEXION, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Log.d("BOOLEAN",args[0].toString());
                if(args[0].toString().equals("true"))
                {
                    Log.d("BOOLEAN","PASSE");

                    ConnexionAutorisee = true;
            }}
        });
        mSocket.on(Net.PREREQUIS, new Emitter.Listener() {
            ObjectMapper objectMapper2 = new ObjectMapper();

            @Override
            public void call(Object... args) {
                try {
                    MapPrerequis = objectMapper2.readValue(args[0].toString(), new TypeReference<Map<String, List<String>>>() {
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void démarrerÉcoute() {
        mSocket.connect();
    }

    public void envoyerMessage(String msg, ToJSON obj) {
        mSocket.emit(msg, obj.toJSON());
    }
    public void envoyerMessage2(String msg, ToJSON obj) {
        mSocket.emit(msg, obj);
    }
    public void setMainVue(Vue v) {mainVue = v;}
    public void disconnect() {
        if (mSocket != null) mSocket.disconnect();
    }

    public void envoyer(String msg) {
        mSocket.emit(msg);
    }

    public Boolean getConnexionAutorisee() {
        return ConnexionAutorisee;
    }

    public void setConnexionAutorisee(Boolean connexionAutorisee) {
        ConnexionAutorisee = connexionAutorisee;
    }
}