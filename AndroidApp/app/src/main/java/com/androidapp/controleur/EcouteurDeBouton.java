package com.androidapp.controleur;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import com.androidapp.MainActivity;
import com.androidapp.reseau.*;
import com.androidapp.R;
import com.androidapp.reseau.*;
import com.androidapp.vue.*;
import io.socket.client.Socket;

public class EcouteurDeBouton extends AppCompatActivity implements View.OnClickListener {

    private final Connexion mSocket;
    private final Vue vue;



    public EcouteurDeBouton(Vue v, Connexion mSocket) {
        this.vue = v;
        this.mSocket = mSocket;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonValider:
                Log.d("POUR MONTRER", "EcouteurDeBouton : bouton valider cliqué");
                if (vue.selection()==null)
                    vue.displayMsg("Selection invalide (veuillez cocher une matière parmi les choix proposés)");
                else {
                    mSocket.envoyerMessage(Net.VALIDATION, new Matiere(vue.selection()));
                    vue.displayMsg("Votre choix a été transmis au serveur");
                }
                break;
        }
    }
}