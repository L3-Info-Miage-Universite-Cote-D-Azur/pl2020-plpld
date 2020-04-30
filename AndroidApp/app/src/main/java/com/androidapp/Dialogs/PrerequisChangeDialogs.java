package com.androidapp.Dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.androidapp.R;

public class PrerequisChangeDialogs  extends DialogFragment {

    public boolean checked = false;
    public void onCreateDialog(Bundle savedInstanceState, final FragmentActivity Activity) {

        if(Activity != null)
        {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity);
                builder.setMessage(R.string.PrerequisChange)
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                    checked = true;
                            }
                        });

                // On crée le dialog
                builder.create();
                builder.setCancelable(false);
                builder.show();

        }
    }
    public void onCreateDialog(Bundle savedInstanceState, final FragmentActivity Activity,String customMessage) {

        if(Activity != null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(Activity);
            builder.setMessage(customMessage)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });

            // On crée le dialog
            builder.create();
            builder.setCancelable(false);
            builder.show();

        }
    }


}
