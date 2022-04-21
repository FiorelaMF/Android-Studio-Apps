package com.upc.ingenieroselectronicos.ejemplofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//TODO 4: Creamos la aplicación en JAVA para logearnos
public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextInputEditText tieUsuario, tieClave;
    Button btnIngresar,btnRegistrar,btnOlvido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Inicializar el componente mAuth conectándolo al servicio Authentication
        //de Firebase.
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tieUsuario = findViewById(R.id.tieMainActivityUsuario);
        tieClave = findViewById(R.id.tieMainActivityClave);
        btnIngresar = findViewById(R.id.btnMainActivityIngresar);
        btnRegistrar = findViewById(R.id.btnMainActivityRegistrar);
        btnOlvido = findViewById(R.id.btnMainActivityOlvido);

        btnIngresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mAuth.signInWithEmailAndPassword(tieUsuario.getText().toString(),tieClave.getText().toString())
                        .addOnCompleteListener(MainActivity.this,new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Se invoca cuando hay respuesta del servicio de AUtentificación de Firebase
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(MainActivity.this,MenuPrincipalActivity.class);
                                    FirebaseUser usuarioActual = mAuth.getCurrentUser();
                                    intent.putExtra("USUARIO",usuarioActual.getEmail());
                                    startActivity(intent);
                                    finish();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"El usuario o la clave están errados",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });//Cierre de la clase anónima del click del botón

        //Detección del click del botón registrar:
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.createUserWithEmailAndPassword(tieUsuario.getText().toString(),tieClave.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                //Método de respuesta de Firebase diciéndo si pudo o no registrar al usuario
                                if(task.isSuccessful()){
                                    Toast.makeText(MainActivity.this,"Usuario registrado. Ahora ingrese con el usuario y "
                                    +"contraseña ingresados.",Toast.LENGTH_SHORT).show();
                                    mAuth.signOut();
                                }
                                else{
                                    Toast.makeText(MainActivity.this,"Revise los datos ingresados. Algo falló "
                                            +"y no se puede registrar.",Toast.LENGTH_SHORT).show();
                                }
                            }

                });
            }
        });
    }

    public void onStart(){
        super.onStart();
        FirebaseUser usuario = mAuth.getCurrentUser();
        if(usuario!=null){
            Intent intent = new Intent(this,MenuPrincipalActivity.class);
            intent.putExtra("USUARIO",usuario.getEmail());
            startActivity(intent);
            finish();
        }

        btnOlvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,OlvidoActivity.class);
                startActivity(intent);
            }
        });
    }
}