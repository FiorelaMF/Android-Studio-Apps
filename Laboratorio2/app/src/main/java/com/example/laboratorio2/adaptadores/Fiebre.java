package com.example.laboratorio2.adaptadores;

import java.io.Serializable;

public class Fiebre implements Serializable {
    private String curso, fecha, nombre_comp, codigo, temperatura;

    public Fiebre(String curso, String fecha, String nombre_comp, String codigo, String temperatura){
        this.curso = curso;     this.fecha = fecha;
        this.nombre_comp = nombre_comp;     this.codigo = codigo;
        this.temperatura = temperatura;
    }

    //GETTERS

    public String getCurso() {
        return curso;
    }

    public String getFecha() {
        return fecha;
    }

    public String getNombre_comp() {
        return nombre_comp;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTemperatura() {
        return temperatura;
    }
}
