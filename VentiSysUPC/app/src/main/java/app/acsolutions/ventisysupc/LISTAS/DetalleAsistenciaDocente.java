package app.acsolutions.ventisysupc.LISTAS;

public class DetalleAsistenciaDocente {
    private String CODIGO,NOMBRE,INGRESO,FECHA,SALIDA,ESTADO,TEMPE,FOTO;
    public DetalleAsistenciaDocente(String CODIGO,String NOMBRE,String INGRESO,String FECHA,String SALIDA,String ESTADO,String TEMPE, String FOTO){
        this.CODIGO = "Código: " + CODIGO;
        this.NOMBRE = "Nombre: " + NOMBRE;
        this.FECHA = "Fecha: " + FECHA;
        this.ESTADO = "Estado: " + ESTADO;
        this.INGRESO = "Ingreso: " + INGRESO;
        this.SALIDA = "Salida: " + SALIDA;
        this.TEMPE = "Temperatura: " + TEMPE + "°C";
        this.FOTO = FOTO;
    }
    public String getCODIGO() {
        return CODIGO;
    }
    public String getNOMBRE() {
        return NOMBRE;
    }
    public String getFECHA() {
        return FECHA;
    }
    public String getESTADO() {
        return ESTADO;
    }
    public String getINGRESO() {
        return INGRESO;
    }
    public String getSALIDA() { return SALIDA; }
    public String getTEMPE() { return TEMPE; }
    public String getFOTO() {
        return FOTO;
    }
}
