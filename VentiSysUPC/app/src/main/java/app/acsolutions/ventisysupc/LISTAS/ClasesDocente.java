package app.acsolutions.ventisysupc.LISTAS;

public class ClasesDocente {
    private String FECHA,CURSO,SECCION;
    public ClasesDocente(String FECHA,String CURSO,String SECCION){
        this.FECHA = "Fecha: " + FECHA;
        this.CURSO = "Curso: " + CURSO;
        this.SECCION = "Sección: " + SECCION;
    }
    public String getFECHA() {
        return FECHA;
    }
    public String getCURSO() {
        return CURSO;
    }
    public String getSECCION() {
        return SECCION;
    }
}
