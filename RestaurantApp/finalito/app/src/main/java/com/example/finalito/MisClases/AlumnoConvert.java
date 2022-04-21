package com.example.finalito.MisClases;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlumnoConvert {
    public static List<Alumno> StringToArray(String json)throws JSONException{
        //VARIABLES Q ARROJA EL JSON DEL WEBSERVICE
        String NOMBRE="nombre";
        String PRECIO="precio";
        String DISPONIBLES="menus_disp";

        //CONVERSION DE VALORES RAROS
        json=json.replace("\\","");
        json=json.replace("u00f3","ó");
        json=json.replace("u00fa","ú");
        json=json.replace("u00ed","í");
        json=json.replace("u00e9","é");
        json=json.replace("u00e1","á");
        json=json.replace("u00f1","ñ");

        //CONVIERTO EL STRING A ARRAY
        JSONArray jsonArray= new JSONArray(json);

        ///LISTA DE OBJETOS DE SALIDA
        List<Alumno> lista= new ArrayList<>();

        //POBLAR LA LISTA DE SALIDA CON LOS OBJETOS
        for(int i=0;i<jsonArray.length();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            lista.add(new Alumno(obj.getString(NOMBRE),obj.getString(PRECIO),obj.getString(DISPONIBLES)));
        }
        //RETORNAR LISTA
        return lista;
    }

}
