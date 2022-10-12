package com.baz.wizeline.finalproject.SERVICE;

import com.baz.wizeline.finalproject.DAO.UserDAO;
import com.baz.wizeline.finalproject.DTO.ErrorDTO;
import com.baz.wizeline.finalproject.DTO.JsonplaceHolderDTO;
import com.baz.wizeline.finalproject.DTO.ResponseDTO;
import com.baz.wizeline.finalproject.DTO.UsuarioDTO;
import com.baz.wizeline.finalproject.ENTITY.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserServiceBO{
    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Override
    public ResponseEntity<ResponseDTO> crearUsuarioService(UsuarioDTO usuario) {
        ResponseDTO respuesta = new ResponseDTO();

        Usuario usuarioEntidad = new Usuario();

        BeanUtils.copyProperties(usuario, usuarioEntidad);

        usuarioEntidad = userDAO.crearUsuario(usuarioEntidad);

        if(usuarioEntidad == null){
            log.info("No se insertó el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "No se pudo crear el usuario");

            respuesta.setCode("ER001");
            respuesta.setStatus("No se pudo crear el usuario");
            respuesta.setErrors(errorDTO);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Usuario creado correctamente");

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> loginService(String usuario, String contrasenia) {
        ResponseDTO respuesta = new ResponseDTO();

        Usuario usuarioEncontrado = userDAO.login(usuario, contrasenia);

        if(usuarioEncontrado == null){
            log.info("No se encontró el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "Usuario no encontrado");

            respuesta.setCode("ER001");
            respuesta.setStatus("Usuario no encontrado");
            respuesta.setErrors(errorDTO);
            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Login exitoso");
        respuesta.setResultado(usuarioEncontrado);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> cambiarContrasenia(String idUsuario, String nuevaContrasenia) {
        ResponseDTO respuesta = new ResponseDTO();

        long actualizado = userDAO.cambiarContrasenia(idUsuario, nuevaContrasenia);

        if(actualizado <= 0){
            log.info("No se pudo actualizar el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "No se pudo actualizar el usuarios con id: "+idUsuario);

            respuesta.setCode("ER001");
            respuesta.setStatus("No se pudo actualizar la contraseña");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Contrasenia de usuario actualizado");
        respuesta.setResultado("Total modificado: "+actualizado);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> obtenerUsuarios() {
        ResponseDTO respuesta = new ResponseDTO();

        List<Usuario> usuarios = userDAO.obtenerUsuarios();

        if(usuarios.size() <= 0){
            log.info("No se encontraron usuarios");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "No se encontraron usuarios");

            respuesta.setCode("ER001");
            respuesta.setStatus("No se encontraron usuarios");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Usuarios encontrados");
        respuesta.setResultado(usuarios);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> obtenerUsuarioPoId(String idUsuario) {
        ResponseDTO respuesta = new ResponseDTO();

        Usuario usuario = userDAO.obtenerUsuario(idUsuario);


        if(usuario == null) {
            log.info("No se encontró el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "Usuario no encontrado");

            respuesta.setCode("ER001");
            respuesta.setStatus("Usuario no encontrado");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }
        respuesta.setCode("OK000");
        respuesta.setStatus("Usuario encontrado");
        respuesta.setResultado(usuario);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> borrarUsuarioId(String idUsuario) {
        ResponseDTO respuesta = new ResponseDTO();

        long cantidadBorrada = userDAO.borrarUsuario(idUsuario);

        if(cantidadBorrada <= 0){
            log.info("No se borro el usuario");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "No se borro el usuarios con id: "+idUsuario);

            respuesta.setCode("ER001");
            respuesta.setStatus("Usuario no borrado");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Usuario borrado!");
        respuesta.setResultado("Total eliminado: "+cantidadBorrada);

        return ResponseEntity.ok(respuesta);
    }

    @Override
    public ResponseEntity<ResponseDTO> consumirApiPublica(String url) {
        ResponseDTO respuesta = new ResponseDTO();

        /* Consumo de API pública */
        RestTemplate restTemplate = new RestTemplate();

        JsonplaceHolderDTO object = restTemplate.getForObject(url, JsonplaceHolderDTO.class);

        if(object == null){
            log.info("No se pudo consumir la api publica");
            ErrorDTO errorDTO = new ErrorDTO("ER001", "Fallo al consumir la api publica");

            respuesta.setCode("ER001");
            respuesta.setStatus("Error al consumir api");
            respuesta.setErrors(errorDTO);

            return new ResponseEntity<>(respuesta, HttpStatus.BAD_REQUEST);
        }

        respuesta.setCode("OK000");
        respuesta.setStatus("Api consumida");
        respuesta.setResultado(object);

        return ResponseEntity.ok(respuesta);
    }
}