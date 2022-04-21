package app.acsolutions.ventisysupc.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import app.acsolutions.ventisysupc.MENUS.MainActivity;
import app.acsolutions.ventisysupc.R;

public class AdminRegistrarActivity extends AppCompatActivity implements View.OnClickListener{
    private Button BACK,REGISTRAR,CARRERA,SEXO,ESTADO;
    private AlertDialog.Builder OPCIONES;
    private ProgressDialog PROGRESO;
    private EditText CODIGO,CORREO,NOMBRE,PATERNO,MATERNO;
    String COR,TIP,COD,CLA,AP,AM,NOM,SEX,CAR,COD_CAR;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    final CharSequence[] CARRERAS = {"Administración","Arquitectura","Biología","Comunicación","Derecho","Diseño","Economía","Gastronomía","Ingeniería Civil","Ingeniería Ambiental","Ingeniería Biomédica","Ingeniería Electrónica","Ingeniería Mecatrónica","Ingeniería de Software","Medicina","Música","Odontología","Psicología","Relaciones Internacionales","Terapia Física","Turismo y Administración"};
    final String[] CODIGOS_CAR = {"ADMI","ARQI","BIOL","COMU","DERE","DISE","ECON","GAST","CIVI","AMBI","BIOM","ELEC","MECA","SOFT","MEDI","MUSI","ODON","PSIC","RELA","TERA","TURI"};
    final CharSequence[] SEXOS = { "MASCULINO","FEMENINO","NO BINARIO"};
    final CharSequence[] ESTADOS =  { "ADMIN","DOCEN","USER"};
    ArrayList<String> NONO = new ArrayList<>();
    ArrayList<String> COCO = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_registrar);
        BACK = findViewById(R.id.back);
        CODIGO = findViewById(R.id.codigo);
        CORREO = findViewById(R.id.correo);
        NOMBRE = findViewById(R.id.nombre);
        PATERNO = findViewById(R.id.ap);
        MATERNO = findViewById(R.id.am);
        OPCIONES = new AlertDialog.Builder(this);
        CARRERA = findViewById(R.id.carrera);
        SEXO = findViewById(R.id.sexo);
        ESTADO = findViewById(R.id.estado);
        REGISTRAR = findViewById(R.id.registrar);
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }
        else if (view.getId() ==  CARRERA.getId()){
            OPCIONES.setTitle("Seleccionar Una Carrera:");
            OPCIONES.setItems(CARRERAS, (dialog, which) -> {
                CARRERA.setText(CARRERAS[which].toString());
                COD_CAR = CODIGOS_CAR[which];
                Log.e("CARRERA: ",COD_CAR);
            });OPCIONES.show();
        }
        else if (view.getId() == SEXO.getId()){
            OPCIONES.setTitle("Seleccionar Un Sexo:");
            OPCIONES.setItems(SEXOS, (dialog, which) -> {
                SEXO.setText(SEXOS[which].toString());
            });OPCIONES.show();
        }
        else if (view.getId() == ESTADO.getId()){
            OPCIONES.setTitle("Seleccionar Un Tipo de Usuario:");
            OPCIONES.setItems(ESTADOS, (dialog, which) -> {
                ESTADO.setText(ESTADOS[which].toString());
            });OPCIONES.show();
        }
        else if (view.getId() == REGISTRAR.getId()){
            AdiosTeclado();
            PROGRESO = ProgressDialog.show(this,"¡Verificando Información!", "Por favor espere...",false,false);
            if (!CODIGO.getText().toString().equals(" ") && CODIGO.getText().toString().length()>=5){
                COD = CODIGO.getText().toString().toUpperCase();
                if(CORREO.getText().toString().contains("@") && CORREO.getText().toString().length()>=10){
                    COR = CORREO.getText().toString().toLowerCase();
                    if (!NOMBRE.getText().toString().equals(" ") && NOMBRE.getText().toString().length()>=4){
                        NOM = NOMBRE.getText().toString().toUpperCase();
                        if (!PATERNO.getText().toString().equals(" ") && PATERNO.getText().toString().length()>=2){
                            AP = PATERNO.getText().toString().toUpperCase();
                            if (!MATERNO.getText().toString().equals(" ") && MATERNO.getText().toString().length()>=2){
                                AM = MATERNO.getText().toString().toUpperCase();
                                if (!CARRERA.getText().toString().equals("SELECCIONAR ...")){
                                    CAR = CARRERA.getText().toString().toUpperCase();
                                    if (!SEXO.getText().toString().equals("SELECCIONAR ...")){
                                        SEX = SEXO.getText().toString().toUpperCase();
                                        if (!ESTADO.getText().toString().equals("SELECCIONAR ...")){
                                            TIP = ESTADO.getText().toString().toUpperCase();
                                            CLA = "123456";
                                            db.collection("usuarios").document(COD)
                                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful()) {
                                                                DocumentSnapshot document = task.getResult();
                                                                if (Objects.requireNonNull(document).exists()) {
                                                                    PROGRESO.dismiss();
                                                                    Toast.makeText(AdminRegistrarActivity.this, "El Usuario Ingresado ya se encuentra registrado en la base de datos", Toast.LENGTH_LONG).show();
                                                                }
                                                                else {
                                                                    Map<String, Object> docData = new HashMap<>();
                                                                    docData.put("AM", AM);
                                                                    docData.put("AP", AP);
                                                                    docData.put("CARRERA", CAR);
                                                                    docData.put("CLAVE", CLA);
                                                                    docData.put("CODIGO", COD);
                                                                    docData.put("CORREO", COR);
                                                                    docData.put("NOMBRE", NOM);
                                                                    docData.put("SEXO", SEX);
                                                                    docData.put("TIPO", TIP);
                                                                    db.collection("usuarios").document(COD)
                                                                            .set(docData)
                                                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                                @Override
                                                                                public void onSuccess(Void aVoid) {
                                                                                    if (TIP.equals("DOCEN")){
                                                                                        AGREGAR_PROFE(COD_CAR);
                                                                                    }
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    PROGRESO.dismiss();
                                                                                    Toast.makeText(AdminRegistrarActivity.this, " No se Registró al usuario: "+ COD, Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }
                                                            } else {
                                                                PROGRESO.dismiss();
                                                                Toast.makeText(AdminRegistrarActivity.this, "No se puso conectar satisfactoriamente " +  task.getException(), Toast.LENGTH_LONG).show();
                                                            }
                                                        }
                                                    });
                                        }
                                        else{
                                            PROGRESO.dismiss();
                                            Toast.makeText(AdminRegistrarActivity.this, "Se debe seleccionar un Estado", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                    else{
                                        PROGRESO.dismiss();
                                        Toast.makeText(AdminRegistrarActivity.this, "Se debe seleccionar un Sexo", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else{
                                    PROGRESO.dismiss();
                                    Toast.makeText(AdminRegistrarActivity.this, "Se debe seleccionar una Carrera", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                PROGRESO.dismiss();
                                Toast.makeText(AdminRegistrarActivity.this, "El Apellido Maternos no puede estar en blanco y debe tener mas de 2 dígitos", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            PROGRESO.dismiss();
                            Toast.makeText(AdminRegistrarActivity.this, "El Apellido Paterno no puede estar en blanco y debe tener mas de 2 dígitos", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        PROGRESO.dismiss();
                        Toast.makeText(AdminRegistrarActivity.this, "El Nombre no puede estar en blanco y debe tener mas de 4 dígitos", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    PROGRESO.dismiss();
                    Toast.makeText(AdminRegistrarActivity.this, "El Correo no puede estar en blanco y debe tener mas de 10 dígitos", Toast.LENGTH_LONG).show();
                }
            }
            else {
                PROGRESO.dismiss();
                Toast.makeText(AdminRegistrarActivity.this, "El Código no puede estar en blanco y debe tener mas de 5 dígitos", Toast.LENGTH_LONG).show();
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
    public void AGREGAR_PROFE(String codigo){
        db.collection("docentes").document(codigo).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (Objects.requireNonNull(document).exists()){
                        String DADA = Objects.requireNonNull(document.getData()).toString();
                        DADA = DADA.replace("{", "");
                        DADA = DADA.replace("}", "");
                        String[] DATOS = DADA.split(",");
                        for (String s : DATOS) {
                            String[] COSO = s.split("=");
                            COCO.add(COSO[0].replace(" ",""));
                            NONO.add(COSO[1]);
                        }
                        COCO.add(COD);
                        NONO.add(NOM + " " + AP + " " + AM);
                        Log.e("COD: ",COCO.toString());
                        Log.e("PRO: ",NONO.toString());
                        db.collection("docentes").document(codigo)
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Map<String, Object> docData = new HashMap<>();
                                        for (int I = 0 ; I<COCO.size() ; I++){
                                            docData.put(COCO.get(I), NONO.get(I));
                                        }
                                        db.collection("docentes").document(codigo)
                                                .set(docData)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        PROGRESO.dismiss();
                                                        Toast.makeText(AdminRegistrarActivity.this, "Se Registró al usuario: " + COD, Toast.LENGTH_SHORT).show();
                                                        onBackPressed();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        PROGRESO.dismiss();
                                                        Toast.makeText(AdminRegistrarActivity.this, " No se Registró al usuario: "+ COD, Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        PROGRESO.dismiss();
                                        Toast.makeText(AdminRegistrarActivity.this, " No se Registró al usuario: "+ COD, Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                }
                else{
                    PROGRESO.dismiss();
                    Toast.makeText(AdminRegistrarActivity.this, "No se puso conectar satisfactoriamente " +  task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}