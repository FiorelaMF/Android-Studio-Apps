package com.upc.ingenieroselectronicos.ejemplofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
//TODO 7: Vamos a solicitar una nueva contraseña

public class OlvidoActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextInputEditText tieCorreo;
    private Button btnPedir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido);

        mAuth = FirebaseAuth.getInstance();
        tieCorreo = findViewById(R.id.tieOlvidoActivityMailOlvido);
        btnPedir = findViewById(R.id.btnOlvidoActivityPedirClave);

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnPedir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.setLanguageCode("es");
                mAuth.sendPasswordResetEmail(tieCorreo.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(OlvidoActivity.this,"Ha recibido un correo para "+
                                    "restablecer su contraseña",Toast.LENGTH_SHORT).show();
                            finish();
                        }else{
                            Toast.makeText(OlvidoActivity.this,"No se pudo restablecer la clave. "+
                                    "Verifique que esté registrada la cuenta.",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }
}