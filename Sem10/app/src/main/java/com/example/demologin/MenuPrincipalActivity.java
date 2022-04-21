package com.example.demologin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MenuPrincipalActivity extends AppCompatActivity{
    private TextView txvTextoMenuPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        txvTextoMenuPrincipal = findViewById(R.id.txvMenuPrincipalActivityBienvenida);

        SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);
        String nombre = pref.getString("NOMBRE",null);
        String facultad = pref.getString("FACULTAD",null);

        txvTextoMenuPrincipal.setText("Bienvenido "+nombre+"\nFacultad: "+facultad);
    }

    // TODO 15: Agregar el menu AppBar de MenuPrincipalActivity
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        switch (id){
            case R.id.menuPrincipalCerrar:
                SharedPreferences pref = getSharedPreferences("usuario.xml",MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putInt("LOGIN",0);
                editor.commit();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.menuPrincipalSalir:
                finishAffinity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
