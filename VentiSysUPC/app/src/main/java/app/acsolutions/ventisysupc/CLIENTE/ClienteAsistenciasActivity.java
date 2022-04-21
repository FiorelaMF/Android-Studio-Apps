package app.acsolutions.ventisysupc.CLIENTE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import app.acsolutions.ventisysupc.LISTAS.MisAsistencias;
import app.acsolutions.ventisysupc.LISTAS.MisAsistenciasAdaptador;
import app.acsolutions.ventisysupc.R;

public class ClienteAsistenciasActivity extends AppCompatActivity implements View.OnClickListener{
    RecyclerView LISTA;
    List<MisAsistencias> asistencias;
    MisAsistenciasAdaptador adapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button BACK;
    private TextView ASISTENCIAS,TARDANZAS,FALTAS;
    private ProgressDialog PROGRESO;
    String CODIGO,DATO;
    int ASI,FAL,TAR;
    ArrayList<String> ESTADO = new ArrayList<>();
    ArrayList<String> FECHAS = new ArrayList<>();
    ArrayList<String> INGRES = new ArrayList<>();
    ArrayList<String> SALIDA = new ArrayList<>();
    ArrayList<String> TEMPER = new ArrayList<>();
    ArrayList<String> STRING = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_asistencias);
        BACK = findViewById(R.id.back);
        ASISTENCIAS = findViewById(R.id.asist);
        TARDANZAS = findViewById(R.id.tarda);
        FALTAS = findViewById(R.id.falta);
        LISTA = findViewById(R.id.lista);
        Bundle EXTRAS = getIntent().getExtras();
        if (EXTRAS == null){ CODIGO = "NO"; }
        else { CODIGO = EXTRAS.getString("CODIGO"); }
        ASI = 0;
        FAL = 0;
        TAR = 0;
        LISTA.setLayoutManager(new LinearLayoutManager(this));
        asistencias = new ArrayList<>();
        adapter = new MisAsistenciasAdaptador(asistencias);
        LISTA.setAdapter(adapter);

        PROGRESO = ProgressDialog.show(this,"¡Cargando Información!", "Por favor espere...",false,false);
        db.collection("asistencias").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @SuppressLint({"SetTextI18n", "NotifyDataSetChanged"})
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        String[] coso = document.getId().split("_");
                        Log.e("ssss: ",document.getId());
                        if (CODIGO.equals(coso[6])){
                            DATO = document.getData().toString();
                            DATO = DATO.replace("{", "");
                            DATO = DATO.replace("}", "");
                            Log.e("DATO: ",DATO);
                            String[] DATOS = DATO.split(",");
                            for (String s : DATOS) {
                                Log.e("DATO: ","|"+s+"|");
                                String[] COSO = s.split("=");
                                switch (COSO[0]) {
                                    case " CODIGO":
                                        STRING.add(COSO[1]); break;
                                    case " ESTADO":
                                        if (COSO[1].equals("ASISTIÓ")){ ASI = ASI + 1; }
                                        else if (COSO[1].equals("TARDE")){ TAR = TAR + 1; }
                                        else if (COSO[1].equals("FALTA")){ FAL = FAL + 1; }
                                        ESTADO.add(COSO[1]); break;
                                    case " FECHA":
                                        FECHAS.add(COSO[1]); break;
                                    case " INGRESO":
                                        INGRES.add(COSO[1]); break;
                                    case " SALIDA":
                                        SALIDA.add(COSO[1]); break;
                                    case "TEMPERATURA":
                                        TEMPER.add(COSO[1]); break;
                                }
                            }
                        }
                    }
                }
                else { Toast.makeText(ClienteAsistenciasActivity.this, "Error de Conexión", Toast.LENGTH_LONG).show(); }
                Log.e("l101: ", STRING.toString());
                Log.e("l102: ", ESTADO.toString());
                Log.e("l103: ", FECHAS.toString());
                Log.e("l104: ", INGRES.toString());
                Log.e("l105: ", SALIDA.toString());
                Log.e("l106: ", TEMPER.toString());
                asistencias.removeAll(asistencias);
                // ORDENAR
                if (STRING.size()>0){
                    for (int I = 0;I<STRING.size();I++){
                        MisAsistencias CADENA = new MisAsistencias(ESTADO.get(I),FECHAS.get(I),INGRES.get(I),SALIDA.get(I),TEMPER.get(I));
                        asistencias.add(CADENA);
                    }
                    adapter.notifyDataSetChanged();
                    ASISTENCIAS.setText(ASI + " ASISTENCIA(S)");
                    TARDANZAS.setText(TAR + " TARDANZA(S)");
                    FALTAS.setText(FAL + " FALTA(S)");
                }
                else{
                    LISTA.setVisibility(View.INVISIBLE);
                }
                PROGRESO.dismiss();
            }
        });
    }
    @Override
    public void onClick(View view) {
        if (view.getId() == BACK.getId()){
            onBackPressed();
        }
    }
}