package metier;

import org.json.JSONException;
import org.json.JSONObject;

import metier.ToJSON;

import org.json.JSONException;
import org.json.JSONObject;

public class Model implements ToJSON {
    private String text;
    private boolean isSelected = false;

    public Model(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public JSONObject toJSON() {
        JSONObject model = new JSONObject();
        try{
            model.put("nom",text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return model;
    }
}
