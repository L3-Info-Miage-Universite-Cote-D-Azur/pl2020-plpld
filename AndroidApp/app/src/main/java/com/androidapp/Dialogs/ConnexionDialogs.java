package com.androidapp.Dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import com.androidapp.R;


/**
 *  Classe ConnexionDialogs qui s'occupe de créer des Dialogs Android pour la connexion de l'étudiant.
 *  Un Dialog est affiché sur l'écran, qui informe l'utilisateur si la connexion est réussie ou non
 */

public class ConnexionDialogs extends DialogFragment {


    public void onCreateDialog(Bundle savedInstanceState, final FragmentActivity Activity, Boolean check) {

        if(Activity != null)
        {

            if (check) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity);
                builder.setMessage(R.string.ConnexionReussie)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                // On crée le dialog
                builder.create();
                builder.setCancelable(false);
                builder.show();

            }

            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity);
                builder.setMessage(R.string.identifiant)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                builder.create();
                builder.show();
            }
        }
        }

    public void onCreateDialog(Bundle savedInstanceState, final FragmentActivity Activity, Boolean check,String etu) {

        if(Activity != null)
        {

            if (check) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Activity);
                builder.setMessage(R.string.InscriptionReussie)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        });

                // On crée le dialog
                builder.create();
                builder.setCancelable(false);
                builder.show();

            }

            else
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity);
                builder.setMessage(R.string.InscriptionRefusee)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            }
                        });

                builder.create();
                builder.show();
            }
        }
    }

}


