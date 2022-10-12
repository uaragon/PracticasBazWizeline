package com.baz.wizeline.finalproject.DAO;

import com.baz.wizeline.finalproject.ENTITY.Usuario;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(UserDAO.class)
public class UserDAOTest {
    /* Generación de logs por prueba - UserControllerTest: Logger */
    private static final Logger log = LoggerFactory.getLogger(UserDAOTest.class);

    /* Uso de Mockito en cada prueba - UserDAOTest */
    @MockBean
    private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Create User")
    public void DadoElComponenteDAO_CuandoIngresanUnBeanDeUsuarioCorrecto_SeInsertaEnLaBD(){
        log.info("Se arma la entidad correcta a regresar");
        Usuario usuarioEntidad = new Usuario("test","123");

        log.info("Servicio que devolverá la estructura simuladamente guardada");
        Mockito.when(mongoTemplate.save(Mockito.any(Usuario.class))).thenReturn(usuarioEntidad);

        log.info("Se consume el servicio");
        /* Prueba unitaria de cada operación CRUD - mongoTemplate.save */
        Usuario usuarioGuardado = mongoTemplate.save(usuarioEntidad);

        log.info("Validar el resultado");
        Assertions.assertAll(
                () -> Assertions.assertNotNull(usuarioGuardado),
                () -> Assertions.assertEquals("kXD", usuarioGuardado.getNombre())
        );

        log.info("Usuario guardado en BD: "+ usuarioGuardado.getId()+ " "+ usuarioGuardado.getNombre()+" "+ usuarioGuardado.getContrasenia());
    }

    @Test
    @DisplayName("Delete User")
    public void DadoElComponenteDAO_CuandoIngresanUnIdDeUsuario_SeEliminaEnLaBD(){
        log.info("Servicio remove patra devolver la respuesta esperada");
        Mockito.when(mongoTemplate.remove(Mockito.anyString())).thenReturn(new DeleteResult() {
            @Override
            public boolean wasAcknowledged() {
                return false;
            }

            @Override
            public long getDeletedCount() {
                return 0;
            }
        });

        log.info("Se consume el servicio mockeado.");
        /* Prueba unitaria de cada operación CRUD - mongoTemplate.remove */
        DeleteResult cantidadEliminada = mongoTemplate.remove("123456");

        log.info("Se asegura los resultados");
        Assertions.assertAll(
                () -> Assertions.assertNotNull(cantidadEliminada),
                () -> Assertions.assertEquals(0, cantidadEliminada.getDeletedCount())
        );

        log.info("Cantidad de usuarios eliminados en BD: "+ cantidadEliminada.getDeletedCount());
    }

    @Test
    @DisplayName("FindAll User")
    public void DadoElComponenteDAO_CuandoIngresaAlServicioDeObtenerTodosUsuarios_BuscamosEnBDALosUsuarios(){
        log.info("Se estructura la respuesta esperada que es una lista de UsuarioEntidad");
        List<Usuario> lista = new ArrayList<>();
        lista.add(new Usuario("Usuario1FindAll","contrasenia1FindAll"));
        lista.add(new Usuario("Usuario2FindAll","contrasenia2FindAll"));

        log.info("Se mockea el metodo que devolverá la lista esperada");
        Mockito.when(mongoTemplate.findAll(Usuario.class)).thenReturn(lista);

        log.info("Se consume el servicio mockeado");
        /* Prueba unitaria de cada operación CRUD - mongoTemplate.findAll */
        List<Usuario> listaUsuariosEncontrados = mongoTemplate.findAll(Usuario.class);

        log.info("Valida los resultados");
        Assertions.assertAll(
                () -> Assertions.assertNotNull(listaUsuariosEncontrados),
                () -> Assertions.assertEquals(2, listaUsuariosEncontrados.size())
        );

        log.info("Catidad de usuarios encontrados en BD: "+ listaUsuariosEncontrados.size());
    }

    @Test
    @DisplayName("Update Password DAO")
    public void DadoElComponenteDAO_CuandoIngresaAlServicioDeActualizacion_BuscamosEnBDElIdYActualizamosContrasenia(){
        log.info("Servicio que se utilizara al actualizar la contrasenia");
        Mockito.when(mongoTemplate.updateFirst(Mockito.any(Query.class), Mockito.any(Update.class), Mockito.any(Class.class)))
                .thenReturn(null);

        log.info("Se crean nuevos objetos ya que son necesarios para el servicio de mongoTemplate");
        Query query = new Query();
        Update update = new Update();

        log.info("Se consume el servicio");
        /* Prueba unitaria de cada operación CRUD - mongoTemplate.updateFirst */
        UpdateResult result = mongoTemplate.updateFirst(query, update, Usuario.class);

        log.info("Valida los resultados con el sig assert");
        Assertions.assertAll(
                () -> Assertions.assertNull(result)
        );
    }

}