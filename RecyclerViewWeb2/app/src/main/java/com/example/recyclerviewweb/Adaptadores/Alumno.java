package com.example.recyclerviewweb.Adaptadores;

import java.io.Serializable;

// TODO 12: Crea clase Alumno para obtener la info de los
//  alumnos de la cadena JSON
public class Alumno implements Serializable {
    private String nombre;
    private String apellido;
    private String codigo;
    private String correo;
    private String urlfoto;
    private String carrera;
    private String cargo;
    private String ciclo;
    private String estado;
    private String campus;

    public Alumno(String nombre,
                String apellido,
                String codigo,
                String correo,
                String urlfoto,
                String carrera,
                String cargo,
                String ciclo,
                String estado,
                String campus){
        this.nombre = nombre;   this.apellido = apellido;
        this.codigo = codigo;   this.correo = correo;
        this.urlfoto = urlfoto;     this.carrera = carrera;
        this.cargo = cargo;     this.ciclo = ciclo;
        this.estado = estado;   this.campus = campus;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCorreo() {
        return correo;
    }

    public String getUrlfoto() {
        return urlfoto;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getCargo() {
        return cargo;
    }

    public String getCiclo() {
        return ciclo;
    }

    public String getEstado() {
        return estado;
    }

    public String getCampus() {
        return campus;
    }
}
