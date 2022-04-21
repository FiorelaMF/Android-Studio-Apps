package com.upc.ingenieroselectronicos.ejemplofirebase;

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
import com.upc.ingenieroselectronicos.ejemplofirebase.MisClases.Donante;
import com.upc.ingenieroselectronicos.ejemplofirebase.MisClases.DonanteAdapter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
//TODO 12: Cargamos la lista de donantes en el RecylerView
public class ListaDonantesActivity extends AppCompatActivity {
    private RecyclerView recyclerViewDonantes;
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private List<Donante> listaDonantes = new ArrayList<>();
    private DatabaseReference mDbRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_donantes);

        recyclerViewDonantes = findViewById(R.id.rcvListaDonantesActiviyLista);
        mDbRef = mDatabase.getReference("Donadores/Datos");
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerViewDonantes.setLayoutManager(layout);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Ponemos un evento que escuche los datos que cambian en el Real TIme data base.
        //Este evento siempre se ejecuta la primera vez que cargas la actividad
        mDbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listaDonantes.clear();
                for(DataSnapshot child: snapshot.getChildren()){
                    Donante obj = child.getValue(Donante.class);
                    listaDonantes.add(obj);
                }
                Collections.sort(listaDonantes);
                DonanteAdapter adapter = new DonanteAdapter(ListaDonantesActivity.this,listaDonantes);
                recyclerViewDonantes.setAdapter(adapter);
                adapter.setOnItemClickListener(new DonanteAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Donante p) {
                        Intent intent = new Intent(ListaDonantesActivity.this,EditarDonanteActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("DATOS",p);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}