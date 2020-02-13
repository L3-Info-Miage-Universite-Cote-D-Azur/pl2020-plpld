package reseau;

import com.example.androidapp.controleur.EcouteurDeReseau;


import java.net.URISyntaxException;

import constantes.NET;
import io.netty.channel.unix.Socket;
import io.socket.client.IO;
import metier.ToJSON;

public class Connexion {




    private Socket mSocket;


    public void écouterRéseau() {
        try {
            mSocket = IO.socket("http://10.0.2.2:10101");

            EcouteurDeReseau net = new EcouteurDeReseau();

            mSocket.on(NET.VALEUR_CPT, net);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void démarrerÉcoute() {
        mSocket.connect();

    }

    public void envoyerMessage(String msg, ToJSON obj) {

        mSocket.emit(msg, obj);
    }




    public void disconnect() {
        if (mSocket != null) mSocket.disconnect();
    }

    public void envoyer(String msg) {
        mSocket.emit(msg);
    }
}