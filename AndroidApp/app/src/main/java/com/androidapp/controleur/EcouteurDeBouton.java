package com.androidapp.controleur;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.androidapp.MainActivity;
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
                Log.d("POUR MONTRER", "on a cliqué");
                break;
        }
        mSocket.envoyer(Net.AJOUTER);
    }
}

