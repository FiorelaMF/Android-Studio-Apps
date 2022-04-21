package com.example.finalito;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalito.Adaptadores.AlumnoAdapter;
import com.example.finalito.MisClases.Alumno;
import com.example.finalito.MisClases.AlumnoConvert;

import org.json.JSONException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rcvmain;
    List<Alumno> ListaAlumnos= new ArrayList<>();
    AlumnoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //INSERTAR IMAGEN EN EL MENU(APPBAR)
        getSupportActionBar().hide();

        rcvmain=findViewById(R.id.rcvmain);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rcvmain.setLayoutManager(layout);


//COMIENZA LA CONECCION
        String url="https://fmanco.000webhostapp.com/SOTR/ver_menus.php?key=12345";
        try {
            RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);

//            final String requestBody ="codigo="+codigo+"&key=123456";
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //////////////////////////SE RECIBE LA RESPUESTAAA
                    Log.e(">>>",response);

                    try {
                        ListaAlumnos= AlumnoConvert.StringToArray(response);
                        adapter =new AlumnoAdapter(MainActivity.this,ListaAlumnos);
                        rcvmain.setAdapter(adapter);

                        adapter.setOnItemClickListener(new AlumnoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Alumno p) {
                                Intent intent = new Intent(MainActivity.this,SegundaActivity.class);
                                intent.putExtra("MENU",(Serializable)p);
                                startActivity(intent);
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