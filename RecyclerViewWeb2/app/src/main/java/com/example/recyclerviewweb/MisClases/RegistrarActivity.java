package com.example.recyclerviewweb.MisClases;

import androidx.appcompat.app.AppCompatActivity;

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
import com.example.recyclerviewweb.R;

import java.io.UnsupportedEncodingException;

//TODO 10: Registrar a un nuevo usuario en el curso SOTR
public class RegistrarActivity extends AppCompatActivity {
    private String url = "sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/registrar.php";
    private EditText edtNombre, edtApellidos, edtCorreo, edtCodigo,
            edtClave, edtClaveRep, edtCarrera;
    private Spinner spiCargo, spiCampus;
    private Button btnRegistro;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        edtNombre = findViewById(R.id.edtRegistrarActivityNombre);
        edtApellidos = findViewById(R.id.edtRegistrarActivityApellido);
        edtCorreo = findViewById(R.id.edtRegistrarActivityCorreo);
        edtCodigo = findViewById(R.id.edtRegistrarActivityCodigo);
        edtClave = findViewById(R.id.edtRegistrarActivityClave);
        edtClaveRep = findViewById(R.id.edtRegistrarActivityClaveRep);
        edtCarrera = findViewById(R.id.edtRegistrarActivityCarrera);
        spiCampus = findViewById(R.id.spiRegistrarActivityCampus);
        spiCargo = findViewById(R.id.spiRegistrarActivityCargo);
        btnRegistro = findViewById(R.id.btnRegistrarActivityRegistrar);
        progressBar = findViewById(R.id.pgbRegistrarActivityEspera);

        //Quitamos el giro del telefono
        //getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Flecha de retroceso del App
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Detecta el click del boton de registro
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Registro reg = new Registro(edtNombre.getText().toString(),
                                            edtApellidos.getText().toString(),
                                            edtCorreo.getText().toString(),
                                            edtClave.getText().toString(),
                                            edtClaveRep.getText().toString(),
                                            edtCarrera.getText().toString(),
                                            spiCargo.getSelectedItem().toString(),
                                            edtCodigo.getText().toString(),
                                            spiCampus.getSelectedItem().toString()
                                            );
                if(!reg.verificarClave()){
                    Toast.makeText(RegistrarActivity.this,
                            "Las contraseñas no son iguales o son muy cortas",
                            Toast.LENGTH_SHORT).show();
                } else if(!reg.verificarCodigo()){
                    Toast.makeText(RegistrarActivity.this,
                            "El código no corresponde a uno de UPC",
                            Toast.LENGTH_SHORT).show();
                } else if(!reg.verificarCorreo()){
                    Toast.makeText(RegistrarActivity.this,
                            "El correo no es correcto",
                            Toast.LENGTH_SHORT).show();
                } else if(spiCargo.getSelectedItemPosition()==0){
                    Toast.makeText(RegistrarActivity.this,
                            "Debe elegir un cargo",
                            Toast.LENGTH_SHORT).show();
                } else if(spiCampus.getSelectedItemPosition()==0){
                    Toast.makeText(RegistrarActivity.this,
                            "Debe elegir un campus",
                            Toast.LENGTH_SHORT).show();
                }
                else{
                    // Cumplio todo. Enviar datos al registro Web POST
                    progressBar.setVisibility(View.VISIBLE);
                    try{
                        RequestQueue requestQueue = Volley.newRequestQueue(RegistrarActivity.this);
                        String correo = edtCorreo.getText().toString();
                        String nombre = edtNombre.getText().toString();
                        String apellidos = edtApellidos.getText().toString();
                        String carrera = edtCarrera.getText().toString();
                        String cargo = spiCargo.getSelectedItem().toString();
                        //cargo = cargo.toLowerCase();
                        String clave = edtClave.getText().toString();
                        String codigo = edtCodigo.getText().toString();
                        String campus = spiCampus.getSelectedItem().toString();
                        final String requestBody = "correo="+correo+"&nombre="+nombre+
                                "&apellido="+apellidos+"&carrera="+carrera+"&cargo="+cargo+
                                "&clave="+clave+"&codigo="+codigo+"&key=123456&campus="+campus;

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("Respuesta", response);
                                        progressBar.setVisibility(View.GONE);
                                        if(response.contains("REGISTRO") && response.contains("OK")){
                                            Toast.makeText(RegistrarActivity.this,
                                                    "Se realizó el registro correctamente",
                                                    Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else if(response.contains("REGISTRO") && response.contains("REPETIDO")){
                                            Toast.makeText(RegistrarActivity.this,
                                                    "Este código ya se encuentra registrado",
                                                    Toast.LENGTH_SHORT).show();
                                        } else if(response.contains("ERROR")){
                                            Toast.makeText(RegistrarActivity.this,
                                                    "Algo salió mal. Revie los datos ingresados",
                                                    Toast.LENGTH_SHORT).show();
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
                                try {
                                    // if requestBody == null return null else requestBody
                                    return requestBody==null? null:requestBody.getBytes("utf-8");
                                } catch (UnsupportedEncodingException uee){
                                    VolleyLog.wtf("Codificacion no soportada");
                                    return null;
                                }
                            }
                        };
                        requestQueue.add(stringRequest);
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }

                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}