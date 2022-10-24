package com.baz.wizeline.javamaven.controller;

import com.baz.wizeline.javamaven.model.ApiPublicaModel;
import com.baz.wizeline.javamaven.model.ErrorDTO;
import com.baz.wizeline.javamaven.model.ResponseDTO;
import com.baz.wizeline.javamaven.model.UserDTO;
import com.baz.wizeline.javamaven.repository.AccountsJSONClient;
import com.baz.wizeline.javamaven.repository.UserDAL;
import com.baz.wizeline.javamaven.repository.UserRepository;
import com.baz.wizeline.javamaven.servicios.ApiPublicaService;
import com.baz.wizeline.javamaven.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


@RestController
@RequestMapping("/operaciones.con.usuarios")
public class UserController {
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(UserController.class.getName());
    @Autowired
    private final UserDAL userDAL;
    private final UserRepository userRepository;
    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    AccountsJSONClient accountsJSONClient;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private Utils utils;
    @Autowired
    @Qualifier("ApiPublicaService")
    private ApiPublicaService apiPublicaService;


    public UserController(UserDAL userDAL, UserRepository userRepository) {
        this.userDAL = userDAL;
        this.userRepository = userRepository;

    }

    //Obtener todos los usuarios metodo GET
    @RequestMapping(value = "/obtenerUsuarios", method = RequestMethod.GET)
    public List<UserDTO> getAllUserss() {
        LOG.info("Obteniedo todos los usuarios.");


        return userRepository.findAll();
    }
@RequestMapping(value = "obtenerUsuarioss",method = RequestMethod.GET)
public ResponseEntity<?> obtenerUsuarios() {
    ResponseDTO respuesta = new ResponseDTO();

    List<UserDTO> usuarios = userRepository.findAll();

    if(usuarios.size() <= 0){
        LOG.info("No se encontraron usuarios");
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

    //Crear un nuevo usuario método POST
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> addNewUsers(@RequestBody UserDTO userDTO) {
        String responseText;
        HttpHeaders responseHeaders = new HttpHeaders();
        //Valida fecha de nacimiento con formato correcto
        LOG.info("Validando formato de la fecha de nacimiento: "+userDTO.fechaNacimiento);
        if (Utils.isDateFormatValid(userDTO.getFechaNacimiento())) {

            //Valida password password
            LOG.info("Validando estructura de la contraseña. ");
            if (Utils.isPasswordValid(userDTO.getPassword())) {
                LOG.info("Guardar usuario.");
                return new ResponseEntity<>(userRepository.save(userDTO), responseHeaders, HttpStatus.OK);
            } else {
                Instant finalDeEjecucion = Instant.now();
                LOGGER.info("LearningJava - Cerrando recursos ...");
                responseText = "Password Incorrecto ,la contraseña debe tener al menos una letra mayúscula,minúscula,un número y un caracter especial.";
                return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
            }


        } else {
            responseText = "Formato de Fecha Incorrecto, formato requerido dd-mm-aaaa (día : 2 dígitos, mes : 2 dígitos, año: 4 dígitos)";

        }
        return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
    }

    //Actualizar usuario por id método PUT
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ResponseEntity<?> UpdateUser(@RequestBody UserDTO userDTO) {
        String nuevoNombre = userDTO.name;
        String responseText = "";
        HttpHeaders responseHeaders = new HttpHeaders();
        LOG.info("Buscando usuario a actualizar.");
        userDTO = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(userDTO.getUserId())), UserDTO.class);
        if (userDTO != null) {
            responseText = "Usuario actualizado correctamente";
            userDTO.setName(nuevoNombre);
            LOG.info("Guardando usuario actualizado.");
            mongoTemplate.save(userDTO, "user");
            userDTO.setName(userDTO.getName());
            LOG.info("Enviando respuesta...");
            return new ResponseEntity<>(userRepository.save(userDTO), responseHeaders, HttpStatus.OK);
        } else {
            responseText = "Usuario no encontrado";

        }

        return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
    }

    //Eliminar usuario por id método DELETE
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public ResponseEntity<?> eliminarUsuario(@RequestBody UserDTO userDTO) {
        String responseText = "";
        HttpHeaders responseHeaders = new HttpHeaders();

        LOG.info("Buscando usuario a eliminar.");
        userDTO = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(userDTO.getUserId())), UserDTO.class);
        // userDTO.setName(userDTO.getName());
        if (userDTO != null) {
            LOG.info("Eliminando usuario.");
            mongoTemplate.remove(userDTO, "user");
            userDTO.setName(userDTO.getName());
            LOG.info("Enviando respuesta...");
            responseText = "Usuario eliminado correctamente :" + userDTO.getUserId();
            return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
        } else {
            responseText = "Usuario no encontrado";

        }
        return new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<List<UserDTO>> getUsers() {
        List<UserDTO> accounts = userDAL.getAllUsers();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);

    }
//Método GET para consumir API Pública
    @GetMapping(path = "/buscarComentarios",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ApiPublicaModel>> buscarComentariosConExchange() {

        LOG.info("Ingresa a metodo buscarComentariosConExchange");

        return apiPublicaService.buscarComentariosConExchange();
    }



}