package com.example.finalito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.finalito.MisClases.Alumno;

public class SegundaActivity extends AppCompatActivity {
    TextView txv1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);
        txv1=findViewById(R.id.reserva);

        Intent rx_intent= getIntent();
        if(rx_intent.hasExtra("MENU")){
            Alumno MENU= (Alumno) rx_intent.getSerializableExtra("MENU");
            txv1.setText("Reserva de "+MENU.getNombre());
        }
    }
}