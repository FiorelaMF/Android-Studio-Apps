package com.example.laboratorio2.adaptadores;
import java.io.Serializable;

public class Ventilacion implements Serializable {
    private String fecha;
    private String asistentes;

    public Ventilacion(String fecha, String asistentes){
        this.fecha = fecha;     this.asistentes = asistentes;
    }

    //GETTERS
    public String getFecha() {
        return fecha;
    }

    public String getAsistentes() {
        return asistentes;
    }
}
