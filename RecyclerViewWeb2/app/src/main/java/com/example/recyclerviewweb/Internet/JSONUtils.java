package com.example.recyclerviewweb.Internet;

import android.util.Log;

import com.example.recyclerviewweb.Adaptadores.Alumno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// TODO 14: Creamos los metodos en la clase JSONUtils para que transformen
// un JSON a una lista de una clase que tenemos en JAVA
public class JSONUtils {
    public static List<Alumno> parseJSONAlumno(String json) throws JSONException {
        final String NOMBRE = "nombre";
        final String APELLIDO = "apellido";
        final String CODIGO = "codigo";
        final String CORREO = "correo";
        final String URLFOTO = "urlfoto";
        final String CARRERA = "carrera";
        final String CARGO = "cargo";
        final String CICLO = "ciclo";
        final String ESTADO = "estado";
        final String CAMPUS = "campus";

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

            lista.add(new Alumno(obj.getString(NOMBRE),
                            obj.getString(APELLIDO),
                            obj.getString(CODIGO),
                            obj.getString(CORREO),
                            obj.getString(URLFOTO),
                            obj.getString(CARRERA),
                            obj.getString(CARGO),
                            obj.getString(CICLO),
                            obj.getString(ESTADO),
                            obj.getString(CAMPUS)
                        ));
        }
        return lista;

    }

    public static List<String> devolver_ciclo(List<Alumno> lista) throws JSONException {
        List<String> ciclos = new ArrayList<>();
        ciclos.add(lista.get(0).getCiclo());
        int j = 0;
        for(int i=1;i<lista.size(); i++){
            for(j=0;j<ciclos.size(); j++){
                if (lista.get(i).getCiclo().equals(ciclos.get(j)))
                    break;
            }
            if (j==ciclos.size())
                ciclos.add(lista.get(i).getCiclo());
        }
        Collections.sort(ciclos, Collections.reverseOrder());
        return ciclos;
    }
}
