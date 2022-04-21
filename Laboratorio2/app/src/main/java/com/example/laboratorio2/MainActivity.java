package com.example.laboratorio2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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
import com.example.laboratorio2.admin.MenuAdminActivity;
import com.example.laboratorio2.cliente.MenuClienteActivity;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private EditText edt_usuario, edt_clave;
    private Button btn_ingresar;
    private ProgressBar progressBar;
    private TextView txv_olvido;

    private String url = "https://fmanco.000webhostapp.com/SOTR/AndroidRWeb/loguin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_clave = findViewById(R.id.edtMainActivityClave);
        edt_usuario = findViewById(R.id.edtMainActivityUsuario);
        btn_ingresar = findViewById(R.id.btnMainActivityIngresar);
        progressBar = findViewById(R.id.pgbMainActivityEspera);
        txv_olvido = findViewById(R.id.txvMainActivityOlvido);

        /*Vamos a esconder el AppBar y anular el giro del teléfono*/
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Al pulsar el TextVIew de olvido de contraseña
        txv_olvido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,OlvidoActivity.class);
                startActivity(intent);
            }
        });

        //Al pulsar el Botón de Login nos vamos al servicio Web POST de login.
        btn_ingresar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progressBar.setVisibility(View.VISIBLE);
                try{
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    String usuario = edt_usuario.getText().toString();
                    String pass = edt_clave.getText().toString();
                    final String requestBody = "correo="+usuario+"&clave="+pass+"&key=12345";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("Resp serv", response);
                                    if (response.contains("OK") && response.contains("\"CARGO\":\"Admin\"")) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(MainActivity.this, "Clave correcta. Bienvenido"
                                                , Toast.LENGTH_SHORT).show();
                                        SharedPreferences pref = getSharedPreferences("Usuario.xml", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("USUARIO", edt_usuario.getText().toString());
                                        editor.putString("CARGO", "Admin");
                                        //Obtenemos el nombre del usuario que se ha logueado
                                        //Para eso buscamos el índice de "NOMBRE": en el JSON
                                        int n = response.indexOf("\"NOMBRE\":");
                                        String nombre = response.substring(n+10);
                                        int nf = nombre.indexOf("\"");
                                        nombre = nombre.substring(0,nf);
                                        Log.e("Nombre",nombre);
                                        editor.putString("NOMBRE",nombre);
                                        editor.commit();

                                        Intent intent = new Intent(MainActivity.this, MenuAdminActivity.class);
                                        intent.putExtra("USUARIO", edt_usuario.getText().toString());
                                        startActivity(intent);
                                        finish();
                                    } else if (response.contains("OK") && (response.contains("\"CARGO\":\"Alumno\""))) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(MainActivity.this, "Clave correcta. Bienvenido"
                                                , Toast.LENGTH_SHORT).show();
                                        SharedPreferences pref = getSharedPreferences("Usuario.xml", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("USUARIO", edt_usuario.getText().toString());
                                        editor.putString("Cargo", "Alumno");
                                        //Obtenemos el nombre del usuario que se ha logueado
                                        //Para eso buscamos el índice de "NOMBRE": en el JSON
                                        int n = response.indexOf("\"NOMBRE\":");
                                        String nombre = response.substring(n+10);
                                        int nf = nombre.indexOf("\"");
                                        nombre = nombre.substring(0,nf);
                                        Log.e("Nombre",nombre);
                                        editor.putString("NOMBRE",nombre);

                                        editor.commit();
                                        Intent intent = new Intent(MainActivity.this, MenuClienteActivity.class);
                                        intent.putExtra("USUARIO", edt_usuario.getText().toString());
                                        intent.putExtra("ESTADO", "ALUMNO");
                                        startActivity(intent);
                                        finish();
                                    } else if (response.contains("\"CARGO\":\"Docente\"")) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(MainActivity.this, "Clave correcta. Bienvenido"
                                                , Toast.LENGTH_SHORT).show();
                                        SharedPreferences pref = getSharedPreferences("Usuario.xml", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("USUARIO", edt_usuario.getText().toString());
                                        editor.putString("Cargo", "Alumno");
                                        //Obtenemos el nombre del usuario que se ha logueado
                                        //Para eso buscamos el índice de "NOMBRE": en el JSON
                                        int n = response.indexOf("\"NOMBRE\":");
                                        String nombre = response.substring(n+10);
                                        int nf = nombre.indexOf("\"");
                                        nombre = nombre.substring(0,nf);
                                        Log.e("Nombre",nombre);
                                        editor.putString("NOMBRE",nombre);

                                        editor.commit();
                                        Intent intent = new Intent(MainActivity.this, MenuClienteActivity.class);
                                        intent.putExtra("USUARIO", edt_usuario.getText().toString());
                                        intent.putExtra("ESTADO", "DOCENTE");
                                        startActivity(intent);
                                        finish();
                                    } else if (response.contains("ER")) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(MainActivity.this, "La clave o el usuario no están bien"
                                                , Toast.LENGTH_SHORT).show();

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //Log.e("Error",error.getMessage());
                            //Log.e("Resultado","fallo update "+error);
                        }
                    }){
                        @Override
                        public String getBodyContentType(){
                            return "application/json; charset=utf-8";
                        }
                        @Override
                        public byte[] getBody() throws AuthFailureError
                        {
                            try{
                                return requestBody == null ? null:requestBody.getBytes("utf-8");
                            }catch(UnsupportedEncodingException uee){
                                VolleyLog.wtf("Codificación no soportada al tratar de conectarse con %s usando %s",
                                        requestBody,"utf-8");
                                return null;
                            }
                        }
                    };
                    requestQueue.add(stringRequest);
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        });


    }

    @Override
    protected void onStart(){
        super.onStart();
        /*Verificar si hay una sesión ya creada: Shared Preferences */
        SharedPreferences pref = getSharedPreferences("Usuario.xml",MODE_PRIVATE);
        String user = pref.getString("USUARIO",null);
        if(user!=null){
            /*El usuario ya se ha logueado antes.*/
            String admin = pref.getString("CARGO",null);
            if(admin!=null){
                if(admin.equals("Admin")){
                    Intent intent = new Intent(this,MenuAdminActivity.class);
                    intent.putExtra("USUARIO",user);
                    startActivity(intent);
                }
                else if(admin.equals("Alumno") || admin.equals("Docente")){
                    Intent intent = new Intent(this,MenuClienteActivity.class);
                    intent.putExtra("USUARIO",user);
                    startActivity(intent);
                }
            }
        }
    }

}