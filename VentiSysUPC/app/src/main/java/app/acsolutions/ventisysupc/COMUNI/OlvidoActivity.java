package app.acsolutions.ventisysupc.COMUNI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import app.acsolutions.ventisysupc.R;

public class OlvidoActivity extends AppCompatActivity implements View.OnClickListener{
    private Button ENVIAR,BACK;
    private EditText CORREO;
    private AlertDialog.Builder MENSAJE;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String DATO,COR,TIP,COD,CLA,AP,AM,NOM;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uso_olvido);
        BACK = findViewById(R.id.back);
        ENVIAR = findViewById(R.id.envio);
        CORREO = findViewById(R.id.codigo);
        MENSAJE = new AlertDialog.Builder(this);
        MENSAJE.setTitle("MENSAJE");
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == ENVIAR.getId()){
            AdiosTeclado();
            String U = CORREO.getText().toString().toUpperCase();
            String[] coso = U.split("@");
            U = coso[0];
            if (U.length()>=1 && !U.equals(" ")){
                DocumentReference DOCREF = db.collection("usuarios").document(U);
                DOCREF.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (Objects.requireNonNull(document).exists()) {
                                DATO = Objects.requireNonNull(document.getData()).toString();
                                SacarValores(DATO);
                                EnviaCorreo(COR,CLA);
                                CORREO.setText("");
                            }
                            else {
                                MENSAJE.setTitle("Usuario No Registrado");
                                MENSAJE.setMessage("El Usuario Ingresado no se encuentra registrado en la base de datos");
                                MENSAJE.show();
                            }
                        } else {
                            MENSAJE.setTitle("¡Error de Conexión!");
                            MENSAJE.setMessage("No se puso conectar satisfactoriamente " +  task.getException() );
                            MENSAJE.show();
                        }
                    }
                });
            }
        }
        else if (view.getId() == BACK.getId()){
            onBackPressed();
        }
    }
    private void EnviaCorreo(String CC, String PP) {
        String MENSAJOTE = "Estimado "+ NOM + " " + AP + ":\n\nTu contraseña es : " + PP;
        String ASUNTOTE = "Envio de Contraseña LAB2";
        JavaMailAPI JAVA = new JavaMailAPI(this,CC,ASUNTOTE,MENSAJOTE);
        JAVA.execute();
    }
    private void AdiosTeclado() {
        View view = this.getCurrentFocus();
        if (view != null){
            InputMethodManager IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            IMM.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }
    private void SacarValores(String dato) {
        dato = dato.replace("{", "");
        dato = dato.replace(" ", "");
        dato = dato.replace("}", "");
        String[] DATOS = dato.split(",");
        for (String s : DATOS) {
            String[] COSO = s.split("=");
            switch (COSO[0]) {
                case "CORREO":
                    COR = COSO[1]; break;
                case "TIPO":
                    TIP = COSO[1]; break;
                case "CODIGO":
                    COD = COSO[1]; break;
                case "CLAVE":
                    CLA = COSO[1]; break;
                case "AP":
                    AP = COSO[1]; break;
                case "AM":
                    AM = COSO[1]; break;
                case "NOMBRE":
                    NOM = COSO[1]; break;
            }
        }
    }
}