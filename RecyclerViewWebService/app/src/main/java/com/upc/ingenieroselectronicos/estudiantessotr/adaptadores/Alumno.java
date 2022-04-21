package com.upc.ingenieroselectronicos.estudiantessotr.adaptadores;

import java.io.Serializable;

//TODO 21: Crear la clase alumno para ver el listado
public class Alumno implements Serializable {
    private String nombre;
    private String apellido;
    private String codigo;
    private String correo;
    private String carrera;
    private String ciclo;
    private String urlfoto;
    private String cargo;
    private String estado;
    private String campus;

    public Alumno(String nombre, String apellido, String correo, String codigo, String carrera, String ciclo,
                  String urlfoto,String cargo,String estado,String campus) {
        this.nombre=nombre;
        this.apellido = apellido;
        this.carrera = carrera;
        this.codigo = codigo;
        this.ciclo=ciclo;
        this.urlfoto = urlfoto;
        this.correo = correo;
        this.cargo = cargo;
        this.estado = estado;
        this.campus=campus;
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

    public String getCarrera() {
        return carrera;
    }

    public String getCiclo() {
        return ciclo;
    }

    public String getUrlfoto(){
        return this.urlfoto;
    }

    public String getCorreo(){return this.correo;}

    public String getCargo(){return this.cargo;}

    public String getEstado(){return this.estado;}

    public String getCampus(){return this.campus;}

    public void setEstado(String estado){this.estado=estado; }


}
