package com.example.recyclerviewweb.MisClases;

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
import com.example.recyclerviewweb.Administrador.MenuAdminActivity;
import com.example.recyclerviewweb.Cliente.MenuClienteActivity;
import com.example.recyclerviewweb.R;

import java.nio.charset.StandardCharsets;
// TODO 6: Conectamos a internet en caso se pulse el boton
//         o nos vamos a lasactividades de Olvido a Registro

public class MainActivity extends AppCompatActivity {
    //private String url = "https://fmanco.000webhostapp.com/SOTR/AndroidRWeb/loguin.php";
    private String url = "https://sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/loguin.php";
    private Button btnIngresar;
    private EditText edtUsuario, edtClave;
    private ProgressBar progressBar;
    private TextView txvRegistrar, txvOlvido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnIngresar = findViewById(R.id.btnMainActivityIngresar);
        edtUsuario = findViewById(R.id.edtMainActivityUsuario);
        edtClave = findViewById(R.id.edtMainActivityClave);
        progressBar = findViewById(R.id.pgbMainActivityEspera);
        txvOlvido = findViewById(R.id.txvMainActivityOlvido);
        txvRegistrar = findViewById(R.id.txvMainActivityRegistrar);
        progressBar.setVisibility(View.GONE);

        // Vamos a escondar el AppBar
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //Detectar el click en caso me olvide de la contraseña.
        //Para esto se implementa un metodo abstracto en una clase anonima
        txvOlvido.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, OlvidoActivity.class);
                startActivity(intent);
            }
        });

        //Detectar el click en caso quiera registrar un nuevo usuario
        txvRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
                startActivity(intent);
            }
        });

        //Detectar el click del boton INGRESAR y conectar por POST a la URL
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                btnIngresar.setEnabled(false);

                //Conexion a Internet
                try{
                    RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                    String usuario = edtUsuario.getText().toString();
                    String clave = edtClave.getText().toString();
                    final String requestBody = "correo="+usuario+"&key=12345&clave="+clave;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("Respuesta",response);
                                    progressBar.setVisibility(View.GONE);
                                    btnIngresar.setEnabled(true);

                                    //Aqui tengo la respuesta del servidor
                                    if(response.contains("OK") && response.contains("\"ADMIN\":\"S\"")){
                                        Toast.makeText(MainActivity.this,"Bienvenido en modo administrador",Toast.LENGTH_SHORT).show();
                                        SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("USUARIO",edtUsuario.getText().toString());
                                        editor.putString("ADMIN","S");

                                        //Vamos a obtener el nombre del usuario de la cadena JSON
                                        int n = response.indexOf("\"NOMBRE\":");
                                        String nombre = response.substring(n+10);
                                        int nf = nombre.indexOf("\"");
                                        nombre = nombre.substring(0,nf);
                                        Log.e("Nombre",nombre);
                                        editor.putString("NOMBRE",nombre);
                                        editor.commit();
                                        Intent intent = new Intent(MainActivity.this, MenuAdminActivity.class);
                                        startActivity(intent);
                                        finish(); //Matamos al MAinActivity


                                    } else if(response.contains("OK") && response.contains("\"ADMIN\":\"N\"")){
                                        Toast.makeText(MainActivity.this,"Bienvenido en modo cliente",
                                                Toast.LENGTH_SHORT).show();
                                        SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);
                                        SharedPreferences.Editor editor = pref.edit();
                                        editor.putString("USUARIO",edtUsuario.getText().toString());
                                        editor.putString("ADMIN","N");

                                        //Vamos a obtener el nombre del usuario de la cadena JSON
                                        int n = response.indexOf("\"NOMBRE\":");
                                        String nombre = response.substring(n+10);
                                        int nf = nombre.indexOf("\"");
                                        nombre = nombre.substring(0,nf);
                                        Log.e("Nombre",nombre);
                                        editor.putString("NOMBRE",nombre);
                                        editor.commit();
                                        Intent intent = new Intent(MainActivity.this, MenuClienteActivity.class);
                                        startActivity(intent);
                                        finish(); //Matamos al MAinActivity

                                    } else if(response.contains("ER")){
                                        Toast.makeText(MainActivity.this,"La clave o el usuario no corresponden",
                                                Toast.LENGTH_SHORT).show();
                                    } else{
                                        Toast.makeText(MainActivity.this,"Revise sus datos. No están en el formato correcto.",
                                                Toast.LENGTH_SHORT).show();
                                    }

                                }

                                },new Response.ErrorListener(){
                                    @Override
                                    public void onErrorResponse(VolleyError error){
                                        Log.e("Error","Fallo del servidor");
                                    }
                                }
                            ){
                                @Override
                        public String getBodyContentType(){
                                    return "application/json; charset=utf-8";
                                }
                                @Override
                                public byte[] getBody() throws AuthFailureError {
                                    try{
                                        //if requestBody==null return null else return requestBody.getBytes
                                        // "utf-8"
                                        return requestBody==null? null:requestBody.getBytes(StandardCharsets.UTF_8);
                                        //throw new UnsupportedEncodingException("Unsupported Encoding Exception");
                                    } catch(Exception uee){
                                        VolleyLog.wtf("Codificación no soportada");
                                        return null;
                                    }
                                }
                    };
                    requestQueue.add(stringRequest);

                } catch(Exception ex){
                    ex.printStackTrace();
                }
            }

        });

    }

    @Override
    public void onStart(){
        super.onStart();
        SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);
        if (pref.contains("ADMIN") && pref.contains("USUARIO")){
            String admin = pref.getString("ADMIN",null);
            String usuario = pref.getString("USUARIO",null);
            if(usuario!=null){
                if(admin.equals("S")){
                    Intent intent = new Intent(this,MenuAdminActivity.class);
                    startActivity(intent);
                    finish();
                } else if(admin.equals("N")){
                    Intent intent = new Intent(this,MenuClienteActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }
    }

}