package com.example.pc2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import com.example.pc2.MisClases.CosaUno;

import java.io.UnsupportedEncodingException;

public class SegundaActivity extends AppCompatActivity {
    Spinner spnseccion;
    EditText edtnota;
    TextView txvcodigo;
    Button btnenviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        edtnota= findViewById(R.id.edtCalifica);
        spnseccion=findViewById(R.id.spiEvaluacionessegunda);
        txvcodigo=findViewById(R.id.txvcodigosegunda);
        btnenviar=findViewById(R.id.btnenviarsegunda);

        Intent rx_intent= getIntent();
        if(rx_intent.hasExtra("TABLA")){

            CosaUno TABLA= (CosaUno) rx_intent.getSerializableExtra("TABLA");
            txvcodigo.setText("ALUMNO: "+TABLA.getCodigo());
            btnenviar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //COMIENZA LA CONECCION
                    String url="https://sergiosalasarriaran.000webhostapp.com/sotrupc/2021-2/practica2/actualizarnotas.php";
                    try {
                        RequestQueue requestQueue= Volley.newRequestQueue(SegundaActivity.this);

                        String seccion=spnseccion.getSelectedItem().toString();
                        String nota=edtnota.getText().toString();

                        if(seccion.equals("pc1")){
                            TABLA.setPc1(nota);
                        }else if(seccion.equals("pc2")){
                            TABLA.setPc2(nota);
                        }else if(seccion.equals("lb1")){
                            TABLA.setLb1(nota);
                        }else if(seccion.equals("lb2")){
                            TABLA.setLb2(nota);
                        }else if(seccion.equals("ea")){
                            TABLA.setEa(nota);
                        }else if(seccion.equals("dd")){
                            TABLA.setDd(nota);
                        }else if(seccion.equals("tb")){
                            TABLA.setTb(nota);
                        }


                        final String requestBody ="codigo="+TABLA.getCodigo()+"&seccion="+TABLA.getSeccion()+
                                "&pc1="+TABLA.getPc1()+"&pc2="+TABLA.getPc2()+"&lb1="+TABLA.getLb1()+"&lb2="+
                                TABLA.getLb2()+"&ea="+TABLA.getEa()+"&dd="+TABLA.getDd()+"&tf="+TABLA.getTb();
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                //////////////////////////SE RECIBE LA RESPUESTAAA
                                Log.e(">>>",response);

                                if(response.contains("OK")){
                                    Toast.makeText(SegundaActivity.this, "NOTA ACTUALIZADA", Toast.LENGTH_SHORT).show();

                                }
                                else if(response.contains("NO EXISTE")){
                                    Toast.makeText(SegundaActivity.this, "NO EXISTE", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(SegundaActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
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
                                try{
                                    return requestBody==null? null:requestBody.getBytes("utf-8");
                                }catch (UnsupportedEncodingException uee){
                                    VolleyLog.wtf("Codificcacion no soportada");
                                    return null;
                                }
                            }
                        };
                        requestQueue.add(stringRequest);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                    //ACABA LA CONECCION
                }
            });


        }
    }
}