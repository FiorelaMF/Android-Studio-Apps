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

// TODO 5: Realizar la deteccion de clicks de los botones
public class MainActivity extends AppCompatActivity {

    private final int CODIGO_INTENT_REGISTRO = 1;
    private Button btnIngresar, btnRegistrar;
    private EditText edtUsuario, edtClave, edtNombre;
    private Spinner spiFacultad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Enlazamos los componentes Java con xml
        btnIngresar = findViewById(R.id.btnIngresar);
        btnRegistrar = findViewById(R.id.btnRegistrar);
        edtUsuario = findViewById(R.id.edtUsuario);
        edtClave = findViewById(R.id.edtClave);
        edtNombre = findViewById(R.id.edtNombreRegistro);
        spiFacultad = findViewById(R.id.spiFacultad);

        //Eliminar el AppBar
        getSupportActionBar().hide();
        //Eliminar el giro de telefono
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Creamos el evento anonimo para atender los clicks del boton de registro
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,RegistrarActivity.class);
                startActivityForResult(intent,CODIGO_INTENT_REGISTRO);

            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);
        int login = pref.getInt("LOGIN", 0);
        if(login == 1){
            Intent intent = new Intent(MainActivity.this, MenuPrincipalActivity.class);
            startActivity(intent);
            finish();
        }

        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String correo = edtUsuario.getText().toString();
                String clave = edtClave.getText().toString();
                SharedPreferences pref = getSharedPreferences("usuario.xml", MODE_PRIVATE);
                String correoG = pref.getString("CORREO", null);
                String claveG = pref.getString("CLAVE", null);
                if(correoG!=null && claveG!=null){
                    if(correo.equals(correoG) && clave.equals(claveG)){
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("LOGIN", 1);
                        editor.commit();
                        Intent intent = new Intent(MainActivity.this, MenuPrincipalActivity.class);
                        startActivity(intent);
                        finish();
                    } else{
                        Toast.makeText(MainActivity.this, "Su correo y clave no son correctos.", Toast.LENGTH_SHORT).show();
                    }
                } else{
                    Toast.makeText(MainActivity.this, "Debe registrar un usuario primero para poder entrar", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }

    // TODO 6: Funcion del evento click del boton  olvido clave
    public void btnOlvidoClave(View view){
        Intent intent = new Intent(this,OlvidoActivity.class);
        startActivity(intent); // Me voy a OlvidoActivity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==CODIGO_INTENT_REGISTRO){
            if(resultCode==RESULT_OK){
                String valor = data.getStringExtra("CORREO");
                edtUsuario.setText(valor);

            }
        }
    }

}