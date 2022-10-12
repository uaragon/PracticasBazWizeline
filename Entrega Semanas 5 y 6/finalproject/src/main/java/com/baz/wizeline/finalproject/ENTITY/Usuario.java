package com.baz.wizeline.finalproject.ENTITY;

import com.baz.wizeline.finalproject.UTILS.Utils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class Usuario {

    public Usuario(){}

    public Usuario(String nombre, String contrasenia){
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }
    @Id
    private int id = Utils.randomAccountNumber();
    private String nombre;
    private String contrasenia;
    private String correo;

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}