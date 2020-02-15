package com.androidapp.controleur;

import android.util.Log;

import com.androidapp.MainActivity;

import io.socket.emitter.Emitter;

public class EcouteurDeReseau implements Emitter.Listener {

    @Override
    public void call(Object... args) {
        Log.d("POUR MONTRER", ""+args[0]);
    }
}