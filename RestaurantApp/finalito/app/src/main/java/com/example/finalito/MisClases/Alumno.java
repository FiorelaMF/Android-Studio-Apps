package com.example.finalito.MisClases;

import java.io.Serializable;

public class Alumno implements Serializable {
    public String nombre;
    public String precio;
    public String disponibles;

    public Alumno(String nombre, String precio, String disponibles) {
        this.nombre = nombre;
        this.precio = precio;
        this.disponibles = disponibles;
    }

    public String getNombre() {
        return nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public String getDisponibles() {
        return disponibles;
    }
}
