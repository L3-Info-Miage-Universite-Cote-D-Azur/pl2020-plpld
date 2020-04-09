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
    private Vue mainVue;
    public Map<String,List<String>> MapPrerequis = new HashMap<>();
    public Map<Integer, List<String>> selectionUE = new HashMap<>();
    public Map<String, Map<Integer, List<String>>> MapPredefini = new HashMap<>();
    public String predefini = "Personnalisé";

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
                try {
                    HashMap<String, List<String>> map = objectMapper.readValue(args[0].toString(), new TypeReference<Map<String, List<String>>>() {
                    });
                    mainVue.receptionUE(map);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        mSocket.on(Net.ENVOIE_S1, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    HashMap<String, List<String>> map = objectMapper.readValue(args[0].toString(), new TypeReference<Map<String, List<String>>>() {
                    });
                    mainVue.receptionUE(map);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        mSocket.on(Net.PREDEFINI, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    Map<String, Map<Integer, List<String>>> map = objectMapper.readValue(args[0].toString(), new TypeReference<Map<String, Map<Integer, List<String>>>>() {
                    });
                    MapPredefini = map;
                    mainVue.receptionPredefini(map);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
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