package com.example.laboratorio2.admin;

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
//import com.example.laboratorio2.Alumno;
//import com.example.laboratorio2.AlumnoAdapter;
//import com.example.laboratorio2.internet.JSONUtils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class MenuAdminActivity extends AppCompatActivity{
    private Button btnVentAulas, btnCasosFiebre, btnRegAlumno, btnEliminarAlumno, btnMatricularAlumno, btnVerAsist, btnProgCurso;
    private EditText edt_usuario, edt_clave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_admin);

        edt_clave = findViewById(R.id.edtMainActivityClave);
        edt_usuario = findViewById(R.id.edtMainActivityUsuario);

        btnVentAulas = findViewById(R.id.btnAdminVentAulas);
        btnCasosFiebre = findViewById(R.id.btnAdminVerFiebre);
        btnRegAlumno = findViewById(R.id.btnAdminRegistrarAlumno);
        btnEliminarAlumno = findViewById(R.id.btnAdminEliminarAlumno);
        btnMatricularAlumno = findViewById(R.id.btnAdminMatricularAlumno);
        btnVerAsist = findViewById(R.id.btnAdminVerAsist);
        btnProgCurso = findViewById(R.id.btnAdminProgramarCurso);

        /*Vamos a esconder el AppBar y anular el giro del teléfono*/
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // --------- VER VENTILACION AULAS ----------
        btnVentAulas.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MenuAdminActivity.this, MenuVentilacionActivity.class);
                intent.putExtra("USUARIO", edt_usuario.getText().toString());
                startActivity(intent);
                finish();

            }
        });

        // --------- VER CASOS FIEBRE ----------
        btnCasosFiebre.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MenuAdminActivity.this, VerFiebreActivity.class);
                //intent.putExtra("USUARIO", edt_usuario.getText().toString());
                startActivity(intent);
                finish();

            }
        });

        // --------- REGISTRAR ALUMNO ----------
        btnRegAlumno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MenuAdminActivity.this, RegistrarAlumnoActivity.class);
                //intent.putExtra("USUARIO", edt_usuario.getText().toString());
                startActivity(intent);
                finish();

            }
        });

        // --------- ELIMINAR ALUMNO ----------
        btnEliminarAlumno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MenuAdminActivity.this, EliminarAlumnoActivity.class);
                //intent.putExtra("USUARIO", edt_usuario.getText().toString());
                startActivity(intent);
                finish();

            }
        });

        // --------- MATRICULAR ALUMNO ----------
        btnMatricularAlumno.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


            }
        });

        // --------- VER MIS ASISTENCIAS ----------
        btnVerAsist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){


            }
        });

        // --------- PROGRAMAR CURSO ----------
        btnProgCurso.setOnClickListener(new View.OnClickListener(){
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
