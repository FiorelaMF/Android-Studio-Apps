package com.example.laboratorio2.internet;

import android.util.Log;

import com.example.laboratorio2.adaptadores.Alumno;
import com.example.laboratorio2.adaptadores.Asistencia;
import com.example.laboratorio2.adaptadores.Fiebre;
import com.example.laboratorio2.adaptadores.Ventilacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Creamos los metodos en la clase JSONUtils para que transformen
// un JSON a una lista de una clase que tenemos en JAVA
public class JSONUtils {

    // ****************** VER VENTILACION **************************
    public static List<Ventilacion> parseJSONVentilacion(String json) throws JSONException {
        final String FECHA = "fecha";
        final String ASISTENTES = "asistentes";

        json = json.replace("\\","");
        json = json.replace("u00f3","ó");
        json = json.replace("u00fa","ú");
        json = json.replace("u00ed","í");
        json = json.replace("u00e9","é");
        json = json.replace("u00e1","á");

        JSONArray jsonArray;

        //Verificar que el string este en formato JSON
        try{
            jsonArray = new JSONArray(json);
        } catch (JSONException e){
            //e.printStackTrace();
            return null;
        }

        List<Ventilacion> lista = new ArrayList<>();
        Log.e("Total elem",Integer.toString(jsonArray.length()));
        for(int i=0;i<jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);

            lista.add(new Ventilacion("Fecha: "+obj.getString(FECHA),
                    "Asistentes: "+obj.getString(ASISTENTES)
            ));
        }
        return lista;

    }

    public static List<String> devolver_ventilacion(List<Ventilacion> lista) throws JSONException {
        List<String> ventilaciones = new ArrayList<>();
        ventilaciones.add(lista.get(0).getFecha());
        int j = 0;
        for(int i=1;i<lista.size(); i++){
            for(j=0;j<ventilaciones.size(); j++){
                if (lista.get(i).getFecha().equals(ventilaciones.get(j)))
                    break;
            }
            if (j==ventilaciones.size())
                ventilaciones.add(lista.get(i).getFecha());
        }
        Collections.sort(ventilaciones, Collections.reverseOrder());
        return ventilaciones;
    }

    // ****************** VER CASOS FIEBRE **************************
    public static List<Fiebre> parseJSONFiebre(String json) throws JSONException {
        final String CURSO = "curso";
        final String FECHA = "fecha";
        final String NOMBRE_COMP = "nombre_comp";
        final String CODIGO = "codigo";
        final String TEMPERATURA = "temperatura";

        json = json.replace("\\","");
        json = json.replace("u00f3","ó");
        json = json.replace("u00fa","ú");
        json = json.replace("u00ed","í");
        json = json.replace("u00e9","é");
        json = json.replace("u00e1","á");

        JSONArray jsonArray;

        //Verificar que el string este en formato JSON
        try{
            jsonArray = new JSONArray(json);
        } catch (JSONException e){
            //e.printStackTrace();
            return null;
        }

        List<Fiebre> lista = new ArrayList<>();
        Log.e("Total elem",Integer.toString(jsonArray.length()));
        for(int i=0;i<jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);

            lista.add(new Fiebre("Curso: "+obj.getString(CURSO), "Fecha: "+obj.getString(FECHA),
                                "Alumno: "+obj.getString(NOMBRE_COMP), "Código: "+obj.getString(CODIGO),
                    "Temperatura: "+obj.getString(TEMPERATURA)
            ));
        }
        return lista;
    }
    public static List<String> devolver_fiebre(List<Fiebre> lista) throws JSONException {
        List<String> fiebres = new ArrayList<>();
        fiebres.add(lista.get(0).getFecha());
        int j = 0;
        for(int i=1;i<lista.size(); i++){
            for(j=0;j<fiebres.size(); j++){
                if (lista.get(i).getFecha().equals(fiebres.get(j)))
                    break;
            }
            if (j==fiebres.size())
                fiebres.add(lista.get(i).getFecha());
        }
        Collections.sort(fiebres, Collections.reverseOrder());
        return fiebres;
    }

    // ****************** VER ALUMNO PARA ELIMINAR **************************
    public static List<Alumno> parseJSONAlumno(String json) throws JSONException {
        final String NOMBRE = "nombre";
        final String APELLIDOP = "apellidop";
        final String APELLIDOM = "apellidom";
        final String CARRERA = "carrera";
        final String SEXO = "sexo";

        json = json.replace("\\","");
        json = json.replace("u00f3","ó");
        json = json.replace("u00fa","ú");
        json = json.replace("u00ed","í");
        json = json.replace("u00e9","é");
        json = json.replace("u00e1","á");

        JSONArray jsonArray;

        //Verificar que el string este en formato JSON
        try{
            jsonArray = new JSONArray(json);
        } catch (JSONException e){
            //e.printStackTrace();
            return null;
        }

        List<Alumno> lista = new ArrayList<>();
        Log.e("Total elem",Integer.toString(jsonArray.length()));
        for(int i=0;i<jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);

            lista.add(new Alumno("Nombre: "+obj.getString(NOMBRE), "Apellido paterno: "+obj.getString(APELLIDOP),
                    "Apellido materno: "+obj.getString(APELLIDOM), "Carrera: "+obj.getString(CARRERA),
                    "Sexo: "+obj.getString(SEXO)
            ));
        }
        return lista;
    }
    public static List<String> devolver_alumno(List<Alumno> lista) throws JSONException {
        List<String> alumnos = new ArrayList<>();
        alumnos.add(lista.get(0).getNombre());
        int j = 0;
        for(int i=1;i<lista.size(); i++){
            for(j=0;j<alumnos.size(); j++){
                if (lista.get(i).getNombre().equals(alumnos.get(j)))
                    break;
            }
            if (j==alumnos.size())
                alumnos.add(lista.get(i).getNombre());
        }
        Collections.sort(alumnos, Collections.reverseOrder());
        return alumnos;
    }


    // ================================= CLIENTE =========================
    // ****************** VER ALUMNO PARA ELIMINAR **************************
    public static List<Asistencia> parseJSONAsistencia(String json) throws JSONException {
        final String FECHA = "fecha";
        final String HINGRESO = "ingreso";
        final String HSALIDA = "salida";
        final String ESTADO = "estado";
        final String TEMP = "temperatura";

        json = json.replace("\\","");
        json = json.replace("u00f3","ó");
        json = json.replace("u00fa","ú");
        json = json.replace("u00ed","í");
        json = json.replace("u00e9","é");
        json = json.replace("u00e1","á");

        JSONArray jsonArray;

        //Verificar que el string este en formato JSON
        try{
            jsonArray = new JSONArray(json);
        } catch (JSONException e){
            //e.printStackTrace();
            return null;
        }

        List<Asistencia> lista = new ArrayList<>();
        Log.e("Total elem",Integer.toString(jsonArray.length()));
        for(int i=0;i<jsonArray.length(); i++){
            JSONObject obj = jsonArray.getJSONObject(i);

            lista.add(new Asistencia("Fecha: "+obj.getString(FECHA), "Ingreso: "+obj.getString(HINGRESO),
                    "Salida: "+obj.getString(HSALIDA), obj.getString(ESTADO),
                    "Temperatura: "+obj.getString(TEMP)
            ));
        }
        return lista;
    }/*
    public static List<String> devolver_asistencia(List<Asistencia> lista) throws JSONException {
        List<String> asistencias = new ArrayList<>();
        alumnos.add(lista.get(0).getNombre());
        int j = 0;
        for(int i=1;i<lista.size(); i++){
            for(j=0;j<alumnos.size(); j++){
                if (lista.get(i).getNombre().equals(alumnos.get(j)))
                    break;
            }
            if (j==alumnos.size())
                alumnos.add(lista.get(i).getNombre());
        }
        Collections.sort(alumnos, Collections.reverseOrder());
        return alumnos;
    }*/

}

