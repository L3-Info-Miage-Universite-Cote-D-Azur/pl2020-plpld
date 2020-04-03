package com.androidapp.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.androidapp.R;
import com.androidapp.vue.activity.MainActivity;

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

}