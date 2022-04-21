package com.example.laboratorio2.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laboratorio2.MainActivity;
//import com.example.laboratorio2.MiPerfilActivity;
import com.example.laboratorio2.R;
import com.example.laboratorio2.adaptadores.Ventilacion;
import com.example.laboratorio2.adaptadores.VentilacionAdapter;
import com.example.laboratorio2.internet.JSONUtils;
//import com.example.laboratorio2.Alumno;
//import com.example.laboratorio2.AlumnoAdapter;
//import com.example.laboratorio2.internet.JSONUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MenuVentilacionActivity extends AppCompatActivity {
    private Spinner spiCampus, spiCurso, spiCarrera, spiSeccion;
    private Button btnBuscar;
    private RecyclerView rcvVentilaciones;
    List<Ventilacion> ventilaciones = new ArrayList<>();
    List<Ventilacion> lista;
    VentilacionAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_ventilacion);

        spiCampus = findViewById(R.id.spiAdmin1Campus);
        spiCurso = findViewById(R.id.spiAdmin1Curso);
        spiCarrera = findViewById(R.id.spiAdmin1Carrera);
        spiSeccion = findViewById(R.id.spiAdmin1Seccion);
        btnBuscar = findViewById(R.id.btnAdmin1Buscar);
        rcvVentilaciones = findViewById(R.id.rcvAdmin1Vent);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rcvVentilaciones.setLayoutManager(layout);
        SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener seleccion de los Spinners
                String campus = spiCampus.getSelectedItem().toString();
                String curso = spiCurso.getSelectedItem().toString();
                String carrera = spiCarrera.getSelectedItem().toString();
                String seccion = spiSeccion.getSelectedItem().toString();

                carrera = carrera.replace(" ", "%");
                lista = new ArrayList<>();

                String url = "https://fmanco.000webhostapp.com/SOTR/AndroidRWeb/get_admin_ventilacion.php?key=12345&campus="+campus+"&curso="+curso+"&seccion="+seccion+"&carrera="+carrera;

                //Conecta internet
                try{
                    RequestQueue requestQueue = Volley.newRequestQueue(MenuVentilacionActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("Respuesta", response);
                                    Log.e("URL",url);
                                    //List<Alumno> alumnos = new ArrayList<>();

                                    if(response.contains("OK")){
                                        try{
                                            ventilaciones = JSONUtils.parseJSONVentilacion(response);
                                        } catch (Exception ex){
                                            ex.printStackTrace();
                                        }

                                        //Carga lista solo con los datos seleccionado
                                        for(int p=0; p<ventilaciones.size();p++){
                                            lista.add(ventilaciones.get(p));
                                        }

                                        //En Lista ya tengo los datos
                                        adapter = new VentilacionAdapter(MenuVentilacionActivity.this,lista);
                                        rcvVentilaciones.setAdapter(adapter);  //muestra los datos enel RecyclerView
                                        adapter.setOnItemClickListener(new VentilacionAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Ventilacion p) {

                                            }
                                        });

                                    } else if(response.contains(("ER"))){
                                        Toast.makeText(MenuVentilacionActivity.this, "Algo falló. Verifique los datos colocados."
                                                , Toast.LENGTH_SHORT).show();
                                    }



                                    /*
                                    try{
                                        ventilaciones = JSONUtils.devolver_ventilacion(ventilaciones);
                                    } catch (Exception ex){
                                        ex.printStackTrace();
                                    }

                                    for (int i=0; i<ciclos.size(); i++){
                                        Log.e("Ciclo "+i,ciclos.get(i));
                                    }

                                    //Cargamos el Spinner con los ciclos actuales del JSON
                                    spiCiclos.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                            android.R.layout.simple_spinner_dropdown_item,ciclos));
                                    spiCiclos.setEnabled(true);

                                    //Cargamos el RecyclerView con los datos del Ventilacion
                                    spiCampus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                            //Log.e("Selection",Integer.toString(i));
                                            String campus = spiCampus.getSelectedItem().toString();
                                            lista = new ArrayList<>();

                                            //Carga lista solo con los alumnos del ciclo seleccionado
                                            for(int p=0; p<alumnos.size();p++){
                                                if (alumnos.get(p).getCiclo().equals(ciclo))
                                                    lista.add(alumnos.get(p));
                                            }

                                            //En Lista ya tengo los alumnos del ciclo seleccionado
                                            adapter = new AlumnoAdapter(MenuAdminActivity.this,lista);
                                            rcvLista.setAdapter(adapter);  //muestra los datos enel RecyclerView
                                            adapter.setOnItemClickListener(new AlumnoAdapter.OnItemClickListener() {
                                                @Override
                                                public void onItemClick(Alumno p) {
                                                    Intent intent = new Intent(MenuAdminActivity.this, CambioPerfilActivity.class);
                                                    //String k = "4";
                                                    intent.putExtra("ALUMNO",(Serializable) p);
                                                    startActivity(intent);
                                                }
                                            });
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> parent) {

                                        }
                                    });*/

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error","Fallo del servidor");
                        }
                    }){
                        @Override
                        public String getBodyContentType(){
                            return "application/json; charset=utf-8";
                        }
                        @Override
                        public byte[] getBody() throws AuthFailureError {
                            return null;
                        }
                    };
                    requestQueue.add(stringRequest);
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }});
    }
}