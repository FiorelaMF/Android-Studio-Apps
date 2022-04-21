package com.upc.ingenieroselectronicos.ejemplofirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.upc.ingenieroselectronicos.ejemplofirebase.MisClases.Donante;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

//TODO 15: Vamos a editar los datos de un registro Firebase seleccionado y aparte subiremos
// un archivo de imágen al storage de Firebase.
public class EditarDonanteActivity extends AppCompatActivity {
    private EditText edtNombre, edtCorreo, edtFecha, edtDonacion;
    private CircleImageView imgFoto;
    private Button btnUpdate;
    private RadioButton rbMasculino, rbFemenino;
    private FirebaseStorage storage= FirebaseStorage.getInstance();
    private StorageReference storageRef;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mDbRef;
    private Donante obj;

    private Bitmap bitmap;
    private String ImageName = "image_data";
    private ByteArrayOutputStream byteArrayOutputStream;
    private final int CAMERA = 1;
    private final int GALLERY = 2;
    private  int cambio_foto = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_donante);
        edtCorreo = findViewById(R.id.edtEditarDonanteActivityCorreo);
        edtNombre = findViewById(R.id.edtEditarDonanteActivityNombre);
        edtDonacion = findViewById(R.id.edtEditarDonanteActivityDonacion);
        edtFecha = findViewById(R.id.edtEditarDonanteActivityFecha);
        imgFoto = findViewById(R.id.imgEditarDonanteActivityFoto);
        btnUpdate = findViewById(R.id.btnEditarDonanteActivityActualizar);
        rbMasculino = findViewById(R.id.rbEditarDonanteActivityMasculino);
        rbFemenino = findViewById(R.id.rbEditarDonanteActivityFemenino);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        storageRef = storage.getReference();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        obj = (Donante)bundle.getSerializable("DATOS");
        edtNombre.setText(obj.getNombre());
        edtCorreo.setText(obj.getCorreo());
        edtCorreo.setEnabled(false);
        edtDonacion.setText("S/."+obj.getDonacion());
        edtFecha.setText(obj.getFecha());
        if(obj.isSexof())
            rbFemenino.setChecked(true);
        else
            rbMasculino.setChecked(true);
        if(obj.getUrlfoto().equals("ninguna"))
            imgFoto.setImageResource(R.drawable.user);
        else {
            Picasso.with(this).load(obj.getUrlfoto()).error(R.drawable.user).into(imgFoto);
            Log.e("URL FOTO", obj.getUrlfoto());
        }
        Log.e("KEY",obj.getKey());
        imgFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostrarCuadroDialogo();
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        mDbRef = mDatabase.getReference("Donadores/Datos");

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cambio_foto==1){
                    cambio_foto=0;
                    StorageReference ref = storageRef.child("donantes/"+obj.getCorreo());
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
                    byte[] data = baos.toByteArray();
                    UploadTask uploadTask = ref.putBytes(data);
                    uploadTask.addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull  Exception e) {

                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //Ha subido el archivo satisfactoriamente
                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    //Acá recibo la URL de la foto qeu acabo de subir
                                    String imageUrl = uri.toString();
                                    Log.e("imageURL",imageUrl);
                                    mDbRef.child(obj.getKey()).child("urlfoto").setValue(imageUrl);
                                    obj.setUrlfoto(imageUrl);
                                    Toast.makeText(EditarDonanteActivity.this,"Se actualizó la foto en Firebase",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
                double donacion = 0.0;
                boolean sexof;
                if(edtDonacion.getText().toString().contains("S/.")){
                    String monto = edtDonacion.getText().toString().substring((3));
                    donacion = Double.parseDouble(monto);
                }
                else
                    donacion = Double.parseDouble(edtDonacion.getText().toString());
                if(rbFemenino.isChecked())
                    sexof = true;
                else
                    sexof = false;
                Donante nuevo = new Donante(edtNombre.getText().toString(),
                        edtCorreo.getText().toString(),
                        sexof, edtFecha.getText().toString(),
                        donacion, obj.getUrlfoto());
                nuevo.setKey(obj.getKey());//Se debe enviar la clase con el Key, si se manda null desaparecerá la variable en el RealTimeDatabase
                mDbRef.child(obj.getKey()).setValue(nuevo);
                Toast.makeText(EditarDonanteActivity.this,"Se actualizaron los datos",
                        Toast.LENGTH_SHORT).show();
            }
        });




    }

    private void mostrarCuadroDialogo(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        String[] pictureDialogItem = {"Galeria de fotos","Camara"};
        alertDialog.setItems(pictureDialogItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch(i){
                    case 0:
                        choosePhotoFromGallery();
                        break;
                    case 1:
                        //choosePhotoFromCamara();
                        break;
                }
            }
        });
        alertDialog.show();
    }

    public void choosePhotoFromGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent,GALLERY);
    }
    public void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(requestCode==GALLERY){
            if(data!=null){
                Uri contentURI = data.getData();
                try{
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),contentURI);
                    imgFoto.setImageBitmap(bitmap);
                    cambio_foto=1;
                }catch(IOException e){
                    e.printStackTrace();
                    Toast.makeText(this,"Error de lectura",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}