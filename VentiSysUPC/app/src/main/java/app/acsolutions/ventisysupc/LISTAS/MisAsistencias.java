package app.acsolutions.ventisysupc.LISTAS;

public class MisAsistencias {
    private String ESTADO,FECHA,INGRESO,SALIDA,TEMPERATURA;
    public MisAsistencias(String ESTADO,String FECHA,String INGRESO,String SALIDA,String TEMPERATURA){
        this.ESTADO = "Estado: " + ESTADO;
        this.FECHA = "Fecha: " + FECHA;
        this.INGRESO = "Ingreso: " + INGRESO;
        this.SALIDA = "Salida: " + SALIDA;
        this.TEMPERATURA = "Temperatura: " + TEMPERATURA;
    }
    public String getESTADO() {
        return ESTADO;
    }
    public String getFECHA() {
        return FECHA;
    }
    public String getINGRESO() {
        return INGRESO;
    }
    public String getSALIDA() {
        return SALIDA;
    }
    public String getTEMPERATURA() {
        return TEMPERATURA;
    }
}
