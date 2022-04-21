package app.acsolutions.ventisysupc.ADMIN;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import app.acsolutions.ventisysupc.LISTAS.Asistencias;
import app.acsolutions.ventisysupc.LISTAS.AsistenciasAdaptador;
import app.acsolutions.ventisysupc.LISTAS.MisAsistencias;
import app.acsolutions.ventisysupc.LISTAS.MisAsistenciasAdaptador;
import app.acsolutions.ventisysupc.R;

public class AdminAsistenciasActivity extends AppCompatActivity implements View.OnClickListener{
    private Button BACK,CAMPUS,CARRERA,CURSO,SECCION,FECHA,BUSCAR;
    private ProgressDialog PROGRESO;
    RecyclerView LISTA;
    List<Asistencias> asistencias;
    AsistenciasAdaptador adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_asistencias);
        BACK = findViewById(R.id.back);
        CAMPUS = findViewById(R.id.campus);
        CARRERA = findViewById(R.id.carrera);
        CURSO = findViewById(R.id.curso);
        SECCION = findViewById(R.id.seccion);
        FECHA = findViewById(R.id.fecha);
        BUSCAR = findViewById(R.id.buscar);
        LISTA = findViewById(R.id.lista);
        ArrayList<String> NOMBRE = new ArrayList<>();
        ArrayList<String> TIPOPO = new ArrayList<>();
        ArrayList<String> INGRES = new ArrayList<>();
        ArrayList<String> SALIDA = new ArrayList<>();
        ArrayList<String> FOTOTO = new ArrayList<>();
        ArrayList<String> ESTADO = new ArrayList<>();
        LISTA.setLayoutManager(new LinearLayoutManager(this));
        asistencias = new ArrayList<>();
        adapter = new AsistenciasAdaptador(asistencias);
        LISTA.setAdapter(adapter);
        PROGRESO = ProgressDialog.show(this,"¡Cargando Información!", "Por favor espere...",false,false);
        // calcular TODODOOD
        PROGRESO.dismiss();

        asistencias.removeAll(asistencias);

        for (int I = 0;I<5;I++){
            Asistencias CADENA = new Asistencias(("DOCEN"),("HOLA"+ I),("HOLA"+ I),("HOLA"+ I),("HOLA"+ I),("ASISTIO "+I));
            asistencias.add(CADENA);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }
    }
}