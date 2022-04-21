package com.example.laboratorio2.cliente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.laboratorio2.R;
import com.example.laboratorio2.adaptadores.Asistencia;
import com.example.laboratorio2.adaptadores.Ventilacion;
import com.example.laboratorio2.adaptadores.VentilacionAdapter;
import com.example.laboratorio2.admin.MenuVentilacionActivity;
import com.example.laboratorio2.internet.JSONUtils;

import java.util.ArrayList;
import java.util.List;

public class MisAsistenciasActivity extends AppCompatActivity {
    private TextView nAsistencias, nTardanzas, nFaltas;
    private RecyclerView rcvMisAsist;
    List<Asistencia> asistencias = new ArrayList<>();
    List<Asistencia> lista;
    VentilacionAdapter adapter;
    private String codigo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_asistencias);

        nAsistencias = findViewById(R.id.txvAsistencias);
        nTardanzas = findViewById(R.id.txvTardanzas);
        nFaltas = findViewById(R.id.txvFaltas);

        SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);
        Intent intent= getIntent();
        codigo = intent.getStringExtra("USUARIO");
        Log.e("User",codigo);

        lista = new ArrayList<>();

        String url = "https://fmanco.000webhostapp.com/SOTR/AndroidRWeb/get_cliente_misasistencias.php?key=12345&codigo="+codigo;

        //Conecta internet
        try{
            RequestQueue requestQueue = Volley.newRequestQueue(MisAsistenciasActivity.this);

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Respuesta", response);
                            Log.e("URL",url);
                            /*

                            if(response.contains("OK")){
                                try{
                                    asistencias = JSONUtils.parseJSONAsist(response);
                                } catch (Exception ex){
                                    ex.printStackTrace();
                                }

                                //Carga lista solo con los datos seleccionado
                                for(int p=0; p<asistencias.size();p++){
                                    lista.add(asistencias.get(p));
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
                            } */

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