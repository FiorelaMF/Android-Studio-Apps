package app.acsolutions.ventisysupc.LISTAS;

public class Ventilacion {
    private String CURSO,FECHA,ASIST;
    public Ventilacion(String CURSO,String FECHA,String ASIST){
        this.CURSO = "Curso: " + CURSO;
        this.FECHA = "Fecha: " + FECHA;
        this.ASIST = "Asistentes: " + ASIST;
    }
    public String getCURSO() {
        return CURSO;
    }
    public String getFECHA() {
        return FECHA;
    }
    public String getASIST() {
        return ASIST;
    }
}
