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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

import app.acsolutions.ventisysupc.CLIENTE.ClienteVentilacionActivity;
import app.acsolutions.ventisysupc.CLIENTE.ClienteAsistenciasActivity;
import app.acsolutions.ventisysupc.CLIENTE.ClienteDatosActivity;
import app.acsolutions.ventisysupc.CLIENTE.ClienteDocenteActivity;
import app.acsolutions.ventisysupc.COMUNI.ContraActivity;
import app.acsolutions.ventisysupc.R;

public class MenuClienteActivity extends AppCompatActivity implements View.OnClickListener{
    private Button MENU,ASIST,VENTI,DATOS,DOCEN;
    private AlertDialog.Builder OPCION;
    private CharSequence[] OPCIONES;
    private Intent CONTRA,DATOTES,ASISTEN,VENTIL,DOCENTES;
    private ImageView FOTO;
    private TextView NOMB,CODIG;
    private ProgressDialog PROGRESO;

    String DATO,TIP,COD,CLA,AP,AM,NOM;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);
        FOTO = findViewById(R.id.foto);
        MENU = findViewById(R.id.menu);
        ASIST = findViewById(R.id.asistencias);
        VENTI = findViewById(R.id.ventilacion);
        DATOS = findViewById(R.id.datosotos);
        DOCEN = findViewById(R.id.docentes);
        NOMB = findViewById(R.id.nombre);
        CODIG = findViewById(R.id.codigo);
        CONTRA = new Intent(this, ContraActivity.class);
        DATOTES = new Intent(this, ClienteDatosActivity.class);
        VENTIL = new Intent(this, ClienteVentilacionActivity.class);
        ASISTEN = new Intent(this, ClienteAsistenciasActivity.class);
        DOCENTES = new Intent(this, ClienteDocenteActivity.class);
        OPCION = new AlertDialog.Builder(this);
        OPCION.setTitle("Seleccionar Opción:");
        OPCIONES = new CharSequence[]{"Cambiar Contraseña","Cerrar Sesión","Salir"};
        Bundle EXTRAS = getIntent().getExtras();
        if (EXTRAS == null){ COD = "NO"; }
        else{
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
                                    if (TIP.equals("DOCEN")){ DOCEN.setVisibility(View.VISIBLE); }
                                    else{ DOCEN.setVisibility(View.INVISIBLE); }
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
                                    Toast.makeText(MenuClienteActivity.this, "No existe información", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                PROGRESO.dismiss();
                                Toast.makeText(MenuClienteActivity.this, "No se puso conectar" +  task.getException(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == MENU.getId()){
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
        }
        else if (view.getId() == ASIST.getId()){
            ASISTEN.putExtra("CODIGO",COD);
            startActivity(ASISTEN);
        }
        else if (view.getId() == VENTI.getId()){
            VENTIL.putExtra("CODIGO",COD);
            startActivity(VENTIL);
        }
        else if (view.getId() == DATOS.getId()){
            DATOTES.putExtra("CODIGO",COD);
            startActivity(DATOTES);
        }
        else if (view.getId() == DOCEN.getId()){
            DOCENTES.putExtra("CODIGO",COD);
            startActivity(DOCENTES);
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