package com.example.sensorapp;

import androidx.annotation.DimenRes;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class Sensor implements Serializable, Comparable<Sensor> {
    private double temperatura;
    private double humedad;
    private int co2;
    private int aforo;
    private String fecha;
    private String hora;


    private String key;


    //Como esta clase se va a conectar con el RealTime database de Firebase tanto para lectura
    //como para escritura necesitamos OBLIGATORIAMENTE un constructor por defecto:
    public Sensor(){

    }
    public Sensor(double temperatura, double humedad, int co2, int aforo, String fecha, String hora){
        this.temperatura = temperatura;
        this.humedad = humedad;
        this.co2 = co2;
        this.fecha = fecha;
        this.aforo = aforo;
        this.hora = hora;
    }
    //GETTERS:


    public double getTemperatura() {
        return temperatura;
    }

    public double getHumedad() {
        return humedad;
    }

    public int getCo2() {
        return co2;
    }

    public int getAforo() {
        return aforo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getHora() {
        return hora;
    }

    public String getKey() {
        return key;
    }

    //El método que compare fechas y me devuelva -1 cuando la fecha es mayor
    @Override
    public int compareTo(Sensor obj){
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        try{
            date = sd.parse(this.fecha);//this.fecha="hola mundo"/17/11/2021
        }catch(ParseException ex){
            ex.printStackTrace();
            return 0;
        }
        long thismilisegundos = date.getTime();
        try{
            date = sd.parse(obj.getFecha());//obj.getFecha()="hola mundo"/17/11/2021
        }catch(ParseException ex){
            ex.printStackTrace();
            return 0;
        }
        long objmilisegundos = date.getTime();
        if(thismilisegundos>objmilisegundos)
            return -1;
        else if(thismilisegundos<objmilisegundos)
            return 1;
        else
            return 0;
    }

}
