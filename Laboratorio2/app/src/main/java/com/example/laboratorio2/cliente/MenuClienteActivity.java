package com.example.laboratorio2.cliente;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.laboratorio2.MainActivity;
//import com.example.laboratorio2.MiPerfilActivity;
import com.example.laboratorio2.R;
import com.example.laboratorio2.admin.MenuAdminActivity;
import com.example.laboratorio2.admin.MenuVentilacionActivity;
//import com.example.laboratorio2.Alumno;
//import com.example.laboratorio2.AlumnoAdapter;
//import com.example.laboratorio2.internet.JSONUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MenuClienteActivity extends AppCompatActivity {
    private Button btnVerAsist, btnVerVent, btnMisDatos, btnVerAsistMisClases;
    private EditText edt_usuario;
    private String codigo;
    private String[] aux;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_cliente);

        edt_usuario = findViewById(R.id.edtMainActivityUsuario);

        btnVerAsist = findViewById(R.id.btnClienteVerAsist);
        btnVerVent = findViewById(R.id.btnClienteVerVent);
        btnMisDatos = findViewById(R.id.btnClienteMisDatos);
        btnVerAsistMisClases = findViewById(R.id.btnClienteAsistMisClases);

        aux =  edt_usuario.getText().toString().split("@",1);
        codigo = aux[0];

        /*Vamos a esconder el AppBar y anular el giro del teléfono*/
        //getSupportActionBar().hide();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Bienvenida
        SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);
        if (pref.contains("NOMBRE"))
            getSupportActionBar().setTitle("Bienvenido/a "+pref.getString("NOMBRE",""));

        // Reconocer que sea Alumno o Docente
        if (pref.contains("ALUMNO"))
            btnVerAsistMisClases.setVisibility(View.GONE);

        // --------- VER MIS ASISTENCIAS ----------
        btnVerAsist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MenuClienteActivity.this, MisAsistenciasActivity.class);
                intent.putExtra("USUARIO", codigo);
                startActivity(intent);
                finish();

            }
        });

        // --------- VER VENTILACION ----------
        btnVerVent.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


            }
        });

        // --------- VER MIS DATOS ----------
        btnMisDatos.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


            }
        });

        // --------- VER LAS ASISTENCIAS DE MIS CLASES (DOCENTE ONLY) ----------
        btnVerAsistMisClases.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


            }
        });


    }


    // ============================ OPCIONES MENU ============================
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
                Intent intent = new Intent(MenuClienteActivity.this, MainActivity.class);
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
