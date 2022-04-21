package com.upc.ingenieroselectronicos.estudiantessotr;

import androidx.appcompat.app.AppCompatActivity;

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
import com.upc.ingenieroselectronicos.estudiantessotr.misclases.Registro;

import java.io.UnsupportedEncodingException;

//TODO 17: Registrar un nuevo alumno en el semestre actual
public class RegistrarActivity extends AppCompatActivity {
    private EditText edt_nombre, edt_apellido,edt_codigo,edt_correo,edt_clave,edt_claverep,edt_carrera;
    private Spinner spi_cargo,spi_campus;
    private Button btn_registro;
    private ProgressBar progressBar;
    private String url = "https://sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/registrar.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        edt_nombre = findViewById(R.id.edt_nombre_nuevo);
        edt_apellido = findViewById(R.id.edt_apellido_nuevo);
        edt_correo = findViewById(R.id.edt_correo_nuevo);
        edt_codigo = findViewById(R.id.edt_codigo_nuevo);
        edt_clave = findViewById(R.id.edt_clave_nuevo);
        edt_carrera = findViewById(R.id.edt_carrera_nuevo);
        edt_claverep = findViewById(R.id.edt_claverep_nuevo);
        spi_cargo = findViewById(R.id.spi_cargo);
        btn_registro = findViewById(R.id.btn_registrar_nuevo);
        progressBar = findViewById(R.id.pgb_espera_registro);
        spi_campus = findViewById(R.id.spi_campus);

        /*Vamos a esconder el AppBar y anular el giro del teléfono*/
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_registro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Registro reg = new Registro(edt_nombre.getText().toString(),
                        edt_apellido.getText().toString(),
                        edt_correo.getText().toString(),
                        edt_clave.getText().toString(),
                        edt_claverep.getText().toString(),
                        edt_carrera.getText().toString(),
                        spi_cargo.getSelectedItem().toString(),
                        edt_codigo.getText().toString(),
                        spi_campus.getSelectedItem().toString());
                if(!reg.verificarClaves())
                {
                    Toast.makeText(RegistrarActivity.this,getString(R.string.registro_clave_mal),
                            Toast.LENGTH_SHORT).show();
                }
                else if(!reg.verificarCodigo()){
                    Toast.makeText(RegistrarActivity.this,getString(R.string.registro_codigo_mal),
                            Toast.LENGTH_SHORT).show();
                }
                else if(!reg.verificarCorreo()){
                    Toast.makeText(RegistrarActivity.this,getString(R.string.registro_correo_mal),
                            Toast.LENGTH_SHORT).show();
                }
                else if(spi_cargo.getSelectedItemPosition()==0)
                    Toast.makeText(RegistrarActivity.this,getString(R.string.registro_campus_mal),
                            Toast.LENGTH_SHORT).show();
                else if(spi_campus.getSelectedItemPosition()==0)
                    Toast.makeText(RegistrarActivity.this,getString(R.string.registro_campus_mal),
                            Toast.LENGTH_SHORT).show();
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(RegistrarActivity.this);
                        String correo = edt_correo.getText().toString();
                        String nombre = reg.getNombre();
                        String apellido = reg.getApellidos();
                        String carrera = reg.getCarrera();
                        String cargo = spi_cargo.getSelectedItem().toString();
                        if(cargo.equals("Guest") || cargo.equals("Invitado"))
                            cargo = "INVITADO";
                        else if(cargo.equals("Professor") || cargo.equals("Profesor"))
                            cargo = "PROFESOR";
                        else if(cargo.equals("Student") || cargo.equals("Alumno"))
                            cargo = "ALUMNO";
                        else
                            cargo = cargo.toUpperCase();
                        String clave = reg.getClave();
                        String  codigo = reg.getCodigo();
                        String campus=null;
                        if(reg.getCampus().contains("SM"))
                            campus = "SM";
                        else
                            campus = "MO";
                        final String requestBody = "correo=" + correo + "&nombre="+nombre+"&apellido="+
                                apellido+"&carrera="+carrera+"&cargo="+cargo+"&clave="+clave+"&codigo="+
                                codigo+"&key=12345"+"&campus="+campus;
                        Log.e("ENVIADO",requestBody);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("Resp serv", response);
                                        if (response.contains("REGISTRO") && response.contains("OK")) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrarActivity.this, R.string.registro_ok
                                                    , Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegistrarActivity.this, MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else if (response.contains("REGISTRO") && response.contains("REP")) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrarActivity.this, R.string.registro_rep
                                                    , Toast.LENGTH_SHORT).show();

                                        } else if (response.contains("ER")) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrarActivity.this, R.string.registro_er
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

                            @Override
                            public byte[] getBody() throws AuthFailureError {
                                try {
                                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                                } catch (UnsupportedEncodingException uee) {
                                    VolleyLog.wtf("Codificación no soportada al tratar de conectarse con %s usando %s",
                                            requestBody, "utf-8");
                                    return null;
                                }
                            }
                        };
                        requestQueue.add(stringRequest);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                }

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        finish();
        return true;
    }

}