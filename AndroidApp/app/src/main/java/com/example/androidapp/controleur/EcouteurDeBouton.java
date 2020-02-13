package controleur;
import android.util.Log;
import android.view.View;

import com.example.androidapp.MainActivity;
import com.example.androidapp.R;
import com.example.androidapp.reseau.Connexion;
import com.example.androidapp.vue.Vue;

import constantes.NET;
import io.socket.client.Socket;

public class EcouteurDeBouton implements View.OnClickListener {

    private final Connexion mSocket;
    private final Vue vue;



    public EcouteurDeBouton(Connexion mSocket) {

        this.mSocket = mSocket;
    }

    @Override
    public void onClick() {
        /*
        switch (v.getId()) {
            case R.id.button:
                Log.d("POUR MONTRER", "on a cliqué");
                modèle.ajouter();
                break;
            case R.id.text:
                modèle.raz();
                break;
        }
           */

        mSocket.envoyer(NET.AJOUTER);

    }



}

