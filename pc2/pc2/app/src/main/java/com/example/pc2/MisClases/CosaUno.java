package com.example.pc2.MisClases;

import java.io.Serializable;

public class CosaUno implements Serializable {
    //VARIABLES DE LA CLASE
    public String codigo;
    public String seccion;
    public String pc1;
    public String lb1;
    public String ea;
    public String pc2;
    public String lb2;
    public String tb;
    public String dd;


    //CONSTRUCTOR

    public CosaUno(String codigo, String seccion, String pc1, String lb1, String ea, String pc2, String lb2, String tb, String dd) {
        this.codigo = codigo;
        this.seccion = seccion;
        this.pc1 = pc1;
        this.lb1 = lb1;
        this.ea = ea;
        this.pc2 = pc2;
        this.lb2 = lb2;
        this.tb = tb;
        this.dd = dd;
    }


    //GETTERS

    public String getCodigo() {
        return codigo;
    }

    public String getSeccion() {
        return seccion;
    }

    public String getPc1() {
        return pc1;
    }

    public String getLb1() {
        return lb1;
    }

    public String getEa() {
        return ea;
    }

    public String getPc2() {
        return pc2;
    }

    public String getLb2() {
        return lb2;
    }

    public String getTb() {
        return tb;
    }

    public String getDd() {
        return dd;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public void setPc1(String pc1) {
        this.pc1 = pc1;
    }

    public void setLb1(String lb1) {
        this.lb1 = lb1;
    }

    public void setEa(String ea) {
        this.ea = ea;
    }

    public void setPc2(String pc2) {
        this.pc2 = pc2;
    }

    public void setLb2(String lb2) {
        this.lb2 = lb2;
    }

    public void setTb(String tb) {
        this.tb = tb;
    }

    public void setDd(String dd) {
        this.dd = dd;
    }
}
