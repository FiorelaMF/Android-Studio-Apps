package app.acsolutions.ventisysupc.COMUNI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import app.acsolutions.ventisysupc.CLIENTE.ClienteDatosActivity;
import app.acsolutions.ventisysupc.MENUS.MainActivity;
import app.acsolutions.ventisysupc.R;

public class ContraActivity extends AppCompatActivity implements View.OnClickListener{
    private Button CAMBIO,BACK;
    private EditText ANTIGUA,NUEVA,REPETICION;
    private AlertDialog.Builder MENSAJE;
    private ProgressDialog PROGRESO;
    String CLAVE,USUARIO;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uso_contra);
        BACK = findViewById(R.id.back);
        CAMBIO = findViewById(R.id.envio);
        ANTIGUA = findViewById(R.id.antigua);
        NUEVA = findViewById(R.id.nueva);
        REPETICION = findViewById(R.id.repite);
        MENSAJE = new AlertDialog.Builder(this);
        Bundle EXTRAS = getIntent().getExtras();
        if (EXTRAS == null){
            CLAVE = "NO"; USUARIO = "NO";
        }
        else{
            USUARIO = EXTRAS.getString("CODIGO");
            CLAVE = EXTRAS.getString("CLAVE");
        }
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }
        else if (view.getId() == CAMBIO.getId()){
            AdiosTeclado();
            String A = ANTIGUA.getText().toString();
            String N = NUEVA.getText().toString();
            String R = REPETICION.getText().toString();
            PROGRESO = ProgressDialog.show(this,"¡Comprobando!", "Por favor espere...",false,false);
            if(A.equals(CLAVE)){
                if (N.length()>=5){
                    if (R.equals(N)){
                        db.collection("usuarios").document(USUARIO)
                                .update("CLAVE",NUEVA.getText().toString())
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        PROGRESO.dismiss();
                                        ANTIGUA.setText("");
                                        NUEVA.setText("");
                                        REPETICION.setText("");
                                        MENSAJE.setTitle("Contraseña Cambiada con Éxito");
                                        MENSAJE.setMessage("Se Cerrará la Sesión");
                                        MENSAJE.setCancelable(false);
                                        MENSAJE.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finishAffinity();
                                            }}).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        PROGRESO.dismiss();
                                        Toast.makeText(ContraActivity.this, "NO SE PUDO", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else{
                        PROGRESO.dismiss();
                        Toast.makeText(ContraActivity.this, "La Contraseña Nueva y su Confirmación no son iguales", Toast.LENGTH_LONG).show();
                        ANTIGUA.setText("");
                        NUEVA.setText("");
                        REPETICION.setText("");
                    }
                }
                else{
                    PROGRESO.dismiss();
                    Toast.makeText(ContraActivity.this, "La Contraseña Nueva debe tener 5 o más caracteres", Toast.LENGTH_LONG).show();
                    ANTIGUA.setText("");
                    NUEVA.setText("");
                    REPETICION.setText("");
                }
            }
            else{
                PROGRESO.dismiss();
                Toast.makeText(ContraActivity.this, "La Contraseña Antigua no coincide con la registrada en la base de datos", Toast.LENGTH_LONG).show();
                ANTIGUA.setText("");
                NUEVA.setText("");
                REPETICION.setText("");
            }
        }
    }
    private void AdiosTeclado() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            IMM.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
}