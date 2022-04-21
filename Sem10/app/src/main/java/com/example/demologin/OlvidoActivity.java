package com.example.demologin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// TODO 8: Solicitar la contraseña (falso) y regresar al Main Activity
public class OlvidoActivity extends AppCompatActivity {
    private EditText edtUsuario;
    private Button btnEnviar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido);

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        edtUsuario = findViewById(R.id.edtUSuarioOlvido);
        btnEnviar = findViewById(R.id.btnPedirClave);

        //Deteccion de clicks de un objeto mediante clases anonimas
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish(); // Mata el OlvidoActivity
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(OlvidoActivity.this, "Correo enviado y actividad OlvidoActivity destruida", Toast.LENGTH_SHORT).show();
    }
}