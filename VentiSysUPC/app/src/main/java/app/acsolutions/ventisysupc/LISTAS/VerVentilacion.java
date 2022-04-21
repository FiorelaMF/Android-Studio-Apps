package app.acsolutions.ventisysupc.LISTAS;

public class VerVentilacion {
    private String FECHA,CURSO;
    public VerVentilacion(String FECHA,String CURSO){
        this.FECHA = "Fecha: " + FECHA;
        this.CURSO = "Curso: " + CURSO;
    }
    public String getFECHA() {
        return FECHA;
    }
    public String getCURSO() {
        return CURSO;
    }
}
