package app.acsolutions.ventisysupc.MENUS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import app.acsolutions.ventisysupc.ADMIN.AdminEliminarActivity;
import app.acsolutions.ventisysupc.ADMIN.AdminVentilacionActivity;
import app.acsolutions.ventisysupc.ADMIN.AdminProgramarActivity;
import app.acsolutions.ventisysupc.ADMIN.AdminFiebreActivity;
import app.acsolutions.ventisysupc.COMUNI.ContraActivity;
import app.acsolutions.ventisysupc.ADMIN.AdminMatricularActivity;
import app.acsolutions.ventisysupc.R;
import app.acsolutions.ventisysupc.ADMIN.AdminRegistrarActivity;
import app.acsolutions.ventisysupc.ADMIN.AdminAsistenciasActivity;

public class MenuAdminActivity extends AppCompatActivity implements View.OnClickListener{
    private Button MENU,ELIMI,MATRIC,PROGRA,REGIS,ASIST,FIEBR,VENTIL;
    private AlertDialog.Builder OPCION;
    private CharSequence[] OPCIONES;
    private Intent CONTRA,ELIMININAR,MATRICULA,VENTILACION,CASOSFIEBRE,REGISTRAR,ASISTENCIAS,PROGRAMAR;
    private ImageView FOTO;
    private TextView NOMB,CODIG;
    private ProgressDialog PROGRESO;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String DATO,COD,CLA,AP,AM,NOM;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        MENU = findViewById(R.id.menu);
        ELIMI = findViewById(R.id.eliminacion);
        MATRIC = findViewById(R.id.matricular);
        PROGRA = findViewById(R.id.curso);
        REGIS = findViewById(R.id.registro);
        ASIST = findViewById(R.id.asistencia);
        FIEBR = findViewById(R.id.fibre);
        VENTIL = findViewById(R.id.venti);
        NOMB = findViewById(R.id.nombre);
        CODIG = findViewById(R.id.codigo);
        FOTO = findViewById(R.id.foto);
        CONTRA = new Intent(this, ContraActivity.class);
        ELIMININAR = new Intent(this, AdminEliminarActivity.class);
        MATRICULA = new Intent(this, AdminMatricularActivity.class);
        PROGRAMAR = new Intent(this, AdminProgramarActivity.class);
        REGISTRAR = new Intent(this, AdminRegistrarActivity.class);
        ASISTENCIAS = new Intent(this, AdminAsistenciasActivity.class);
        CASOSFIEBRE = new Intent(this, AdminFiebreActivity.class);
        VENTILACION = new Intent(this, AdminVentilacionActivity.class);

        OPCION = new AlertDialog.Builder(this);
        OPCION.setTitle("Seleccionar Opción:");
        OPCIONES = new CharSequence[]{"Cambiar Contraseña","Cerrar Sesión","Salir"};

        Bundle EXTRAS = getIntent().getExtras();
        if (EXTRAS == null){
            COD = "NO";
        }
        else{
            COD = EXTRAS.getString("CODIGO");
            PROGRESO = ProgressDialog.show(this,"Buscando Información", "Por favor espere...",false,false);
            COD = EXTRAS.getString("CODIGO");
            db.collection("usuarios").document(COD)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (Objects.requireNonNull(document).exists()) {
                            DATO = Objects.requireNonNull(document.getData()).toString();
                            SacarValores(DATO);
                            NOMB.setText(NOM + " " + AP + " " + AM);
                            CODIG.setText(COD);
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
                            Toast.makeText(MenuAdminActivity.this, "No existe información", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        PROGRESO.dismiss();
                        Toast.makeText(MenuAdminActivity.this, "No se puso conectar" +  task.getException(), Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == MENU.getId()){
            OPCION.setItems(OPCIONES, (dialog, which) -> {
                if(OPCIONES[which].toString().equals("Cambiar Contraseña")){
                    CONTRA.putExtra("CODIGO",COD);
                    CONTRA.putExtra("CLAVE",CLA);
                    startActivity(CONTRA);
                }
                else if(OPCIONES[which].toString().equals("Cerrar Sesión")){
                    SharedPreferences PREF = getSharedPreferences("usuarios.xml",MODE_PRIVATE);
                    SharedPreferences.Editor EDIT = PREF.edit();
                    EDIT.putString("CODIGO",null);
                    EDIT.putString("CLAVE",null);
                    EDIT.apply();
                    finish();
                }
                else if(OPCIONES[which].toString().equals("Salir")){
                    finishAffinity();
                }
            });OPCION.show();
        }
        else if (view.getId() == ELIMI.getId()){
            startActivity(ELIMININAR);
        }
        else if (view.getId() == MATRIC.getId()){
            startActivity(MATRICULA);
        }
        else if (view.getId() == PROGRA.getId()){
            startActivity(PROGRAMAR);
        }
        else if (view.getId() == REGIS.getId()){
            startActivity(REGISTRAR);
        }
        else if (view.getId() == ASIST.getId()){
            startActivity(ASISTENCIAS);
        }
        else if (view.getId() == FIEBR.getId()){
            startActivity(CASOSFIEBRE);
        }
        else if (view.getId() == VENTIL.getId()){
            startActivity(VENTILACION);
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
                case "CODIGO":
                    COD = COSO[1]; break;
                case "CLAVE":
                    CLA = COSO[1]; break;
                case "AP":
                    AP = COSO[1]; break;
                case "AM":
                    AM = COSO[1]; break;
                case "NOMBRE":
                    NOM = COSO[1]; break;
            }
        }
    }
}