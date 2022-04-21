package com.example.pc2.MisClases;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CosaUnoConvert {
    public static List<CosaUno> StringToArray(String json) throws JSONException {
        //VARIABLES Q ARROJA EL JSON DEL WEBSERVICE
        String CODIGO="codigo";
        String SECCION="seccion";
        String PC1="pc1";
        String LB1="lb1";
        String EA="ea";
        String PC2="pc2";
        String LB2="lb2";
        String TB="tb";
        String DD="dd";

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
        List<CosaUno> lista= new ArrayList<>();

        //POBLAR LA LISTA DE SALIDA CON LOS OBJETOS
        for(int i=0;i<jsonArray.length();i++){
            JSONObject obj = jsonArray.getJSONObject(i);
            lista.add(new CosaUno(obj.getString(CODIGO),obj.getString(SECCION),obj.getString(PC1),
                    obj.getString(LB1),obj.getString(EA),obj.getString(PC2),obj.getString(LB2),
                    obj.getString(TB),obj.getString(DD)));
        }
        //RETORNAR LISTA
        return lista;
    }
    public static List<String> devolver_seccion(List<CosaUno> lista) throws JSONException {
        List<String> secciones =new ArrayList<>();
        secciones.add(lista.get(0).getSeccion());
        int j=0;
        for(int i=1;i<lista.size();i++){
            for(j=0;j< secciones.size();j++){
                if(lista.get(i).getSeccion().equals(secciones.get(j)))
                    break;
            }
            if(j==secciones.size())
                secciones.add(lista.get(i).getSeccion());
        }
//        Collections.sort(campus,Collections.reverseOrder());
        Collections.sort(secciones);
        return secciones;
    }



}
