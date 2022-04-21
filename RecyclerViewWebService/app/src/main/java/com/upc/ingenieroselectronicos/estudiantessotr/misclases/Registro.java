package com.upc.ingenieroselectronicos.estudiantessotr.misclases;
//TODO 16: Crear la clase registro para verificar el registro de usuarios:
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

    public Registro(String nombre,String apellidos,String correo,String clave,String clave_rep, String carrera,
                        String cargo,String codigo,String campus){
            this.nombre=nombre;
            this.apellidos=apellidos;
            this.correo = correo;
            this.clave = clave;
            this.clave_rep = clave_rep;
            this.carrera = carrera;
            this.cargo = cargo;
            this.codigo = codigo;
            this.campus = campus;
    }

    /*Verificar que el correo corresponda a un formato correcto*/
    public boolean verificarCorreo(){
       if(this.correo.contains("@") ){
            String primero = this.correo.substring(0,this.correo.indexOf("@"));
            String ultimo = this.correo.substring(this.correo.indexOf("@"));
            if(ultimo.contains(".") && primero.length()>4){
                 return true;
            }
            else return false;
       }
       else
            return false;
    }

    /*Verificar que las contraseñas coincidan*/
    public boolean verificarClaves(){
        if(this.clave.length()>5){
             if(this.clave.equals(this.clave_rep))
                    return true;
             else
                    return false;
        }
        else
                return false;
        }

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

        public String getCampus() {return campus;}

}
