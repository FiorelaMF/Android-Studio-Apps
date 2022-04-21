package com.example.sensorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.sensorapp.Sensor;
import com.example.sensorapp.SensorAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MonitoreoActivity extends AppCompatActivity {
    private RecyclerView recyclerViewSensores;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private List<Sensor> listaSensores = new ArrayList<>();
    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoreo);

        recyclerViewSensores = findViewById(R.id.rcvListaDatos);
        mDbRef = mDatabase.getReference("SOTR/Sensores");
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerViewSensores.setLayoutManager(layout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Ponemos un evento que escuche los datos que cambian en el Real TIme data base.
        //Este evento siempre se ejecuta la primera vez que cargas la actividad
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaSensores.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Sensor obj = child.getValue(Sensor.class);
                    listaSensores.add(obj);
                }
                Collections.sort(listaSensores);
                SensorAdapter adapter = new SensorAdapter(MonitoreoActivity.this, listaSensores);
                recyclerViewSensores.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
