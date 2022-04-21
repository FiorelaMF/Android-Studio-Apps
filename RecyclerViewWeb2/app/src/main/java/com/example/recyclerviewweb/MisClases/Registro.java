package com.example.recyclerviewweb.MisClases;
// TODO 9: Verificamos si todos los datos están bien para el registro
public class Registro {
    private String nombre;
    private String apellidos;
    private String correo;
    private String clave;
    private String clave_rep;
    private String carrera;
    private String cargo;
    private String codigo;
    private String campus;

    public Registro(String nombre,
                    String apellidos,
                    String correo,
                    String clave,
                    String clave_rep,
                    String carrera,
                    String cargo,
                    String codigo,
                    String campus){
        this.nombre = nombre;   this.apellidos = apellidos;
        this.correo = correo;   this.clave = clave;
        this.clave_rep = clave_rep;     this.carrera = carrera;
        this.cargo = cargo;     this.codigo = codigo;
        this.campus = campus;
    }

    // Verificar si el email es email
    public boolean verificarCorreo(){
        if(this.correo.contains("@")){
            String primero = this.correo.substring(0,this.correo.indexOf("@"));
            String ultimo = this.correo.substring(this.correo.indexOf("@"));
            if(ultimo.contains(".") && primero.length()>=4){
                return true;
            } else{
                return false;
            }

        } else{
            return false;
        }
    }

    // Verificar si las contraseñas son iguales y tiene mas de 5 caracteres
    public boolean verificarClave(){
        if(this.clave.equals(this.clave_rep)){
            if(this.clave.length()>5)
                return true;
            else
                return false;

        } else
            return false;
    }

    //Verificar si el codigo del alumno es de la UPC
    public boolean verificarCodigo(){  //u201714412
        if(this.codigo.charAt(0)=='u' && this.codigo.length()==10){
            String numero = this.codigo.substring(1);
            try{
                int val = Integer.parseInt(numero);
            } catch (Exception ex){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }

    //GETTERS

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public String getClave() {
        return clave;
    }

    public String getClave_rep() {
        return clave_rep;
    }

    public String getCarrera() {
        return carrera;
    }

    public String getCargo() {
        return cargo;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCampus() {
        return campus;
    }
}
