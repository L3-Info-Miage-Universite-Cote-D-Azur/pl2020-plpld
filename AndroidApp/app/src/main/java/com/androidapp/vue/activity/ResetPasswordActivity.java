package com.androidapp.vue.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import com.androidapp.R;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class ResetPasswordActivity extends AppCompatActivity {

    private EditText numEtudiant;
    private EditText dateNaissance;
    private Button btnResetPassword;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpassword);

        numEtudiant = (EditText) findViewById(R.id.edt_reset_numEtudiant);
        dateNaissance = findViewById(R.id.edt_reset_dateNaissance);
        btnResetPassword = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);

        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String num = numEtudiant.getText().toString().trim();
                String date = numEtudiant.getText().toString().trim();

                if (TextUtils.isEmpty(num) || TextUtils.isEmpty(date)) {
                    Toast.makeText(getApplicationContext(), "Merci d'entrer votre numéro étudiant et votre date de naissance.", Toast.LENGTH_SHORT).show();
                    return;
                }

                //TODO 23-04-2020: Remplir ici l'action à effectuer pour donner le mot de passe si combinaison numétudiant/datenaissance correct
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
