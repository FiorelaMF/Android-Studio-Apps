package app.acsolutions.ventisysupc.MENUS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.Objects;

import app.acsolutions.ventisysupc.COMUNI.OlvidoActivity;
import app.acsolutions.ventisysupc.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button ENVIAR;
    private TextView OLVIDO;
    private EditText CORREO,CONTRA;
    private Intent OLVIDADO,CLIENTE,ADMIN;
    private ProgressDialog PROGRESO;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String DATO,TIP,COD,CLA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ENVIAR = findViewById(R.id.envio);
        OLVIDO = findViewById(R.id.olvido);
        CORREO = findViewById(R.id.usuario);
        CONTRA = findViewById(R.id.contra);
        OLVIDADO = new Intent(this, OlvidoActivity.class);
        CLIENTE = new Intent(this, MenuClienteActivity.class);
        ADMIN = new Intent(this, MenuAdminActivity.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            String[] PERPER = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.INTERNET};
            int PER_WRIT = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int PER_READ = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int PER_IMEI = checkSelfPermission(Manifest.permission.READ_PHONE_STATE);
            int PER_NETW = checkSelfPermission(Manifest.permission.INTERNET);
            int PER_CAMA = checkSelfPermission(Manifest.permission.CAMERA);
            if (PER_NETW == PackageManager.PERMISSION_GRANTED  && PER_IMEI == PackageManager.PERMISSION_GRANTED && PER_CAMA == PackageManager.PERMISSION_GRANTED && PER_WRIT == PackageManager.PERMISSION_GRANTED && PER_READ == PackageManager.PERMISSION_GRANTED) {
                Log.i("Mensaje", "Tengo acceso a todo");
            } else {
                requestPermissions(PERPER, 100);
            }
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences PREF = getSharedPreferences("usuarios.xml",MODE_PRIVATE);
        String COCO = PREF.getString("CODIGO",null);
        String CONT = PREF.getString("CLAVE",null);
        if (COCO != null && CONT != null){
            PROGRESO = ProgressDialog.show(this,"¡Cargando!", "Por favor espere...",false,false);
            db.collection("usuarios").document(COCO)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (Objects.requireNonNull(document).exists()) {
                                    DATO = Objects.requireNonNull(document.getData()).toString();
                                    SacarValores(DATO);
                                    if (CONT.equals(CLA)){
                                        if (TIP.equals("ADMIN")){
                                            ADMIN.putExtra("CODIGO",COD);
                                            startActivity(ADMIN);
                                        }
                                        else if (TIP.equals("USER") || TIP.equals("DOCEN")){
                                            CLIENTE.putExtra("CODIGO",COD);
                                            startActivity(CLIENTE);
                                        }
                                        PROGRESO.dismiss();
                                    }
                                    else{
                                        PROGRESO.dismiss();
                                        CONTRA.setText("");
                                        SharedPreferences.Editor EDIT = PREF.edit();
                                        EDIT.putString("CODIGO",null);
                                        EDIT.putString("CLAVE",null);
                                        EDIT.apply();
                                    }
                                }
                                else {
                                    PROGRESO.dismiss();
                                    CONTRA.setText("");
                                    CORREO.setText("");
                                    SharedPreferences.Editor EDIT = PREF.edit();
                                    EDIT.putString("CODIGO",null);
                                    EDIT.putString("CLAVE",null);
                                    EDIT.apply();
                                }
                            }
                            else {
                                PROGRESO.dismiss();CONTRA.setText("");CORREO.setText("");
                                Toast.makeText(MainActivity.this, "No se puso conectar satisfactoriamente " +  task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == ENVIAR.getId()){
            AdiosTeclado();
            String U = CORREO.getText().toString().toUpperCase();
            String[] coso = U.split("@");
            U = coso[0];
            String C = CONTRA.getText().toString();
            if (U.length()>=1 && !U.equals(" ")){
                if (C.length()>=1 && !C.equals(" ")){
                    PROGRESO = ProgressDialog.show(this,"¡Cargando!", "Por favor espere...",false,false);
                    DocumentReference DOCREF = db.collection("usuarios").document(U);
                    DOCREF.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (Objects.requireNonNull(document).exists()) {
                                    DATO = Objects.requireNonNull(document.getData()).toString();
                                    SacarValores(DATO);
                                    if (C.equals(CLA)){

                                        SharedPreferences PREF = getSharedPreferences("usuarios.xml",MODE_PRIVATE);
                                        SharedPreferences.Editor EDIT = PREF.edit();
                                        EDIT.putString("CODIGO",COD);
                                        EDIT.putString("CLAVE",CLA);
                                        EDIT.apply();

                                        if (TIP.equals("ADMIN")){
                                            ADMIN.putExtra("CODIGO",COD);
                                            startActivity(ADMIN);
                                        }
                                        else if (TIP.equals("USER") || TIP.equals("DOCEN")){
                                            CLIENTE.putExtra("CODIGO",COD);
                                            startActivity(CLIENTE);
                                        }
                                        PROGRESO.dismiss();
                                    }
                                    else{
                                        PROGRESO.dismiss();CONTRA.setText("");
                                        Toast.makeText(MainActivity.this, "La Contraseña Ingresada no es Correcta", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    PROGRESO.dismiss();CONTRA.setText("");CORREO.setText("");
                                    Toast.makeText(MainActivity.this, "El Usuario Ingresado no se encuentra registrado en la base de datos", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                PROGRESO.dismiss();CONTRA.setText("");CORREO.setText("");
                                Toast.makeText(MainActivity.this, "No se puso conectar satisfactoriamente " +  task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    PROGRESO.dismiss();
                    Toast.makeText(MainActivity.this, "La Contraseña no puede tener solo 1 caracter", Toast.LENGTH_LONG).show();
                }
            }
            else{
                PROGRESO.dismiss();
                Toast.makeText(MainActivity.this, "El Usuario debe ser un Código de Alumno o Administrativo", Toast.LENGTH_LONG).show();
            }
        }
        else if (view.getId() == OLVIDO.getId()){
            startActivity(OLVIDADO);
        }
    }
    private void SacarValores(String dato) {
        dato = dato.replace("{", "");
        dato = dato.replace(" ", "");
        dato = dato.replace("}", "");
        String[] DATOS = dato.split(",");
        for (String s : DATOS) {
            String[] COSO = s.split("=");
            switch (COSO[0]) {
                case "TIPO":
                    TIP = COSO[1]; break;
                case "CODIGO":
                    COD = COSO[1]; break;
                case "CLAVE":
                    CLA = COSO[1]; break;
            }
        }
    }
    private void AdiosTeclado() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            IMM.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}