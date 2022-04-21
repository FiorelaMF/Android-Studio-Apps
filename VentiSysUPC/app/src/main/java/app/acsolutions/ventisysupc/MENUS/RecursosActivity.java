package app.acsolutions.ventisysupc.MENUS;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import app.acsolutions.ventisysupc.R;

public class RecursosActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recursos);
        String[] CARRERAS =    {"Administración","Arquitectura","Biología","Comunicación","Derecho","Diseño","Economía","Gastronomía","Ingeniería Civil","Ingeniería Ambiental","Ingeniería Biomédica","Ingeniería Electrónica","Ingeniería Mecatrónica","Ingeniería de Software","Medicina","Música","Odontología","Psicología","Relaciones Internacionales","Terapia Física","Turismo y Administración"};
        String[] CARRERAS_MO = {"Administración","Arquitectura","Comunicación","Derecho","Diseño","Economía","Gastronomía","Ingeniería Civil","Ingeniería Biomédica","Ingeniería Electrónica","Ingeniería Mecatrónica","Ingeniería de Software","Música","Psicología","Turismo y Administración"};
        String[] CARRERAS_VI = {"Administración","Arquitectura","Biología","Comunicación","Derecho","Ingeniería Civil","Ingeniería Ambiental","Ingeniería de Software","Medicina","Música","Odontología","Psicología","Terapia Física"};
        String[] CARRERAS_SM = {"Administración","Arquitectura","Comunicación","Derecho","Ingeniería Civil","Ingeniería Electrónica","Ingeniería Mecatrónica","Ingeniería de Software","Psicología"};
        String[] CARRERAS_SI = {"Administración","Comunicación","Derecho","Ingeniería Civil","Ingeniería de Software","Relaciones Internacionales"};
    }

    @Override
    public void onClick(View view) {

    }
}