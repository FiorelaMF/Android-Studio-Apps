package com.example.pc2.MisClases;

import java.io.Serializable;

public class CosaDos implements Serializable {
    //VARIABLES DE LA CLASE
    private String campus;

    //CONSTRUCTOR
    public CosaDos(String campus) {
        this.campus = campus;
    }

    //GETTERS

    public String getCampus() {
        return campus;
    }
}
