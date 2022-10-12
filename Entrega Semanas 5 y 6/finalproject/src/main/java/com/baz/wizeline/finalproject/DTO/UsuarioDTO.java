package com.baz.wizeline.finalproject.DTO;

import com.baz.wizeline.finalproject.UTILS.Utils;

public class UsuarioDTO {
    public UsuarioDTO(){}

    public UsuarioDTO(String nombre, String contrasenia){
        this.nombre = nombre;
        this.contrasenia = contrasenia;
    }
    private int id = Utils.randomAccountNumber();
    private String nombre;
    private String contrasenia;

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