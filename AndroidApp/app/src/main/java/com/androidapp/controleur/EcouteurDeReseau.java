package com.androidapp.controleur;

import android.util.Log;

import com.androidapp.vue.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.socket.emitter.Emitter;

public class EcouteurDeReseau implements Emitter.Listener {
    private final Vue vue;
    public static List<Map<String,List<String>>> ListOfMaps = new ArrayList<Map<String,List<String>>>();
    public EcouteurDeReseau(Vue vue) {
        this.vue = vue;
    }

    @Override
    public void call(Object... args) {
        Log.d("POUR MONTRER", "" + args[0]);
        String[] tokens = args[0].toString().split("],");
        HashMap<String,List<String>> map = new HashMap<>();
        for( String str : tokens)
        {
            String[] matieres = str.substring(str.lastIndexOf(":[")).replace(":[","").replace("\"" ,"").replace("]}","").split(",");
            map.put(str.substring(0,str.indexOf(":[")).replace("\"","").replace("{",""), Arrays.asList(matieres));
        }
        ListOfMaps.add(map);
    }
}