package com.example.pc2.MisClases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CosaDosConvert {
    public static List<CosaDos> StringToArray(String json) throws JSONException {
        //VARIABLES Q ARROJA EL JSON DEL WEBSERVICE
        final String CAMPUS="campus";
//        final String CURSO="curso";
//        final String SECCION="seccion";
//        final String FECHA="fecha";
//        final String IDSESION="idsesion";
//        final String ASISTENTES="asistentes";

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
        List<CosaDos> lista= new ArrayList<>();

        //POBLAR LA LISTA DE SALIDA CON LOS OBJETOS
        for(int i=0;i<jsonArray.length();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            lista.add(new CosaDos(obj.getString(CAMPUS)));
        }
        //RETORNAR LISTA
        return lista;

    }
}
