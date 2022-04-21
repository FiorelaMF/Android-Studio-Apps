package com.example.demologin;

import android.widget.EditText;

public class RevisarRegistro {
    private String correo;
    private String clave;
    private String nombre;

    public RevisarRegistro(EditText usuario, EditText clave, EditText nombre){
        this.correo = usuario.getText().toString();
        this.clave = clave.getText().toString();
        this.nombre = nombre.getText().toString();
    }

    public boolean isCorreoOK(){
        if(this.correo.contains("@") && this.correo.contains(".")){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean isClaveOK(){
        if(this.clave.length()>5){
            return true;
        }
        else{
            return false;
        }
    }
    public boolean isNombreOK(){
        if(this.nombre.length()>4 && this.nombre.contains(" ")){
            return true;
        }
        else{
            return false;
        }
    }
    //GETTERS:


    public String getCorreo() {
        return correo;
    }

    public String getClave() {
        return clave;
    }

    public String getNombre() {
        return nombre;
    }
}
