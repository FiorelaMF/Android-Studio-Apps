package app.acsolutions.ventisysupc.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import app.acsolutions.ventisysupc.R;

public class AdminMatricularActivity extends AppCompatActivity implements View.OnClickListener{
    private Button BACK,BUSCAR,CURSO,SECCION,MATRICULAR,RETIRAR ;
    private LinearLayout INFO;
    private EditText CODIGO;
    private ProgressDialog PROGRESO;
    private TextView NOM,APP,CAR;
    private ImageView FOTO;
    private AlertDialog.Builder OPCIONES;
    private CharSequence[] CURS,SECC;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String DATO,CORREO,TIPO,CODIGITO,CLAVESITA,AP,AM,NOMBRESOTE,SEXOTE,CARREROTA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_matricular);
        BACK = findViewById(R.id.back);
        BUSCAR = findViewById(R.id.envio);
        INFO = findViewById(R.id.info);
        CODIGO = findViewById(R.id.codigo);
        NOM = findViewById(R.id.nombre);
        APP = findViewById(R.id.apelidos);
        CAR = findViewById(R.id.carrera);
        FOTO = findViewById(R.id.foto);
        CURSO = findViewById(R.id.curso);
        SECCION = findViewById(R.id.seccion);
        MATRICULAR = findViewById(R.id.matricular);
        RETIRAR = findViewById(R.id.retirar);
        CURS = new CharSequence[]{};
        SECC = new CharSequence[]{};
        OPCIONES = new AlertDialog.Builder(this);
        CURS = new CharSequence[]{};
        SECC = new CharSequence[]{};
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }
        else if (view.getId() == BUSCAR.getId()){
            INFO.setVisibility(View.GONE);
            AdiosTeclado();
            String U = CODIGO.getText().toString().toUpperCase();
            String[] coso = U.split("@");
            U = coso[0];
            if (U.length()>=1 && !U.equals(" ")){
                PROGRESO = ProgressDialog.show(this,"Buscando Información", "Por favor espere...",false,false);
                DocumentReference DOCREF = db.collection("usuarios").document(U);
                DOCREF.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (Objects.requireNonNull(document).exists()) {
                                DATO = Objects.requireNonNull(document.getData()).toString();
                                SacarValores(DATO);
                                NOM.setText("Nombre: " + NOMBRESOTE);
                                APP.setText("Appelidos: " + AP + " " + AM);
                                CAR.setText("Carrera: " + CARREROTA);
                                PROGRESO.dismiss();
                                INFO.setVisibility(View.VISIBLE);
                                CODIGITO = CODIGO.getText().toString().toUpperCase();
                                CODIGO.setText("");
                                StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                                mStorageRef.child("IMAGENES/"+CODIGITO.toUpperCase()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Picasso.get().load(uri).error(R.drawable.lgblanco).placeholder(R.drawable.cargando).into(FOTO);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {

                                    }
                                });



                                // llenar toda la info de curso





                            } else {
                                PROGRESO.dismiss();
                                Toast.makeText(AdminMatricularActivity.this, "El Usuario Ingresado no se encuentra registrado en la base de datos", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            PROGRESO.dismiss();
                            Toast.makeText(AdminMatricularActivity.this, "No se puso conectar satisfactoriamente " +  task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else{
                Toast.makeText(AdminMatricularActivity.this, "El Usuario debe ser un Código de Alumno o Administrativo", Toast.LENGTH_LONG).show();
            }
        }
        else if (view.getId() == CURSO.getId()){
            OPCIONES.setTitle("Seleccionar Un Curso:");
            OPCIONES.setItems(CURS, (dialog, which) -> {
                CURSO.setText(CURS[which].toString());
                // llenar las secciones
                // ver secciones en el servidor...
                SECC = new CharSequence[]{       };
                SECCION.setText("SELECCIONAR ...");
            });OPCIONES.show();
        }
        else if (view.getId() == SECCION.getId()){
            OPCIONES.setTitle("Seleccionar Una Sección:");
            OPCIONES.setItems(SECC, (dialog, which) -> {
                SECCION.setText(SECC[which].toString());
            });OPCIONES.show();
        }
        else if (view.getId() == MATRICULAR.getId()){
            // Poner en la lista de alumnos
        }
        else if (view.getId() == RETIRAR.getId()){
            // eliminar de la lista de alumnos
        }
    }
    private void SacarValores(String dato) {
        dato = dato.replace("{", "");
        dato = dato.replace("}", "");
        String[] DATOS = dato.split(",");
        for (String s : DATOS) {
            Log.e("DATO: ","|"+s+"|");
            String[] COSO = s.split("=");
            switch (COSO[0]) {
                case " CORREO":
                    CORREO = COSO[1]; break;
                case " TIPO":
                    TIPO = COSO[1]; break;
                case " CODIGO":
                    CODIGITO = COSO[1]; break;
                case " CLAVE":
                    CLAVESITA = COSO[1]; break;
                case " AP":
                    AP = COSO[1]; break;
                case " AM":
                    AM = COSO[1]; break;
                case " NOMBRE":
                    NOMBRESOTE = COSO[1]; break;
                case " SEXO":
                    SEXOTE = COSO[1]; break;
                case "CARRERA":
                    CARREROTA = COSO[1]; break;
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