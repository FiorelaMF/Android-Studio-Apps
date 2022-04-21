package com.example.pc2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.pc2.Adaptadores.CosaUnoAdapter;
import com.example.pc2.MisClases.CosaUno;
import com.example.pc2.MisClases.CosaUnoConvert;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcvmain;
    Spinner spnSeccion;
    List<CosaUno> ListaCartas= new ArrayList<>();
    List<String> secciones= new ArrayList<>();
    List<CosaUno> ListaCartasElegidas= new ArrayList<>();
    CosaUnoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        spnSeccion=findViewById(R.id.spnmain);
        rcvmain=findViewById(R.id.rcvmain);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rcvmain.setLayoutManager(layout);

//COMIENZA LA CONECCION
        String url="https://sergiosalasarriaran.000webhostapp.com/sotrupc/2021-2/practica2/getnotas.php";
        try {
            RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);

//            final String requestBody ="codigo="+codigo+"&key=123456";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //////////////////////////SE RECIBE LA RESPUESTAAA
                    Log.e(">>>",response);
                    try {
                        ListaCartas= CosaUnoConvert.StringToArray(response);
                        secciones=CosaUnoConvert.devolver_seccion(ListaCartas);

                        spnSeccion.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_spinner_dropdown_item,secciones));

                        spnSeccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                String seccion=spnSeccion.getSelectedItem().toString();
                                ListaCartasElegidas= new ArrayList<>();

                                for(int p=0;p<ListaCartas.size();p++) {
                                    if (ListaCartas.get(p).getSeccion().equals(seccion))
                                        ListaCartasElegidas.add(ListaCartas.get(p));
                                }
                                adapter =new CosaUnoAdapter(MainActivity.this,ListaCartasElegidas);
                                rcvmain.setAdapter(adapter);
                                adapter.setOnItemClickListener(new CosaUnoAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(CosaUno p) {
                                        Intent intent = new Intent(MainActivity.this,SegundaActivity.class);
                                        intent.putExtra("TABLA",(Serializable)p);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    /////////////////////////
                }
            },new Response.ErrorListener(){
                @Override
                public void onErrorResponse(VolleyError error){
                    Log.e("Error","Fallo del Servidor");
                }
            }){
                @Override
                public String getBodyContentType(){
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    //DESCOMENTAR PARA METODO POST
//                    try{
//                        return requestBody==null? null:requestBody.getBytes("utf-8");
//                    }catch (UnsupportedEncodingException uee){
//                        VolleyLog.wtf("Codificcacion no soportada");
//                        return null;
//                    }
                    return null;
                }
            };
            requestQueue.add(stringRequest);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //ACABA LA CONECCION




    }
}