package com.example.laboratorio2.adaptadores;

import java.io.Serializable;

public class Asistencia implements Serializable {
    private String fecha, hIngreso, hSalida, estado, temp;

    public Asistencia(String fecha, String hIngreso, String hSalida, String estado, String temp){
        this.fecha = fecha;     this.hIngreso = hIngreso;
        this.hSalida = hSalida;     this.estado = estado;
        this.temp = temp;
    }

    //GETTERS

    public String getFecha() {
        return fecha;
    }

    public String gethIngreso() {
        return hIngreso;
    }

    public String gethSalida() {
        return hSalida;
    }

    public String getEstado() {
        return estado;
    }

    public String getTemp() {
        return temp;
    }
}
