package com.upc.ingenieroselectronicos.estudiantessotr;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.squareup.picasso.Picasso;
import com.upc.ingenieroselectronicos.estudiantessotr.adaptadores.Alumno;
import com.upc.ingenieroselectronicos.estudiantessotr.administrador.MenuAdminActivity;
import com.upc.ingenieroselectronicos.estudiantessotr.internet.JSONUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
//TODO 27: Crear el código JAVA para leer la foto y enviarla al servidor
public class MiPerfilActivity extends AppCompatActivity {

    EditText edt_correo, edt_nombre,edt_apellido, edt_codigo, edt_carrera;
    Spinner spi_campus, spi_cargo, spi_retiro;
    Button btn_enviar;
    CircleImageView img_perfil;
    String url = "https://sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/obtener_alumnos.php?key=12345&correo=";
    String url_act = "https://sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/actualizar_datos.php";
    List<Alumno> mis_datos = new ArrayList<>();
    String correo;
    int cambio_foto = 0;

    Bitmap bitmap;
    String ImageName = "image_data";
    ByteArrayOutputStream byteArrayOutputStream;
    byte[] byteArray;
    String ConvertImage;
    HttpURLConnection httpURLConnection;
    URL url_up;
    OutputStream outputStream;
    BufferedWriter bufferedWriter;
    int RC;
    BufferedReader bufferedReader;
    StringBuilder stringBuilder;
    boolean check=true;
    private int GALLERY = 1, CAMERA=2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_perfil);

        img_perfil = findViewById(R.id.img_mifoto);
        edt_correo = findViewById(R.id.edt_correo_edicion);
        edt_nombre = findViewById(R.id.edt_nombre_edicion);
        edt_codigo = findViewById(R.id.edt_codigo_edicion);
        edt_carrera = findViewById(R.id.edt_carrera_edicion);
        edt_apellido = findViewById(R.id.edt_apellido_edicion);
        spi_campus = findViewById(R.id.spi_campus_edicion);
        spi_cargo = findViewById(R.id.spi_cargo_edicion);
        spi_retiro = findViewById(R.id.spi_estado_edicion);
        btn_enviar = findViewById(R.id.btn_editar_edicion);
        /*Vamos a esconder el AppBar y anular el giro del teléfono*/
        getSupportActionBar().setTitle(getString(R.string.editarmisdatos));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedPreferences pref = getSharedPreferences("Usuario.xml", MODE_PRIVATE);
        correo = pref.getString("USUARIO",null);
        correo = correo.toLowerCase();
        String admin = pref.getString("ADMIN",null);
        if(admin.equals("N")){
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);

        }

        try {//Nos conectamos al servidor para obtener la información del alumno
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String mi_url = url+correo;
            Log.e("URL",mi_url);
            final String requestBody = "";

            StringRequest stringRequest = new StringRequest(Request.Method.GET,mi_url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Resp serv", response);
                            try {
                                mis_datos = JSONUtils.parseJsonAlumno(response);
                                edt_apellido.setText(mis_datos.get(0).getApellido());
                                edt_nombre.setText(mis_datos.get(0).getNombre());
                                edt_carrera.setText(mis_datos.get(0).getCarrera());
                                edt_correo.setText(mis_datos.get(0).getCorreo());
                                edt_codigo.setText(mis_datos.get(0).getCodigo());
                                Log.e("URL",mis_datos.get(0).getUrlfoto());
                                Picasso.with(MiPerfilActivity.this).load(mis_datos.get(0).getUrlfoto())
                                        .error(R.drawable.upc).placeholder(R.drawable.upc).into(img_perfil);
                                if(mis_datos.get(0).getEstado().equals("ACTIVO"))
                                    spi_retiro.setSelection(0,false);
                                else
                                    spi_retiro.setSelection(1,false);

                                if(mis_datos.get(0).getCampus().equals("MO"))
                                    spi_campus.setSelection(0);
                                else
                                    spi_campus.setSelection(1);
                                if(mis_datos.get(0).getCargo().equals("Docente"))
                                    spi_cargo.setSelection(1,false);
                                else if(mis_datos.get(0).getCargo().equals("Alumno"))
                                    spi_cargo.setSelection(2,false);
                                else if(mis_datos.get(0).getCargo().equals("Invitado"))
                                    spi_cargo.setSelection(3,false);
                                else
                                    spi_cargo.setSelection(0,false);
                                if(mis_datos.get(0).getEstado().equals("ACTIVO"))
                                    spi_retiro.setSelection(1,false);
                                else
                                    spi_retiro.setSelection(2,false);
                            } catch (Exception ex) {
                                ex.printStackTrace();
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
        }//Fin de la conexión al servidor


        byteArrayOutputStream = new ByteArrayOutputStream();

        img_perfil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mostrarCuadroDialogo();

            }
        });

        btn_enviar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(cambio_foto==1)
                    SubirImagenAlServidor();//Este es un método

                try {//Nos conectamos al servidor para obtener la información del alumno
                    RequestQueue requestQueue = Volley.newRequestQueue(MiPerfilActivity.this);
                    String nombre = edt_nombre.getText().toString();
                    String apellido = edt_apellido.getText().toString();
                    String ciclo = mis_datos.get(0).getCiclo();
                    String correo = edt_correo.getText().toString();
                    String codigo = edt_codigo.getText().toString();
                    String carrera = edt_carrera.getText().toString();
                    int cam = spi_campus.getSelectedItemPosition();
                    String campus=null;
                    if(cam==0)
                        campus = "MO";
                    else
                        campus = "SM";
                    int car = spi_cargo.getSelectedItemPosition();
                    String cargo=null;
                    if(car==1)
                        cargo = "Docente";
                    else if(car==2)
                        cargo = "Alumno";
                    else if(car==3)
                        cargo = "Invitado";
                    int est = spi_retiro.getSelectedItemPosition();
                    String estado=null;
                    if(est==0)
                        estado = "ACTIVO";
                    else
                        estado = "RETIRADO";
                    final String requestBody = "nombre=" +nombre+ "&apellido="+apellido+"&ciclo="+
                            ciclo+"&correo="+correo+"&codigo="+codigo+"&key=12345&carrera="+carrera
                            +"&campus="+campus+"&estado="+estado+"&cargo="+cargo;

                    StringRequest stringRequest = new StringRequest(Request.Method.POST,url_act,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.e("Resp serv", response);
                                    if(response.contains("ACTUALIZAR") && response.contains("OK"))
                                        Toast.makeText(MiPerfilActivity.this,"Se actualizaron los datos",
                                                Toast.LENGTH_SHORT).show();
                                    else if(response.contains("ACTUALIZAR") && response.contains("NO"))
                                        Toast.makeText(MiPerfilActivity.this,"No hubo cambios para actualizar",
                                                Toast.LENGTH_SHORT).show();
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
                }//Fin de la conexión al servidor


            }
        });

    }

    private void mostrarCuadroDialogo()
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        String[] pictureDialogItem={"Photo Gallery","Camera"};
        alertDialog.setItems(pictureDialogItem,new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which){
                switch(which)
                {
                    case 0:
                        choosePhotoFromGallery();
                        break;
                    case 1:
                        takePhotoFromCamera();
                        break;
                }

            }
        });
        alertDialog.show();
    }//Fin de mostrarCuadroDialogo()

    public void choosePhotoFromGallery()
    {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,GALLERY);
    }

    private void takePhotoFromCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==this.RESULT_CANCELED)
        {
            return;
        }
        if(requestCode==GALLERY)
        {
            if(data!=null)
            {
                Uri contentURI=data.getData();
                try
                {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),contentURI);
                    img_perfil.setImageBitmap(bitmap);
                    //Hacemos visible el botón Upload
                    cambio_foto=1;
                }catch(IOException e)
                {
                    e.printStackTrace();
                    Toast.makeText(MiPerfilActivity.this,"Error",Toast.LENGTH_LONG).show();
                }
            }
        }
        else if(requestCode == CAMERA)
        {
            //Guardamos la imagen conseguida de la cámara en un mapa de bits.
            bitmap = (Bitmap)data.getExtras().get("data");
            //la mostramos en nuestro ImageView
            img_perfil.setImageBitmap(bitmap);

        }
    }//Fin del OnActivityResult



    public void SubirImagenAlServidor()
    {
        bitmap.compress(Bitmap.CompressFormat.JPEG,20,byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray,Base64.DEFAULT);
        //AsynTask
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String>
        {
            @Override
            protected void onPreExecute()
            {
                super.onPreExecute();
                Toast.makeText(MiPerfilActivity.this,"Se está subiendo la imagen",
                        Toast.LENGTH_SHORT).show();
            }
            @Override
            protected void onPostExecute(String string1){
                Log.e("Respuesta",string1);
                super.onPostExecute(string1);
                if(string1.contains("IMAGEN") && string1.contains("OK")){
                    Toast.makeText(MiPerfilActivity.this,"Imagen subida",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            protected String doInBackground(Void...params)
            {
                ImageProcessClass imageProcessClass=new ImageProcessClass();
                HashMap<String,String> HashMapParams=new HashMap<>();

                HashMapParams.put("correo",correo);
                HashMapParams.put(ImageName,ConvertImage);

                String FinalData=imageProcessClass.ImageHttpRequest(
                        "https://sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/subir_imagen.php",
                        HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass asyncTaskUploadClass = new AsyncTaskUploadClass();
        asyncTaskUploadClass.execute();

    }//Fin de subirImagenAlServidor()

    public class ImageProcessClass{
        public String ImageHttpRequest(String requestURL, HashMap<String,String> PData)
        {
            StringBuilder stringBuilder = new StringBuilder();
            try{
                url_up = new URL(requestURL);
                httpURLConnection=(HttpURLConnection) url_up.openConnection();
                httpURLConnection.setReadTimeout(20000);
                httpURLConnection.setConnectTimeout(20000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                outputStream=httpURLConnection.getOutputStream();
                bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                bufferedWriter.write(bufferedWriterDataFN(PData));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                RC = httpURLConnection.getResponseCode();
                if(RC==httpURLConnection.HTTP_OK)
                {
                    bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.
                            getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while((RC2=bufferedReader.readLine())!=null)
                    {
                        stringBuilder.append(RC2);
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
        private String bufferedWriterDataFN(HashMap<String,String> HasMapParams) throws UnsupportedEncodingException
        {
            stringBuilder = new StringBuilder();
            for(Map.Entry<String,String>KEY:HasMapParams.entrySet())
            {
                if(check)
                    check=false;
                else
                    stringBuilder.append("&");
                stringBuilder.append(URLEncoder.encode(KEY.getKey(),"UTF-8"));
                stringBuilder.append("=");
                stringBuilder.append(URLEncoder.encode(KEY.getValue(),"UTF-8"));
            }
            return stringBuilder.toString();
        }
    }//Fin de la clase.

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResult)
    {

        super.onRequestPermissionsResult(requestCode,permissions,grantResult);
        if(requestCode==5)
        {
            if(grantResult[0]== PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(MiPerfilActivity.this,"Gracias por permitirnos usar tu cámara",
                        Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(MiPerfilActivity.this,"Uso de cámara inhabilitado,Por favor danos permiso"+
                        " para usar tu cámara",Toast.LENGTH_LONG).show();
            }

        }
    }

    /*Método que se ejecuta al pulsar la flecha de retorno del APPBar*/
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_cerrar:
                //Al cerrar sesión debemos eliminar el registro del SharedPreferences para no volver a
                //entrar al menú principal sin loguearnos.
                SharedPreferences pref = getSharedPreferences("Usuario.xml", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("USUARIO", null);
                editor.putString("ADMIN", null);
                editor.putString("NOMBRE",null);
                editor.commit();
                Intent intent = new Intent(MiPerfilActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.menu_salir:
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}