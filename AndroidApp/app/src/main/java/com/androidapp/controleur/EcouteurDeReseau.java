package com.androidapp.controleur;

import android.util.Log;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.emitter.Emitter;

public class EcouteurDeReseau implements Emitter.Listener {
    public static List<Map<String,List<String>>> ListOfMaps = new ArrayList<Map<String,List<String>>>();
    ObjectMapper objectMapper = new ObjectMapper();
    HashMap<String,List<String>> map;
    @Override
    public void call(Object... args) {
        Log.d("POUR MONTRER", "" + args[0]);
        try {
            map = objectMapper.readValue(args[0].toString(), new TypeReference<Map<String,List<String>>>() {});
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        ListOfMaps.add(map);
    }
}