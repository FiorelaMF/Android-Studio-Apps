package com.example.recyclerviewweb.MisClases;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.example.recyclerviewweb.Adaptadores.Alumno;
import com.example.recyclerviewweb.R;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO 18: Vamos a cargar los datos recibidos del intent de MenuAdminActivity y vamos a colocar
// en los EdiText y Spinners. Luego, si se quiere cambiar hacemos la actualizacion
// de datos en el servidor. Además, cuando hagan click sobre la foto se abrirá la galería de fotos
// para que carguen la nueva foto de perfil
public class CambioPerfilActivity extends AppCompatActivity{
    EditText edtCorreo, edtNombre, edtApellidos, edtCodigo, edtCarrera;
    Spinner spiCampus, spiCargo, spiEstado;
    Button btnEnviar;
    CircleImageView imgFoto;
    String url = "sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/obtener_alumnos.php?key=12345&correo=";
    String url_act = "sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/actualizar_datos.php";

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
    private int GALLERY = 1, CAMERA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio_perfil);
        edtCorreo = findViewById(R.id.edtCambioPerfilActivityCorreo);
        edtNombre = findViewById(R.id.edtCambioPerfilActivityNombre);
        edtApellidos = findViewById(R.id.edtCambioPerfilActivityApellidos);
        edtCodigo = findViewById(R.id.edtCambioPerfilActivityCodigo);
        edtCarrera = findViewById(R.id.edtCambioPerfilActivityCarrera);
        spiCampus = findViewById(R.id.spiCambioPerfilActivityCampus);
        spiCargo = findViewById(R.id.spiCambioPerfilActivityCargo);
        spiEstado = findViewById(R.id.spiCambioPerfilActivityEstado);
        btnEnviar = findViewById(R.id.btnCambioPerfilActivityGrabar);
        imgFoto = findViewById(R.id.imgCambioPerfilActivity);

        Intent rx_intent = getIntent();
        if (rx_intent.hasExtra("ALUMNO")){
            Alumno obj = (Alumno)rx_intent.getSerializableExtra("ALUMNO");
            edtNombre.setText(obj.getNombre());
            edtApellidos.setText(obj.getApellido());
            edtCodigo.setText(obj.getCodigo());
            Picasso.with(this).load(obj.getUrlfoto()).error(R.drawable.upc).placeholder(R.drawable.upc).into(imgFoto);
        }

        imgFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                mostrarCuadroDialogo();
            }
        });

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SubirImagenAWebHost();
            }
        });
    }

    private void mostrarCuadroDialogo(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        String[] pictureDialogItem = {"Galeria de fotos","Cámara"};
        alertDialog.setItems(pictureDialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(i){
                    case 0:
                        eligeFotoDeGaleria();
                        break;
                    case 1:
                        eligeFotoDeCamara();
                        break;
                }
            }
        });
        alertDialog.show();
    }

    public void eligeFotoDeGaleria(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,GALLERY);
    }
    public void eligeFotoDeCamara(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode==this.RESULT_CANCELED){
            return;
        }
        if (requestCode==GALLERY){
            if(data!=null){
                Uri contentURI = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),contentURI);
                    imgFoto.setImageBitmap(bitmap);
                    cambio_foto = 1;
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==CAMERA){
            bitmap = (Bitmap)data.getExtras().get("data");
            imgFoto.setImageBitmap(bitmap);
        }


    }

    public void SubirImagenAWebHost(){
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20,byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
        ConvertImage = Base64.encodeToString(byteArray,Base64.DEFAULT);
        // Luego crear clase asincrona
    }

}
