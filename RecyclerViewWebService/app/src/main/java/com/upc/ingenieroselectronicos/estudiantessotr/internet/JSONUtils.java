package com.upc.ingenieroselectronicos.estudiantessotr.internet;

import android.util.Log;

import com.upc.ingenieroselectronicos.estudiantessotr.adaptadores.Alumno;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//TODO 25: Crear la clase JSONUtils para convertir la cadena JSON en una lista. 
public class JSONUtils {
    public static List<Alumno> parseJsonAlumno(String json) throws JSONException {
        final String NOMBRE    = "nombre";
        final String APELLIDO  = "apellido";
        final String CODIGO    = "codigo";
        final String CORREO    = "correo";
        final String URLFOTO   = "urlfoto";
        final String CARRERA   = "carrera";
        final String CARGO     = "cargo";
        final String CICLO     = "ciclo";
        final String ESTADO    = "estado";
        final String CAMPUS    = "campus";

        String[] parsedData = null;
        json = json.replace("\\","");//  urlfoto = xxxxx\/ = xxxxx/
        json = json.replace("u00f3","ó");
        json = json.replace("u00fa","ú");
        json = json.replace("u00ed","í");
        json = json.replace("u00e9","é");
        json = json.replace("u00e1","á");

        JSONArray jsonArray = new JSONArray(json);
        parsedData = new String[jsonArray.length()];
        List<Alumno> mis_datos = new ArrayList<>();
        for(int i = 0; i<jsonArray.length();i++){
            JSONObject obj =jsonArray.getJSONObject(i);

            mis_datos.add(new Alumno(obj.getString(NOMBRE),obj.getString(APELLIDO),obj.getString(CORREO),
                    obj.getString(CODIGO),obj.getString(CARRERA),obj.getString(CICLO),obj.getString(URLFOTO),
                    obj.getString(CARGO),obj.getString(ESTADO),obj.getString(CAMPUS)));
        }
        return mis_datos;
    }

    public static List<String> devolver_ciclos(List<Alumno> lista){
        List<String> ciclos = new ArrayList<>();    //Lista de ciclos
        ciclos.add(lista.get(0).getCiclo());        //Agregamos el primer ciclo de la lista.
        int j = 0;
        for(int i = 1;i<lista.size();i++){          //El servicio Web debe entregar los ciclos por orden cronológico
            for(j = 0;j<ciclos.size();j++) {
                if (lista.get(i).getCiclo().equals(ciclos.get(j))){//Si un ciclo de la lista es nuevo
                    break;
                }
            }
            if(j==ciclos.size())
                ciclos.add(lista.get(i).getCiclo());
        }
        Collections.sort(ciclos, Collections.reverseOrder());
        return ciclos;

    }



    public static List<String> parseJsonCiclos(String json) throws JSONException {

        final String CICLO = "ciclo";

        String[] parsedData = null;
        json = json.replace("\\","");
        json = json.replace("u00f3","ó");
        json = json.replace("u00fa","ú");
        json = json.replace("u00ed","í");
        json = json.replace("u00e9","é");
        json = json.replace("u00e1","á");
        json = json.replace("u00bf","¿");
        Log.e("Modificado",json);
        JSONArray jsonArray = new JSONArray(json);

        parsedData = new String[jsonArray.length()];
        Log.e("parsedData",Integer.toString(parsedData.length));
        List<String> my_data = new ArrayList<>();
        for(int i = 0;i<jsonArray.length();i++)
        {
            JSONObject obj = jsonArray.getJSONObject(i);
            my_data.add(new String(obj.getString(CICLO)));
        }
        return my_data;

    }
}
