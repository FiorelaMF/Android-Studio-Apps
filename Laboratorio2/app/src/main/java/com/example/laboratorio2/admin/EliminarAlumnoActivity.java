package com.example.laboratorio2.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laboratorio2.MisClases.Registro;
import com.example.laboratorio2.R;
import com.example.laboratorio2.adaptadores.Alumno;
import com.example.laboratorio2.adaptadores.AlumnoAdapter;
import com.example.laboratorio2.adaptadores.Fiebre;
import com.example.laboratorio2.adaptadores.FiebreAdapter;
import com.example.laboratorio2.internet.JSONUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class EliminarAlumnoActivity extends AppCompatActivity {
    private EditText edtCodigo;
    private Button btnBuscar, btnEliminar;
    private ProgressBar progressBar;
    private String url, url_del;
    private RecyclerView rcvAlumno;

    List<Alumno> alumnos = new ArrayList<>();
    List<Alumno> lista;
    AlumnoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_alumno);

        edtCodigo = findViewById(R.id.edtAdmin4Codigo);
        btnEliminar = findViewById(R.id.btnAdmin4Eliminar);
        btnBuscar = findViewById(R.id.btnAdmin4Buscar);
        progressBar = findViewById(R.id.pgbAdmin4);
        rcvAlumno = findViewById(R.id.rcvAdmin4Alumno);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);

        btnBuscar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                // Obtener seleccion del codigo
                String codigo = edtCodigo.getText().toString();
                String url = "https://fmanco.000webhostapp.com/SOTR/AndroidRWeb/get_admin_eliminar.php?key=12345&codigo="+codigo;
                String url_del = "https://fmanco.000webhostapp.com/SOTR/AndroidRWeb/post_admin_eliminar.php?key=12345&codigo="+codigo;

                //Conecta internet
                try{
                    RequestQueue requestQueue = Volley.newRequestQueue(EliminarAlumnoActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("Respuesta", response);
                                    Log.e("URL",url);
                                    //List<Alumno> alumnos = new ArrayList<>();

                                    if(response.contains("OK")){
                                        //Encontro a un alumno
                                        try{
                                            alumnos = JSONUtils.parseJSONAlumno(response);
                                        } catch (Exception ex){
                                            ex.printStackTrace();
                                        }

                                        //Carga lista solo con los datos seleccionado
                                        for(int p=0; p<alumnos.size();p++){
                                            lista.add(alumnos.get(p));
                                        }

                                        //En Lista ya tengo los datos
                                        adapter = new AlumnoAdapter(EliminarAlumnoActivity.this, lista);
                                        rcvAlumno.setAdapter(adapter);  //muestra los datos enel RecyclerView
                                        adapter.setOnItemClickListener(new AlumnoAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Alumno p) {

                                            }
                                        });

                                        //Mostrar el boton de eliminar
                                        btnEliminar.setVisibility(View.VISIBLE);

                                        //Si presiona el boton ELIMINAR
                                        btnEliminar.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                // Genera el DELETE
                                                progressBar.setVisibility(View.VISIBLE);
                                                    try {
                                                        RequestQueue requestQueue = Volley.newRequestQueue(EliminarAlumnoActivity.this);
                                                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url_del,
                                                                new Response.Listener<String>() {
                                                                    @Override
                                                                    public void onResponse(String response) {
                                                                        Log.e("Resp serv", response);
                                                                        if (response.contains("ELIMINAR") && response.contains("OK")) {
                                                                            progressBar.setVisibility(View.GONE);
                                                                            Toast.makeText(EliminarAlumnoActivity.this, "Se eliminó exitosamente."
                                                                                    , Toast.LENGTH_SHORT).show();
                                                                            Intent intent = new Intent(EliminarAlumnoActivity.this, MenuAdminActivity.class);
                                                                            startActivity(intent);
                                                                            finish();
                                                                        }else if (response.contains("ER")) {
                                                                            progressBar.setVisibility(View.GONE);
                                                                            Toast.makeText(EliminarAlumnoActivity.this, "Algo falló. Revise los datos nuevamente."
                                                                                    , Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    }
                                                                }, new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError error) {
                                                                Log.e("Error", error.getMessage());
                                                                Log.e("Resultado", "fallo update " + error);
                                                            }
                                                        }) {
                                                            @Override
                                                            public String getBodyContentType() {
                                                                return "application/json; charset=utf-8";
                                                            }

                                                        };
                                                        requestQueue.add(stringRequest);
                                                    } catch (Exception ex) {
                                                        ex.printStackTrace();
                                                    }




                                            }
                                        });

                                        /// ERROR AL ENCONTRAR ALUMNO
                                    } else if(response.contains(("ER"))){
                                        Toast.makeText(EliminarAlumnoActivity.this, "Algo falló. Verifique que el codigo se correcto."
                                                , Toast.LENGTH_SHORT).show();
                                    }



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
        });

    }
}