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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;

//TODO 18: Enviar el correo electrónico en caso de olvido
public class OlvidoActivity extends AppCompatActivity {
    private EditText edt_correo;
    private Button btn_enviar;
    private ProgressBar progressBar;
    private String url = "https://sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/recuperar_clave.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvido);

        btn_enviar = findViewById(R.id.btn_pedir_olvido);
        edt_correo = findViewById(R.id.edt_correo_olvido);
        progressBar = findViewById(R.id.pgb_espera_olvido);

        /*Vamos a esconder el AppBar y anular el giro del teléfono*/
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btn_enviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (!edt_correo.getText().toString().isEmpty()) {
                    /*Solicitamos una nueva contraseña si el correo no está vacío*/
                    progressBar.setVisibility(View.VISIBLE);
                    try {
                        RequestQueue requestQueue = Volley.newRequestQueue(OlvidoActivity.this);
                        String correo = edt_correo.getText().toString();
                        final String requestBody = "correo=" + correo + "&key=12345";
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Log.e("Resp serv", response);
                                        if (response.contains("SOLICITUD") && response.contains("OK")) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(OlvidoActivity.this, R.string.envio_ok
                                                    , Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else if (response.contains("SOLICITUD") && response.contains("NO")) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(OlvidoActivity.this, R.string.envio_no
                                                    , Toast.LENGTH_SHORT).show();
                                            finish();
                                        } else if (response.contains("ER")) {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(OlvidoActivity.this, R.string.envio_er
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