package com.example.sensorapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    EditText edtTemp, edtHum, edtCO2, edtAforo;
    Button btnRegistrar;
    String tieUsuario, tieClave;
    private  String userKey, fecha, hora;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializar el componente mAuth conectándolo al servicio Authentication
        //de Firebase.
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        edtTemp = findViewById(R.id.edtTemperatura);
        edtHum = findViewById(R.id.edtHumedad);
        edtCO2 = findViewById(R.id.edtCO2);
        edtAforo = findViewById(R.id.edtAforo);
        btnRegistrar = findViewById(R.id.btnMainActivityRegistrar);

        FirebaseUser fbusuario = mAuth.getCurrentUser();

    }

    //Detección del click del botón registrar:
    @Override
    public void onStart(){
        super.onStart();
        DatabaseReference mDbRef = mDatabase.getReference("SOTR");
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userKey = mDbRef.push().getKey();
                double temp = Double.parseDouble(edtTemp.getText().toString());
                double hum = Double.parseDouble(edtHum.getText().toString());
                int co2 = Integer.parseInt(edtCO2.getText().toString());
                int aforo = Integer.parseInt(edtAforo.getText().toString());

                Date fecha0 = Calendar.getInstance().getTime();
                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                String fecha = format1.format(fecha0);

                Date hora0 = Calendar.getInstance().getTime();
                SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
                String hora = format2.format(hora0);


                Sensor obj = new Sensor(temp, hum, co2, aforo, fecha,hora);
                //obj.setKey(userKey);
                mDbRef.child(userKey).setValue(obj);
                Toast.makeText(MainActivity.this,"Datos subidos a Firebase",
                        Toast.LENGTH_SHORT).show();
            }
        });

        //Crear una clase anónima que contenga un método abstracto que detecte los cambios en la base de
        //datos real time database de Firebase y al detectar un cambio lo actualice en el App
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {//Cuando hay cambios en la base de datos que apunta mDbRef
                for(DataSnapshot child:snapshot.getChildren()){
                    //Log.i("UserKey",child.getKey());
                    Sensor obj = child.getValue(Sensor.class);
                    //Log.i("Nombre",obj.getNombre());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}