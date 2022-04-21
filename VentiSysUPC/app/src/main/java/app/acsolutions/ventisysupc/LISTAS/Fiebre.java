package app.acsolutions.ventisysupc.LISTAS;

public class Fiebre {
    private String CURSO,FECHA,ALUMNO,CORREO,TEMPE,FOTO;
    public Fiebre(String CURSO,String FECHA,String ALUMNO,String CORREO,String TEMPE, String FOTO){
        this.CURSO = "Curso: " + CURSO;
        this.FECHA = "Fecha: " + FECHA;
        this.ALUMNO = "Alumno: " + ALUMNO;
        this.CORREO = "Correo: " + CORREO;
        this.TEMPE = "Temperatura: " + TEMPE + "°C";
        this.FOTO = FOTO;
    }
    public String getCURSO() {
        return CURSO;
    }
    public String getFECHA() {
        return FECHA;
    }
    public String getALUMNO() {
        return ALUMNO;
    }
    public String getCORREO() {
        return CORREO;
    }
    public String getTEMPE() {
        return TEMPE;
    }
    public String getFOTO() {
        return FOTO;
    }
}
