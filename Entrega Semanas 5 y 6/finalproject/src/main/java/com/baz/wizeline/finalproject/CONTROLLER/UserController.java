package com.baz.wizeline.finalproject.CONTROLLER;

import com.baz.wizeline.finalproject.DTO.ResponseDTO;
import com.baz.wizeline.finalproject.DTO.UsuarioDTO;
import com.baz.wizeline.finalproject.SERVICE.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/operaciones.con.usuarios")
public class UserController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserServiceImpl userServiceImpl;

    /* API con cada endpoint de tipo: POST*/
    @PostMapping("/create")
    public ResponseEntity<?> createUser(
            @RequestBody
                    UsuarioDTO usuario){

        return userServiceImpl.crearUsuarioService(usuario);
    }

    /* Método POST */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam(name = "usuario") String usuario, @RequestParam(name = "contrasenia") String contrasenia){

        return userServiceImpl.loginService(usuario, contrasenia);
    }

    /* API generada por Spring MVC con un endpoint de cada tipo: GET - obtenerusuarios */
    @GetMapping("/getUsers")
    public ResponseEntity<?> obtenerusuarios(){

        return userServiceImpl.obtenerUsuarios();
    }

    /*Método PUT*/
    @PutMapping("/update")
    public ResponseEntity<?> actualizarcontrasenia(
            @RequestParam(name = "idUsuario")
                    String idUsuario,
            @RequestParam(name = "contrasenia")
                    String nuevaContrasenia){

        return userServiceImpl.cambiarContrasenia(idUsuario, nuevaContrasenia);
    }

    /* Método GET*/
    @GetMapping("/obtenerusuario/{idUsuario}")
    public ResponseEntity<?> obtenerusuario(@PathVariable(name = "idUsuario") String idUsuario){

        return userServiceImpl.obtenerUsuarioPoId(idUsuario);
    }

    /* API generada por Spring MVC con un endpoint de cada tipo: DELETE - borrarUsuarioPorId */
    @DeleteMapping("/borrarusuario/{idUsuario}")
    public ResponseEntity<?> borrarUsuario(@PathVariable(name = "idUsuario") String idUsuario){

        return userServiceImpl.borrarUsuarioId(idUsuario);
    }

    /* Método GET consumir api pública*/
    @GetMapping("/buscarAlbum")
    public ResponseEntity<ResponseDTO> jsonplaceholder(){
        String url = "https://jsonplaceholder.typicode.com/";

        return userServiceImpl.consumirApiPublica(url);
    }
}