package com.example.laboratorio2.adaptadores;
import java.io.Serializable;

public class Alumno implements Serializable {
    private String nombre, apellidop, apellidom, carrera, sexo;

    public Alumno(String nombre, String apellidop, String apellidom, String carrera, String sexo){
        this.nombre = nombre;   this.apellidop = apellidop;
        this.apellidom = apellidom; this.carrera = carrera;
        this.sexo = sexo;
    }

    //GETTERS

    public String getNombre() {
        return nombre;
    }

    public String getApellidop() {
        return apellidop;
    }

    public String getApellidom() {
        return apellidom;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getSexo() {
        return sexo;
    }
}
