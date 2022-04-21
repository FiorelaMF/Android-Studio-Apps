package com.example.laboratorio2.admin;

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
import com.example.laboratorio2.MisClases.Registro;
import com.example.laboratorio2.R;

import java.io.UnsupportedEncodingException;

public class RegistrarAlumnoActivity extends AppCompatActivity {
    private EditText edt_codigo, edt_correo, edt_nombre, edt_apellidop, edt_apellidom;
    private Spinner spi_carrera, spi_sexo, spi_estado,spi_campus;
    private Button btn_registro;
    private ProgressBar progressBar;
    private String url = "https://fmanco.000webhostapp.com/SOTR/AndroidRWeb/post_admin_registrar.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_alumno);

        edt_codigo = findViewById(R.id.edtAdmin3Codigo);
        edt_correo = findViewById(R.id.edtAdmin3Correo);
        edt_nombre = findViewById(R.id.edtAdmin3Nombre);
        edt_apellidop = findViewById(R.id.edtAdmin3Apellidop);
        edt_apellidom = findViewById(R.id.edtAdmin3Apellidom);
        spi_carrera = findViewById(R.id.spiAdmin3Carrera);
        spi_sexo = findViewById(R.id.spiAdmin3Sexo);
        spi_estado = findViewById(R.id.spiAdmin3Estado);
        spi_campus = findViewById(R.id.spiAdmin3Campus);
        btn_registro = findViewById(R.id.btnAdmin3Registrar);

        /*Vamos a esconder el AppBar y anular el giro del teléfono*/
        //getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btn_registro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Registro reg = new Registro(edt_codigo.getText().toString(),
                        edt_nombre.getText().toString(),
                        edt_apellidop.getText().toString(),
                        edt_apellidom.getText().toString(),
                        spi_carrera.getSelectedItem().toString(),
                        spi_sexo.getSelectedItem().toString(),
                        spi_campus.getSelectedItem().toString(),
                        spi_estado.getSelectedItem().toString());
                if(!reg.verificarCodigo()){
                    Toast.makeText(RegistrarAlumnoActivity.this,"Código incorrecto. Debe tener el formato u20212XXXX",
                            Toast.LENGTH_SHORT).show();
                }
                else if(spi_carrera.getSelectedItemPosition()==0)
                    Toast.makeText(RegistrarAlumnoActivity.this,"Elija una carrera",
                            Toast.LENGTH_SHORT).show();
                else if(spi_sexo.getSelectedItemPosition()==0)
                    Toast.makeText(RegistrarAlumnoActivity.this,"Elija un sexo",
                            Toast.LENGTH_SHORT).show();
                else if(spi_estado.getSelectedItemPosition()==0)
                    Toast.makeText(RegistrarAlumnoActivity.this,"Elija un cargo",
                            Toast.LENGTH_SHORT).show();
                else if(spi_campus.getSelectedItemPosition()==0)
                    Toast.makeText(RegistrarAlumnoActivity.this,"Elija un campus.",
                            Toast.LENGTH_SHORT).show();
                else{
                    //progressBar.setVisibility(View.VISIBLE);
                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(RegistrarAlumnoActivity.this);
                        String codigo = edt_codigo.getText().toString();
                        String nombre = reg.getNombre();
                        String apellidop = reg.getApellidop();
                        String apellidom = reg.getApellidom();
                        String carrera = spi_carrera.getSelectedItem().toString();
                        String sexo = spi_sexo.getSelectedItem().toString();
                        String campus = spi_campus.getSelectedItem().toString();
                        String estado = spi_estado.getSelectedItem().toString();

                        final String requestBody = "codigo=" + codigo + "&nombre="+nombre+"&apellidop="+
                                apellidop+"&apellidom"+apellidom+"&carrera="+carrera+"&sexo="+sexo+"&campus="+campus+
                                "&estado="+estado+"&key=12345";
                        Log.e("ENVIADO",requestBody);
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("Resp serv", response);
                                        if (response.contains("REGISTRO") && response.contains("OK")) {
                                            //progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrarAlumnoActivity.this, "Se registró exitosamente."
                                                    , Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(RegistrarAlumnoActivity.this, MenuAdminActivity.class);
                                            startActivity(intent);
                                            finish();
                                        } else if (response.contains("REGISTRO") && response.contains("REP")) {
                                            //progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrarAlumnoActivity.this, "Este alumno ya está registrado."
                                                    , Toast.LENGTH_SHORT).show();

                                        } else if (response.contains("ER")) {
                                            //progressBar.setVisibility(View.GONE);
                                            Toast.makeText(RegistrarAlumnoActivity.this, "Algo falló. Revise los datos nuevamente."
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
}