package controleur;
import android.util.Log;

import com.example.androidapp.MainActivity;
import com.example.androidapp.reseau.Connexion;

import static constantes.NET;
import io.socket.client.Socket;

public class EcouteurDeBouton {

    private final Connexion mSocket;



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

