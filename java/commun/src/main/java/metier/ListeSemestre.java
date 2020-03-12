package metier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListeSemestre {

    public HashMap<String, List<String>> getMapUE() {
        return mapUE;
    }


    public void add(String key,List<String> value)
    {
        mapUE.put(key,value);
    }

    public void setMapUE(HashMap<String, List<String>> mapUE) {
        this.mapUE = mapUE;
    }

    HashMap<String, List<String>> mapUE = new HashMap<>();
}
