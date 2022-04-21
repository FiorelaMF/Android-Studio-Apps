package app.acsolutions.ventisysupc.ADMIN;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import app.acsolutions.ventisysupc.LISTAS.Asistencias;
import app.acsolutions.ventisysupc.LISTAS.AsistenciasAdaptador;
import app.acsolutions.ventisysupc.LISTAS.Ventilacion;
import app.acsolutions.ventisysupc.LISTAS.VentilacionAdaptador;
import app.acsolutions.ventisysupc.R;

public class AdminVentilacionActivity extends AppCompatActivity implements View.OnClickListener{

    public Button CAMPUS,CURSO,CARRERA,SECCION,BUSCAR,BACK;
    public CharSequence[] CAMP,CURS,CARR,SECC;
    public AlertDialog.Builder OPCIONES;


    ArrayList<String> NOMBRE = new ArrayList<>();
    ArrayList<String> FECHAS = new ArrayList<>();
    ArrayList<String> ALUMNS = new ArrayList<>();

    RecyclerView LISTA;
    List<Ventilacion> ventilacion;
    VentilacionAdaptador adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ventilacion);
        BACK = findViewById(R.id.back);
        LISTA = findViewById(R.id.lista);
        CAMPUS = findViewById(R.id.campus);
        CARRERA = findViewById(R.id.carrera);
        CURSO = findViewById(R.id.curso);
        SECCION = findViewById(R.id.seccion);
        BUSCAR = findViewById(R.id.buscar);
        OPCIONES = new AlertDialog.Builder(this);
        CAMP = new CharSequence[]{ "Monterrico","San Isidro","San Miguel","Villa"};


        LISTA.setLayoutManager(new LinearLayoutManager(this));
        ventilacion = new ArrayList<>();
        adapter = new VentilacionAdaptador(ventilacion);
        LISTA.setAdapter(adapter);


        ventilacion.removeAll(ventilacion);

        for (int I = 0;I<5;I++){
            Ventilacion CADENA = new Ventilacion(("DOCEN"),("HOLA"+ I),("HOLA"+ I));
            ventilacion.add(CADENA);
        }
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }

        else if(view.getId() == CAMPUS.getId()){

        }
    }
}