package com.androidapp.vue;

import com.androidapp.reseau.Connexion;
import com.androidapp.reseau.*;

import java.util.List;

public interface Vue {
    void displayMsg(String str);
    List<Matiere> selection();
}

