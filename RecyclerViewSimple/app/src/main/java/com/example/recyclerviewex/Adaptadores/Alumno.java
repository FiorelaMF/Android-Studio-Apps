package com.example.recyclerviewex.Adaptadores;
// TODO 1: Crear la clase Alumno que representa la información base a mostrar en el RecyclerView
public class Alumno {
    private String nombre;
    private String codigo;
    private String carrera;
    private boolean sexof;
    private double ponderado;

    public Alumno(String nombre, String codigo, String carrera, boolean sexo, double ponderado){
        this.nombre = nombre;
        this.codigo = codigo;
        this.carrera = carrera;
        this.sexof = sexo;
        this.ponderado = ponderado;
    }

    // GETTERS
    public String getNombre(){ return nombre; }
    public String getCodigo(){ return codigo; }
    public String getCarrera(){ return carrera; }
    public boolean isSexof(){ return sexof; }
    public double getPonderado(){ return ponderado; }

    // SETTERS

}
