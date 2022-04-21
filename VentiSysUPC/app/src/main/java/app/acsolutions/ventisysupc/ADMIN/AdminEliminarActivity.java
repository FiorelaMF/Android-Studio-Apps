package app.acsolutions.ventisysupc.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import app.acsolutions.ventisysupc.CLIENTE.ClienteDatosActivity;
import app.acsolutions.ventisysupc.R;

public class AdminEliminarActivity extends AppCompatActivity implements View.OnClickListener{
    private Button BACK,BUSCAR,ELIMINAR;
    private EditText CODIGO;
    private TextView NOM,APP,CAR,SEX;
    private LinearLayout INFO;
    private AlertDialog.Builder MENSAJE;
    private ProgressDialog PROGRESO;
    private ImageView FOTO;
    String DATO,CORREO,TIPO,CODIGITO,CLAVESITA,AP,AM,NOMBRESOTE,SEXOTE,CARREROTA;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_eliminar);
        BACK = findViewById(R.id.back);
        CODIGO = findViewById(R.id.codigo);
        BUSCAR = findViewById(R.id.envio);
        ELIMINAR = findViewById(R.id.eliminar);
        INFO = findViewById(R.id.info);
        NOM = findViewById(R.id.nombre);
        APP = findViewById(R.id.apelidos);
        CAR = findViewById(R.id.carrera);
        SEX = findViewById(R.id.sexo);
        FOTO = findViewById(R.id.foto);
        MENSAJE = new AlertDialog.Builder(this);
        MENSAJE.setTitle("MENSAJE");
    }
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
                                SEX.setText("Sexo: " + SEXOTE);
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
                                        FOTO.setBackgroundResource(R.drawable.lgblanco);
                                    }
                                });
                            } else {
                                PROGRESO.dismiss();
                                Toast.makeText(AdminEliminarActivity.this, "El Usuario Ingresado no se encuentra registrado en la base de datos", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            PROGRESO.dismiss();
                            Toast.makeText(AdminEliminarActivity.this, "No se puso conectar satisfactoriamente " +  task.getException(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
            } else{
                PROGRESO.dismiss();
                Toast.makeText(AdminEliminarActivity.this, "El Usuario debe ser un Código de Alumno o Administrativo", Toast.LENGTH_LONG).show();
            }
        }
        else if (view.getId() == ELIMINAR.getId()){
            MENSAJE.setTitle("Eliminacion de Usuario");
            MENSAJE.setMessage("¿Está seguro que quiere realizar esta accion?");
            MENSAJE.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }});
            MENSAJE.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EliminarRegistro();
                }}).show();
        }
    }
    private void EliminarRegistro() {
        PROGRESO = ProgressDialog.show(this,"Eliminando Información", "Por favor espere...",false,false);
        db.collection("usuarios").document(CODIGITO)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        StorageReference REF = mStorageRef.child("IMAGENES/" + CODIGITO + ".jpg");
                        REF.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                PROGRESO.dismiss();
                                Toast.makeText(AdminEliminarActivity.this,"Se Realizó la Eliminación", Toast.LENGTH_LONG).show();
                                onBackPressed();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                PROGRESO.dismiss();
                                Toast.makeText(AdminEliminarActivity.this, "No se pudo eliminar la foto de: " + CODIGITO, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PROGRESO.dismiss();
                        Toast.makeText(AdminEliminarActivity.this,"No se Realizó Eliminación", Toast.LENGTH_LONG).show();
                    }
                });
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