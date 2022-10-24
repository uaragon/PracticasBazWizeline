package com.baz.wizeline.javamaven.model;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UsuariosTest {
    public static final Logger logger = LoggerFactory.getLogger(UsuariosTest.class);
    @Test
    public void DadoUnUsuarioNuevo_CuandoSeAgregaNombre_DevolverNombreAgregado() {

        UserDTO userDTO = new UserDTO();
        List<UserDTO> usuarios = new ArrayList<>();
        usuarios.add(new UserDTO("urbano","12345"));
        logger.info("Usuario : "+userDTO);
        logger.info("Usuario 2: "+usuarios.contains("password"));

        logger.info("Usuario : "+usuarios.size());
        userDTO.setName("Luisa");
        logger.info("Usuario : "+userDTO);
        userDTO.setName("NUEVO PAIS");
        String nombre = userDTO.getName();
        logger.info("Usuario : "+userDTO.getName());
        assertEquals ("NUEVO PAIS", nombre);
    }
}
