package com.example.recyclerviewex;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.recyclerviewex.Adaptadores.Alumno;
import com.example.recyclerviewex.Adaptadores.AlumnoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

//TODO 6: Cargamos los datos en el RecyclerView
public class MainActivity extends AppCompatActivity {
    private RecyclerView rcvAlumnos;
    private FloatingActionButton fab;
    private String nombres[] = {"Víctor Meneses","Andrea Acosta","Pierina Frisancho",
                    "Enrique Espinoza", "Josué Bernal", "Noelia Rodriguez"};
    private boolean sexo[]={false,true,true,false,false,true};
    private String codigos[] = {"u201518763","u201718923","u201627478",
                    "u201916738","u201828702","u201728449"};
    private String carreras[] = new String[codigos.length];
    private double ponderados[] = new double[sexo.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //quitar el AppBar
        getSupportActionBar().hide();

        rcvAlumnos = findViewById(R.id.rcvMainActivityAlumnos);
        fab = findViewById(R.id.fabMainActivityBoton);
        //Creamos la lista de alumnos
        List<Alumno> alumnos = new ArrayList<>();
        for(int i=0;i<codigos.length;i++){
            carreras[i]="Ing. Electrónica";
            ponderados[i]=Math.random()*20;
            Alumno p = new Alumno(nombres[i],codigos[i],carreras[i],sexo[i],ponderados[i]);
            alumnos.add(p);
        }
        //El GridLAyoutManager es para dividir la pantalla en columnas
        //GridLayoutManager layout = new GridLayoutManager(this,3,GridLayoutManager.VERTICAL,false);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        rcvAlumnos.setLayoutManager(layout);

        AlumnoAdapter adapter = new AlumnoAdapter(alumnos);
        rcvAlumnos.setAdapter(adapter);  // Se muestran los datos en el RecyclerView

        //Configurar los clicks que se hacen a cada alumno
        adapter.setOnItemClickListener(new AlumnoAdapter.OnItemClickListener(){
            @Override
            public void onClickRecyclerView(Alumno p){
                Toast.makeText(MainActivity.this,"Se hizo click sobre el alumno "+p.getNombre(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Snackbar snack = Snackbar.make(fab,"Click en el botón fab",Snackbar.LENGTH_LONG);
                snack.show();
                snack.setAction("BORRAR", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Eliminando elemento", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}

