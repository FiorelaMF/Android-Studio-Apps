package com.upc.ingenieroselectronicos.estudiantessotr.administrador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
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
import com.upc.ingenieroselectronicos.estudiantessotr.MainActivity;
import com.upc.ingenieroselectronicos.estudiantessotr.MiPerfilActivity;
import com.upc.ingenieroselectronicos.estudiantessotr.R;
import com.upc.ingenieroselectronicos.estudiantessotr.adaptadores.Alumno;
import com.upc.ingenieroselectronicos.estudiantessotr.adaptadores.AlumnoAdapter;
import com.upc.ingenieroselectronicos.estudiantessotr.internet.JSONUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

//TODO 20: Ingresar el código del menú principal
public class MenuAdminActivity extends AppCompatActivity {
    RecyclerView lista_alumnos;

    RelativeLayout relative_lista_alumnos;
    Spinner spinner_ciclos;
    TextView txv_semestre;
    List<Alumno> alumnos = new ArrayList<>();
    List<String> ciclos = new ArrayList<>();
    List<Alumno> lista;
    AlumnoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);
        lista_alumnos = findViewById(R.id.recycler_alumnos);

        relative_lista_alumnos = findViewById(R.id.relative_lista_alumnos);
        spinner_ciclos = findViewById(R.id.spinner_ciclos);
        txv_semestre = findViewById(R.id.txv_titulo_lista_alumnos);

        LinearLayoutManager layout = new LinearLayoutManager(this);
        lista_alumnos.setLayoutManager(layout);
        spinner_ciclos.setEnabled(false);
        txv_semestre.setText(getString(R.string.semestre));
        SharedPreferences pref = getSharedPreferences("Usuario.xml", MODE_PRIVATE);
        if(pref.contains("NOMBRE")){
            getSupportActionBar().setTitle(getString(R.string.hola)+" "+pref.getString("NOMBRE",""));
        }

        try{
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            String url = "https://sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/obtener_alumnos.php?key=12345";
            final String requestBody = "";

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.e("Resp serv", response);
                            try {
                                alumnos = JSONUtils.parseJsonAlumno(response);
                                Log.e("Tam",Integer.toString(alumnos.size()));

                                ciclos = JSONUtils.devolver_ciclos(alumnos);
                                Log.e("ciclos",Integer.toString(ciclos.size()));
                                spinner_ciclos.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                                        android.R.layout.simple_spinner_dropdown_item,ciclos));
                                spinner_ciclos.setEnabled(true);
                                spinner_ciclos.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        String ciclo = spinner_ciclos.getSelectedItem().toString();
                                        lista = new ArrayList<>();
                                        //Cargamos la lista con el ciclo de interés.
                                        for(int i = 0;i<alumnos.size();i++){
                                            if(alumnos.get(i).getCiclo().equals(ciclo)){
                                                lista.add(alumnos.get(i));
                                            }
                                        }
                                        adapter = new AlumnoAdapter(MenuAdminActivity.this,lista);
                                        lista_alumnos.setAdapter(adapter);
                                        SharedPreferences pref = getSharedPreferences("Usuario.xml", MODE_PRIVATE);
                                        final String admin = pref.getString("ADMIN",null);
                                        adapter.setOnItemClickListener(new AlumnoAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Alumno p) {
                                                if(admin.equals("S")) {
                                                    Toast.makeText(MenuAdminActivity.this, "Se hizo click en " + p.getNombre(),
                                                            Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent(MenuAdminActivity.this, MiPerfilActivity.class);
                                                    intent.putExtra("ALUMNO", (Serializable) p);
                                                    startActivity(intent);
                                                }
                                                else{
                                                    Toast.makeText(MenuAdminActivity.this,"Campus "+p.getCampus(),
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parent) {

                                    }
                                });


                            }catch(Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //Log.e("Error",error.getMessage());
                    Log.e("Resultado","fallo update "+error);
                }
            }){
                @Override
                public String getBodyContentType(){
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError
                {
                    try{
                        return requestBody == null ? null:requestBody.getBytes("utf-8");
                    }catch(UnsupportedEncodingException uee){
                        VolleyLog.wtf("Codificación no soportada al tratar de conectarse con %s usando %s",
                                requestBody,"utf-8");
                        return null;
                    }
                }
            };
            requestQueue.add(stringRequest);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(lista_alumnos);


    }//Fin del OnCreate()

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0,
            ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            final int pos = viewHolder.getAdapterPosition();
            switch (direction) {
                case ItemTouchHelper.LEFT:

                    alertDialogDelete(lista.get(pos),pos);
                    break;
                case ItemTouchHelper.RIGHT:

                    alertDialogDelete(lista.get(pos),pos);
                    break;
            }

        }
    };

    private void alertDialogDelete(Alumno p, int pos){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("¿Está seguro de eliminar al alumno?");
        final Alumno pp = p;
        final int position = pos;
        alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Vamos a eliminar la evaluación del sistema
                String url_del = "https://sergiosalasarriaran.000webhostapp.com/sotrupc/2020-2/Semana12/eliminar_estudiante.php";
                try{
                    RequestQueue requestQueue = Volley.newRequestQueue(MenuAdminActivity.this);
                    final String requestBody = "correo=" + pp.getCorreo()+"&ciclo="+pp.getCiclo()+"&key=12345";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_del,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    if (response.contains("ELIMINAR") && response.contains("OK")) {
                                        Toast.makeText(MenuAdminActivity.this,
                                                "Se eliminó al alumno "+pp.getNombre()+" "+
                                                        pp.getApellido(),Toast.LENGTH_SHORT).show();
                                        lista.remove(position);
                                        adapter = new AlumnoAdapter(MenuAdminActivity.this,lista);
                                        lista_alumnos.setAdapter(adapter);
                                        adapter.setOnItemClickListener(new AlumnoAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Alumno p) {
                                                Toast.makeText(MenuAdminActivity.this, "Se hizo click en " + p.getNombre(),
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MenuAdminActivity.this,MiPerfilActivity.class);
                                                intent.putExtra("ALUMNO",(Serializable)p);
                                                startActivity(intent);
                                            }
                                        });

                                    } else {
                                        Toast.makeText(MenuAdminActivity.this,
                                                "No se pudo eliminar",
                                                Toast.LENGTH_SHORT).show();
                                        adapter = new AlumnoAdapter(MenuAdminActivity.this,lista);
                                        lista_alumnos.setAdapter(adapter);
                                        adapter.setOnItemClickListener(new AlumnoAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Alumno p) {
                                                Toast.makeText(MenuAdminActivity.this, "Se hizo click en " + p.getNombre(),
                                                        Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(MenuAdminActivity.this,MiPerfilActivity.class);
                                                intent.putExtra("ALUMNO",(Serializable)p);
                                                startActivity(intent);
                                            }
                                        });

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("Error",error.getMessage());
                            Log.e("Resultado","fallo update "+error);
                        }
                    }){
                        @Override
                        public String getBodyContentType(){
                            return "application/json; charset=utf-8";
                        }
                        @Override
                        public byte[] getBody() throws AuthFailureError
                        {
                            try{
                                return requestBody == null ? null:requestBody.getBytes("utf-8");
                            }catch(UnsupportedEncodingException uee){
                                VolleyLog.wtf("Codificación no soportada al tratar de conectarse con %s usando %s",
                                        requestBody,"utf-8");
                                return null;
                            }
                        }
                    };
                    requestQueue.add(stringRequest);
                }catch(Exception ex){
                    ex.printStackTrace();
                }

            }
        });
        alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MenuAdminActivity.this,"No eliminó nada",Toast.LENGTH_SHORT).show();
                adapter = new AlumnoAdapter(MenuAdminActivity.this,lista);
                lista_alumnos.setAdapter(adapter);
                adapter.setOnItemClickListener(new AlumnoAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Alumno p) {
                        Toast.makeText(MenuAdminActivity.this, "Se hizo click en " + p.getNombre(),
                                Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MenuAdminActivity.this,MiPerfilActivity.class);
                        intent.putExtra("ALUMNO",(Serializable)p);
                        startActivity(intent);
                    }
                });
            }
        });

        alert.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miperfil:
                Intent intent2 = new Intent(this, MiPerfilActivity.class);
                startActivity(intent2);
                return true;
            case R.id.menu_cerrar:
                //Al cerrar sesión debemos eliminar el registro del SharedPreferences para no volver a
                //entrar al menú principal sin loguearnos.
                SharedPreferences pref = getSharedPreferences("Usuario.xml", MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString("USUARIO", null);
                editor.putString("ADMIN", null);
                editor.putString("NOMBRE",null);
                editor.commit();
                Intent intent = new Intent(MenuAdminActivity.this, MainActivity.class);
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