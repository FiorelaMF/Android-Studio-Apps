package app.acsolutions.ventisysupc.CLIENTE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import app.acsolutions.ventisysupc.ADMIN.AdminRegistrarActivity;
import app.acsolutions.ventisysupc.MENUS.MainActivity;
import app.acsolutions.ventisysupc.R;

public class ClienteDatosActivity extends AppCompatActivity implements View.OnClickListener{
    private AlertDialog.Builder OPCIONES, MENSAJES;
    private CharSequence[] CARRERAS,SEXOS;
    private ImageView FOTO;
    private EditText CODIGO, NOMBRE,PATERNO,MATERNO;
    private Button CARRERA,SEXO,BACK,SAVE;
    private ProgressDialog PROGRESO;

    private static final int GALLERY = 2;
    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
    String DATO,COD,AP,AM,NOM,SEX,CAR,FOTO_GALERIA,RUTA;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_datos);
        CODIGO = findViewById(R.id.codigo);
        NOMBRE = findViewById(R.id.nombre);
        PATERNO = findViewById(R.id.ap_pa);
        MATERNO = findViewById(R.id.ap_ma);
        CARRERA = findViewById(R.id.carrera);
        SEXO = findViewById(R.id.sexo);
        BACK = findViewById(R.id.back);
        SAVE = findViewById(R.id.guardar);
        FOTO = findViewById(R.id.foto);
        OPCIONES = new AlertDialog.Builder(this);
        MENSAJES = new AlertDialog.Builder(this);
        CARRERAS = new CharSequence[]{"Administración","Arquitectura","Biología","Comunicación","Derecho","Diseño","Economía","Gastronomía","Ingeniería Civil","Ingeniería Ambiental","Ingeniería Biomédica","Ingeniería Electrónica","Ingeniería Mecatrónica","Ingeniería de Software","Medicina","Música","Odontología","Psicología","Relaciones Internacionales","Terapia Física","Turismo y Administración"};
        SEXOS = new CharSequence[]{ "MASCULINO","FEMENINO","NO BINARIO"};
        Bundle EXTRAS = getIntent().getExtras();
        if (EXTRAS == null){ COD = "NO"; }
        else{
            COD = EXTRAS.getString("CODIGO");
            PROGRESO = ProgressDialog.show(this,"¡Cargando!", "Por favor espere...",false,false);
            db.collection("usuarios").document(COD)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (Objects.requireNonNull(document).exists()) {
                                    DATO = Objects.requireNonNull(document.getData()).toString();
                                    SacarValores(DATO);
                                    CODIGO.setText(COD);
                                    NOMBRE.setText(NOM);
                                    PATERNO.setText(AP);
                                    MATERNO.setText(AM);
                                    CARRERA.setText(CAR);
                                    SEXO.setText(SEX);
                                    StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
                                    mStorageRef.child("IMAGENES/"+COD.toUpperCase()+".jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Picasso.get().load(uri).error(R.drawable.lgblanco).placeholder(R.drawable.cargando).into(FOTO);
                                            PROGRESO.dismiss();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            FOTO.setBackgroundResource(R.drawable.lgblanco);
                                            PROGRESO.dismiss();
                                        }
                                    });
                                }
                                else {
                                    PROGRESO.dismiss();
                                    Toast.makeText(ClienteDatosActivity.this, "El Usuario Ingresado no se encuentra registrado en la base de datos", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                PROGRESO.dismiss();
                                Toast.makeText(ClienteDatosActivity.this, "No se puso conectar satisfactoriamente " +  task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }
        else if (view.getId()  == CODIGO.getId()){
            Toast.makeText(this, "No se puede Cambiar el Código debido a que es unico", Toast.LENGTH_LONG).show();
        }
        else if (view.getId()  == CARRERA.getId()){
            OPCIONES.setTitle("Seleccionar Una Carrera:");
            OPCIONES.setItems(CARRERAS, (dialog, which) -> {
                CARRERA.setText(CARRERAS[which].toString());
            });OPCIONES.show();
        }
        else if (view.getId() == SEXO.getId()){
            OPCIONES.setTitle("Seleccionar Un Sexo:");
            OPCIONES.setItems(SEXOS, (dialog, which) -> {
                SEXO.setText(SEXOS[which].toString());
            });OPCIONES.show();
        }
        else if (view.getId() == SAVE.getId()){
            AdiosTeclado();
            PROGRESO = ProgressDialog.show(this,"¡Verificando Información!", "Por favor espere...",false,false);
            if (!CODIGO.getText().toString().equals(" ") && CODIGO.getText().toString().length()>=5){
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
                                    Map<String, Object> docData = new HashMap<>();
                                    docData.put("AM", AM);
                                    docData.put("AP", AP);
                                    docData.put("CARRERA", CAR);
                                    docData.put("NOMBRE", NOM);
                                    docData.put("SEXO", SEX);
                                    db.collection("usuarios").document(COD)
                                            .update(docData)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    PROGRESO.dismiss();
                                                    Toast.makeText(ClienteDatosActivity.this, "Se Actualizo al usuario: " + COD, Toast.LENGTH_LONG).show();
                                                    onBackPressed();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    PROGRESO.dismiss();
                                                    Toast.makeText(ClienteDatosActivity.this, "No se Actualizo al usuario: "+ COD, Toast.LENGTH_LONG).show();
                                                }
                                            });
                                }
                                else{
                                    PROGRESO.dismiss();
                                    Toast.makeText(ClienteDatosActivity.this, "Se debe seleccionar un Sexo", Toast.LENGTH_LONG).show();
                                }
                            }
                            else{
                                PROGRESO.dismiss();
                                Toast.makeText(ClienteDatosActivity.this, "Se debe seleccionar una Carrera", Toast.LENGTH_LONG).show();
                            }
                        }
                        else{
                            PROGRESO.dismiss();
                            Toast.makeText(ClienteDatosActivity.this, "El Apellido Maternos no puede estar en blanco y debe tener mas de 2 dígitos", Toast.LENGTH_LONG).show();
                        }
                    }
                    else{
                        PROGRESO.dismiss();
                        Toast.makeText(ClienteDatosActivity.this, "El Apellido Paterno no puede estar en blanco y debe tener mas de 2 dígitos", Toast.LENGTH_LONG).show();
                    }
                }
                else{
                    PROGRESO.dismiss();
                    Toast.makeText(ClienteDatosActivity.this, "El Nombre no puede estar en blanco y debe tener mas de 4 dígitos", Toast.LENGTH_LONG).show();
                }
            }
            else {
                PROGRESO.dismiss();
                Toast.makeText(ClienteDatosActivity.this, "El Código no puede estar en blanco y debe tener mas de 5 dígitos", Toast.LENGTH_LONG).show();
            }
        }
        else if (view.getId() == FOTO.getId()){
            MENSAJES.setTitle("Subir Imagen");
            MENSAJES.setMessage("¿Desea Tomar foto o buscar en Galeria?");
            MENSAJES.setNegativeButton("Tomar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    AbrirCamara();
                }});
            MENSAJES.setPositiveButton("Buscar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SubirGaleria();
                }}).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){
            Metodo(Uri.fromFile(new File(RUTA)));
        }
        else if (requestCode == GALLERY && resultCode == RESULT_OK && data != null){
            Metodo(data.getData());
        }
        else {
            Toast.makeText(this, "NO PASO NADA" + resultCode + " " + requestCode, Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("QueryPermissionsNeeded")
    private void AbrirCamara() {
        try {
            String TIME = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            Intent TOMAR_FOTO = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File DIR = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            File IMG = new File(DIR + File.separator + TIME + ".jpg");
            RUTA = IMG.getAbsolutePath();
            Uri URI = FileProvider.getUriForFile(this,"app.acsolutions.ventisysupc.fileprovider", IMG);
            TOMAR_FOTO.putExtra(MediaStore.EXTRA_OUTPUT,URI);
            startActivityForResult(TOMAR_FOTO,1);
        }
        catch (Exception e) { Log.e("Error: ",e.toString());}
    }
    private void SubirGaleria() {
        Intent GALERIA = new Intent(Intent.ACTION_PICK);
        GALERIA.setType("image/*");
        startActivityForResult(GALERIA,GALLERY);
    }
    private void AdiosTeclado() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            IMM.hideSoftInputFromWindow(view.getWindowToken(),0);
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
                case " AP":
                    AP = COSO[1]; break;
                case " AM":
                    AM = COSO[1]; break;
                case " NOMBRE":
                    NOM = COSO[1]; break;
                case " SEXO":
                    SEX = COSO[1]; break;
                case "CARRERA":
                    CAR = COSO[1]; break;
            }
        }
    }
    private void Metodo(Uri URISITO){
        PROGRESO = ProgressDialog.show(this,"¡Cargando!", "Por favor espere...",false,false);
        FOTO_GALERIA = COD + ".jpg";
        StorageReference REF = mStorageRef.child("IMAGENES/" + FOTO_GALERIA);
        REF.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                REF.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        REF.putFile(URISITO).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                PROGRESO.dismiss();
                                Picasso.get().load(URISITO).error(R.drawable.lgblanco).placeholder(R.drawable.cargando).into(FOTO);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                PROGRESO.dismiss();
                                Toast.makeText(ClienteDatosActivity.this, "No se pudo subir la foto de: " + COD, Toast.LENGTH_LONG).show();
                            }});
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        PROGRESO.dismiss();
                        Toast.makeText(ClienteDatosActivity.this, "No se pudo eliminar la foto de: " + COD, Toast.LENGTH_LONG).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                REF.putFile(URISITO).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        PROGRESO.dismiss();
                        Picasso.get().load(URISITO).error(R.drawable.lgblanco).placeholder(R.drawable.cargando).into(FOTO);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        PROGRESO.dismiss();
                        Toast.makeText(ClienteDatosActivity.this, "No se pudo subir la foto de: " + COD, Toast.LENGTH_LONG).show();
                    }});
            }
        });
    }
}