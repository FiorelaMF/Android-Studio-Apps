package app.acsolutions.ventisysupc.ADMIN;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import app.acsolutions.ventisysupc.LISTAS.Fiebre;
import app.acsolutions.ventisysupc.LISTAS.FiebreAdaptador;
import app.acsolutions.ventisysupc.R;

public class AdminFiebreActivity extends AppCompatActivity implements View.OnClickListener{
    private AlertDialog.Builder OPCION;
    private ProgressDialog PROGRESO;
    private CharSequence[] OPCIONES;
    private TextView TEXTO;
    private Button BACK,CAMPUS;
    RecyclerView LISTA;
    List<Fiebre> casos;
    FiebreAdaptador adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String COMPARADOR,DATO;
    ArrayList<String> AULOTA = new ArrayList<>();
    ArrayList<String> FOTASO = new ArrayList<>();
    ArrayList<String> CORREOTE = new ArrayList<>();
    ArrayList<String> FECHOTA = new ArrayList<>();
    ArrayList<String> NOMBRESOTE = new ArrayList<>();
    ArrayList<String> TEMPERATUROTA = new ArrayList<>();
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_fiebre);
        BACK = findViewById(R.id.back);
        CAMPUS = findViewById(R.id.campus);
        LISTA = findViewById(R.id.lista);
        TEXTO = findViewById(R.id.mensaje);
        OPCION = new AlertDialog.Builder(this);
        OPCIONES = new CharSequence[]{"MONTERRICO","SAN ISIDRO","SAN MIGUEL","VILLA"};

        LISTA.setLayoutManager(new LinearLayoutManager(this));
        casos = new ArrayList<>();
        adapter = new FiebreAdaptador(casos);
        LISTA.setAdapter(adapter);
        COMPARADOR = "NN";
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }
        else if (view.getId() == CAMPUS.getId()){
            TEXTO.setVisibility(View.INVISIBLE);
            LISTA.setVisibility(View.INVISIBLE);
            AULOTA.clear();
            FOTASO.clear();
            CORREOTE.clear();
            FECHOTA.clear();
            NOMBRESOTE.clear();
            TEMPERATUROTA.clear();
            OPCION.setTitle("Seleccionar Campus:");
            OPCION.setItems(OPCIONES, (dialog, which) -> {
                CAMPUS.setText(OPCIONES[which].toString());
                if (CAMPUS.getText().toString().equals("MONTERRICO")){ COMPARADOR = "MO"; }
                else if (CAMPUS.getText().toString().equals("SAN ISIDRO")){ COMPARADOR = "SI"; }
                else if (CAMPUS.getText().toString().equals("SAN MIGUEL")){ COMPARADOR = "SM"; }
                else if (CAMPUS.getText().toString().equals("VILLA")) { COMPARADOR = "VI"; }
                PROGRESO = ProgressDialog.show(this,"¡Cargando Información!", "Por favor espere...",false,false);
                casos.removeAll(casos);
                db.collection("casos").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                                String[] coso = document.getId().split("_");
                                if (COMPARADOR.equals(coso[0])){
                                    DATO = document.getData().toString();
                                    DATO = DATO.replace("{", "");
                                    DATO = DATO.replace("}", "");
                                    Log.e("DATO: ",DATO);
                                    String[] DATOS = DATO.split(",");
                                    for (String s : DATOS) {
                                        Log.e("DATO: ","|"+s+"|");
                                        String[] COSO = s.split("=");
                                        switch (COSO[0]) {
                                            case " AULA":
                                                AULOTA.add(COSO[1]); break;
                                            case " CODIGO":
                                                FOTASO.add(COSO[1]+".jpg"); break;
                                            case " FECHA":
                                                FECHOTA.add(COSO[1]); break;
                                            case " CORREO":
                                                CORREOTE.add(COSO[1]); break;
                                            case "TEMPERATURA":
                                                TEMPERATUROTA.add(COSO[1]); break;
                                            case " NOMBRE":
                                                NOMBRESOTE.add(COSO[1]); break;
                                        }
                                    }
                                }
                            }
                        }
                        else { Toast.makeText(AdminFiebreActivity.this, "Error de Conexión", Toast.LENGTH_LONG).show(); }
                        PROGRESO.dismiss();
                        Log.e("l101: ", AULOTA.toString());
                        Log.e("l102: ", FECHOTA.toString());
                        Log.e("l103: ", NOMBRESOTE.toString());
                        Log.e("l104: ", CORREOTE.toString());
                        Log.e("l105: ", TEMPERATUROTA.toString());
                        Log.e("l106: ", FOTASO.toString());
                        casos.removeAll(casos);
                        // ORDENAR
                        if (AULOTA.size()>0){
                            TEXTO.setVisibility(View.GONE);
                            LISTA.setVisibility(View.VISIBLE);
                            for (int I = 0;I<AULOTA.size();I++){
                                Fiebre CADENA = new Fiebre(AULOTA.get(I),FECHOTA.get(I),NOMBRESOTE.get(I),CORREOTE.get(I),TEMPERATUROTA.get(I),FOTASO.get(I));
                                casos.add(CADENA);
                            }
                            adapter.notifyDataSetChanged();
                        }
                        else{
                            TEXTO.setVisibility(View.VISIBLE);
                            LISTA.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            });OPCION.show();
        }
    }
}