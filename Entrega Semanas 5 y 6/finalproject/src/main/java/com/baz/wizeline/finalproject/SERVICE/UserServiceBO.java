package com.baz.wizeline.finalproject.SERVICE;

import com.baz.wizeline.finalproject.DTO.ResponseDTO;
import com.baz.wizeline.finalproject.DTO.UsuarioDTO;
import org.springframework.http.ResponseEntity;

public interface UserServiceBO {

    ResponseEntity<ResponseDTO> crearUsuarioService(UsuarioDTO usuario);
    ResponseEntity<ResponseDTO> loginService(String usuario, String contrasenia);
    ResponseEntity<ResponseDTO> cambiarContrasenia(String idUsuario, String nuevaContrasenia);
    ResponseEntity<ResponseDTO> obtenerUsuarios();
    ResponseEntity<ResponseDTO> obtenerUsuarioPoId(String idUsuario);
    ResponseEntity<ResponseDTO> borrarUsuarioId(String idUsuario);
    ResponseEntity<ResponseDTO> consumirApiPublica(String url);

}