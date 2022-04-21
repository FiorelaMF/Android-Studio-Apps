package app.acsolutions.ventisysupc.LISTAS;

import android.util.Log;

public class Asistencias {
    private String TIPO,NOMBRE,INGRESO,SALIDA,FOTO,ESTADO;
    public Asistencias(String TIPO,String NOMBRE,String INGRESO,String SALIDA,String FOTO,String ESTADO){
        this.TIPO = TIPO;
        String pronom = "";
        if (TIPO.equals("DOCEN")){ pronom = "Docente: "; }
        else if(TIPO.equals("USER")){ pronom = "Alumno: "; }
        this.NOMBRE = pronom + NOMBRE;
        this.INGRESO = "Ingreso: " + INGRESO;
        this.SALIDA = "Salida: " + SALIDA;
        this.ESTADO = "Estado: " + ESTADO;
        this.FOTO = FOTO;
    }
    public String getTIPO() {
        return TIPO;
    }
    public String getNOMBRE() {
        return NOMBRE;
    }
    public String getINGRESO() {
        return INGRESO;
    }
    public String getSALIDA() {
        return SALIDA;
    }
    public String getFOTO() {
        return FOTO;
    }
    public String getESTADO() {
        return ESTADO;
    }
}
