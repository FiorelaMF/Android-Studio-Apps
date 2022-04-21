package com.upc.ingenieroselectronicos.ejemplofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.upc.ingenieroselectronicos.ejemplofirebase.MisClases.Donante;

import java.util.Calendar;
import java.util.List;

//TODO 6: Vamos a agregar el menú principal y permitir que el usuario done dinero
public class MenuPrincipalActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private Button btnEnviar;
    private EditText edtNombre, edtCorreo, edtDonacion, edtFecha;
    private RadioButton rbMasculino, rbFemenino;
    private  String userKey, fecha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        mAuth = FirebaseAuth.getInstance();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        btnEnviar = findViewById(R.id.btnMenuPrincipalActivityEnviar);
        edtNombre = findViewById(R.id.edtMenuPrincipalActivityNombre);
        edtCorreo = findViewById(R.id.edtMenuPrincipalActivityCorreo);
        edtDonacion = findViewById(R.id.edtMenuPrincipalActivityDonacion);
        edtFecha = findViewById(R.id.edtMenuPrincipalActivityFecha);
        rbFemenino = findViewById(R.id.rbMenuPrincipalActivityFemenino);
        rbMasculino = findViewById(R.id.rbMenuPrincipalActivityMasculino);

        FirebaseUser fbusuario = mAuth.getCurrentUser();
        edtCorreo.setText(fbusuario.getEmail());
        edtCorreo.setEnabled(false);

    }

    @Override
    public void onStart(){
        super.onStart();
        DatabaseReference mDbRef = mDatabase.getReference("Donadores/Datos");
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userKey = mDbRef.push().getKey();
                String nombre = edtNombre.getText().toString();
                String correo = edtCorreo.getText().toString();
                double donacion = Double.parseDouble(edtDonacion.getText().toString());
                boolean sexof;
                if(rbFemenino.isChecked())
                    sexof=true;
                else
                    sexof=false;
                Donante obj = new Donante(nombre, correo, sexof,fecha,donacion,"ninguna");
                obj.setKey(userKey);
                mDbRef.child(userKey).setValue(obj);
                Toast.makeText(MenuPrincipalActivity.this,"Datos subidos a Firebase",
                        Toast.LENGTH_SHORT).show();

            }
        });
        //Crear una clase anónima que contenga un método abstracto que detecte los cambios en la base de
        //datos real time database de Firebase y al detectar un cambio lo actualice en el App
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {//Cuando hay cambios en la base de datos que apunta mDbRef
                    for(DataSnapshot child:snapshot.getChildren()){
                        Log.i("UserKey",child.getKey());
                        Donante obj = child.getValue(Donante.class);
                        Log.i("Nombre",obj.getNombre());
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Vamos a levantar un DatePickerDIalog cuando ingresemos la fecha. COn el objetivo de no
        //meter una fecha en un mal formato o que no exista
        edtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                //Date Time Picker
                DatePickerDialog datePickerDialog;
                datePickerDialog = new DatePickerDialog(MenuPrincipalActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                                edtFecha.setText(dayOfMonth+"-"+(monthOfYear+1)+"-"+year);
                                fecha = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                            }
                        },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.menuprincipallistaDonantes:
                Intent intent2 = new Intent(this, ListaDonantesActivity.class);
                startActivity(intent2);
                break;
            case R.id.menuprincipalCerrar:
                mAuth.signOut();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                finish();
            case R.id.menuprincipalSalir:
                finishAffinity();
        }
        return super.onOptionsItemSelected(item);
    }
}