package com.androidapp.controleur;

import android.util.Log;

import com.androidapp.vue.*;

import io.socket.emitter.Emitter;

public class EcouteurDeReseau implements Emitter.Listener {
    private final Vue vue;

    public EcouteurDeReseau(Vue vue) {
        this.vue = vue;
    }

    @Override
    public void call(Object... args) {
        Log.d("POUR MONTRER", "" + args[0]);
        vue.displayMsg("Connexion serveur effectuée");
    }
}