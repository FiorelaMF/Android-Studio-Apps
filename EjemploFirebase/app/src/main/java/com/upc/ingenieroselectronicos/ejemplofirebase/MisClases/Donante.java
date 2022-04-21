package com.upc.ingenieroselectronicos.ejemplofirebase.MisClases;

import androidx.annotation.DimenRes;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Donante implements Serializable, Comparable<Donante> {
    private String nombre;
    private String correo;
    private boolean sexof;
    private String fecha;
    private double donacion;
    private String urlfoto;
    private String key;

    //Como esta clase se va a conectar con el RealTime database de Firebase tanto para lectura
    //como para escritura necesitamos OBLIGATORIAMENTE un constructor por defecto:
    public Donante(){

    }
    public Donante(String nombre, String correo, boolean sexof,String fecha,double donacion, String urlfoto){
        this.nombre = nombre;
        this.correo = correo;
        this.sexof = sexof;
        this.fecha = fecha;
        this.donacion= donacion;
        this.urlfoto = urlfoto;
    }
    //GETTERS:

    public String getNombre() {
        return nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public boolean isSexof() {
        return sexof;
    }

    public String getFecha() {
        return fecha;
    }

    public double getDonacion() {
        return donacion;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public String getKey() {
        return key;
    }
    //SETTER:

    public void setKey(String key) {
        this.key = key;
    }

    public void setUrlfoto(String urlfoto) {
        this.urlfoto = urlfoto;
    }

    //El método que compare fechas y me devuelva -1 cuando la fecha es mayor
    @Override
    public int compareTo(Donante obj){
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
