package com.baz.wizeline.javamaven.controller;
import com.baz.wizeline.javamaven.client.AccountsJSONClient;
import com.baz.wizeline.javamaven.model.User;
import com.baz.wizeline.javamaven.model.UserDAL;
import com.baz.wizeline.javamaven.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/operaciones.con.usuarios")
public class UserController {
    @Autowired
    private final UserDAL userDAL;
    private final UserRepository userRepository;
    @Autowired
    private MongoTemplate mongoTemplate;



    private final Logger LOG = LoggerFactory.getLogger(getClass());
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(UserController.class.getName());
    @Autowired
    AccountsJSONClient accountsJSONClient;


    public UserController(UserDAL userDAL, UserRepository userRepository) {
        this.userDAL = userDAL;
        this.userRepository = userRepository;

    }



    //Obtener todos los usuarios metodo GET
    @RequestMapping(value = "/getUsers", method = RequestMethod.GET)

    public List<User> getAllUsers() {
        LOG.info("Obteniedo todos los usuarios.");
        return userRepository.findAll();
    }
//Crear un nuevo usuario método POST
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public User addNewUsers(@RequestBody User user) {
        LOG.info("Guardar usuario.");
        return userRepository.save(user);
    }
//Actualizar usuario por id método PUT
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public User UpdateUser(@RequestBody User user) {
        LOG.info("Buscando usuario a actualizar.");
        user = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(user.getUserId())), User.class);
        user.setName(user.getName().toString());
        LOG.info("Guardando usuario actualizado.");
        mongoTemplate.save(user, "user");
        user.setName(user.getName());
        LOG.info("Enviando respuesta...");
        return user;
    }

    //Eliminar usuario por id método DELETE
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public User getAllUserSettings1(@RequestBody User user) {
        LOG.info("Buscando usuario a eliminar.");
        user = mongoTemplate.findOne(
                Query.query(Criteria.where("_id").is(user.getUserId())), User.class);
        user.setName(user.getName().toString());
        LOG.info("Eliminando usuario.");
        mongoTemplate.remove(user, "user");
        user.setName(user.getName());
        LOG.info("Enviando respuesta...");
        return user;
    }

    @GetMapping("/getAccounts")
    public ResponseEntity<List<User>> getUsers() {
        List<User> accounts =userDAL.getAllUsers();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
        return new ResponseEntity<>(accounts, responseHeaders, HttpStatus.OK);

    }

}