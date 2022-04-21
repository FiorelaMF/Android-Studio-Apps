package com.example.recyclerviewweb.Administrador;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.recyclerviewweb.Adaptadores.Alumno;
import com.example.recyclerviewweb.Adaptadores.AlumnoAdapter;
import com.example.recyclerviewweb.Internet.JSONUtils;
import com.example.recyclerviewweb.MisClases.CambioPerfilActivity;
import com.example.recyclerviewweb.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//TODO 16: Nos conectamos a obtener_alumnos.php y extraemos los datos y mostramos en el RecyclerView
public class MenuAdminActivity extends AppCompatActivity {
    private String url = "sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/obtener_alumnos.php?key=12345";

    RecyclerView rcvLista;
    Spinner spiCiclos;
    TextView txvSemestre;
    List<Alumno> alumnos = new ArrayList<>();
    List<String> ciclos = new ArrayList<>();
    List<Alumno> lista;
    AlumnoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        rcvLista = findViewById(R.id.rcvMenuAdminActivityAlumnos);
        spiCiclos = findViewById(R.id.spiMenuAdminActivityCiclos);
        txvSemestre = findViewById(R.id.txvMenuAdminActivityTitulo);
        spiCiclos.setEnabled(false);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        rcvLista.setLayoutManager(layout);
        SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);
        if (pref.contains("NOMBRE"))
            getSupportActionBar().setTitle("Bienvenido "+pref.getString("NOMBRE",""));

        //Conecta internet
        try{
            RequestQueue requestQueue = Volley.newRequestQueue(MenuAdminActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Respuesta", response);
                            //List<Alumno> alumnos = new ArrayList<>();
                            try{
                                alumnos = JSONUtils.parseJSONAlumno(response);
                                //Log.e("Apellido:",alumnos.get(1).getApellido());
                            } catch (Exception ex){
                                ex.printStackTrace();
                            }
                            try{
                                ciclos = JSONUtils.devolver_ciclo(alumnos);
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

                            //Cargamos el RecyclerView con los datos del alumnos
                            spiCiclos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    //Log.e("Selection",Integer.toString(i));
                                    String ciclo = spiCiclos.getSelectedItem().toString();
                                    lista= new ArrayList<>();

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
                            });
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

    }

}
