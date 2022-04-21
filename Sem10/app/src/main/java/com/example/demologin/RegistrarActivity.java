package com.example.demologin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

//TODO 12: Crear clase de registro de nuevo usuario
public class RegistrarActivity extends AppCompatActivity {
    private EditText edtCorreo, edtClave, edtNombre;
    private Button btnRegistrar;
    private Spinner spiFacultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        edtCorreo = findViewById(R.id.edtUsuarioRegistro);
        edtClave = findViewById(R.id.edtClaveRegistro);
        edtNombre = findViewById(R.id.edtNombreRegistro);
        btnRegistrar = findViewById(R.id.btnCrearUsuario);
        spiFacultad = findViewById(R.id.spiFacultad);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RevisarRegistro obj = new RevisarRegistro(edtCorreo, edtClave, edtNombre);
                if(obj.isClaveOK() && obj.isCorreoOK() && obj.isNombreOK()){
                    //Guardar los datos del registro en un archivo en Android
                    SharedPreferences pref = getSharedPreferences("usuario.xml", MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("CORREO",obj.getCorreo());
                    editor.putString("NOMBRE",obj.getNombre());
                    editor.putString("CLAVE",obj.getClave());
                    editor.putString("FACULTAD",spiFacultad.getSelectedItem().toString());
                    editor.putInt("LOGIN",0);
                    editor.commit(); //Graba los putStrings y putInt
                    Toast.makeText(RegistrarActivity.this, "Usuario registrado satisfactoriamente", Toast.LENGTH_SHORT).show();


                    //Intent para el ActivityForResult
                    Intent intent = new Intent();
                    intent.putExtra("CORREO", obj.getCorreo());
                    setResult(RESULT_OK, intent);
                    finish();
                }   else{
                    Toast.makeText(RegistrarActivity.this,"no se puede registrar."+
                            "Revise los daatos ingresados",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}