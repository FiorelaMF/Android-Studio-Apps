package com.example.laboratorio2.MisClases;

public class Registro {
    private String codigo;
    private String nombre;
    private String apellidop;
    private String apellidom;
    private String carrera;
    private String sexo;
    private String campus;
    private String estado;

    public Registro(String codigo, String nombre,String apellidop,String apellidom,
                    String carrera, String sexo, String campus, String estado){
        this.codigo = codigo;
        this.nombre=nombre;
        this.apellidop=apellidop;
        this.apellidom=apellidom;
        this.carrera = carrera;
        this.sexo = sexo;
        this.campus = campus;
        this.estado = estado;
    }

    /*Verificar que el correo corresponda a un formato correcto*/

    /*Verificar el código del alumno UPC*/
    public boolean verificarCodigo(){
        if(this.codigo.charAt(0)=='u' && this.codigo.length()==10 ){
            String numero = this.codigo.substring(1,this.codigo.length());
            try{
                int val = Integer.parseInt(numero);
                return true;
            }catch(Exception ex){
                return false;
            }
        }
        else
            return false;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidop() {
        return apellidop;
    }

    public String getApellidom() {
        return apellidom;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getSexo() {
        return sexo;
    }

    public String getCampus() {
        return campus;
    }

    public String getEstado() {
        return estado;
    }
}
