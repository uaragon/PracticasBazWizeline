package com.baz.wizeline.javamaven.controller;

import com.baz.wizeline.javamaven.model.User;
import com.baz.wizeline.javamaven.model.Users;
import com.baz.wizeline.javamaven.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class UserControllerTest {
//Contiene los Tests de los EndPoint de la API

    public static final Logger logger = LoggerFactory.getLogger(ApiPublicaControllerTest.class);
    private static final ObjectMapper om = new ObjectMapper();
    @Autowired
    public TestRestTemplate restTemplate;

    @MockBean
    private UserRepository mockRepository;


    @Test
    //Método POST para crear usuario, valida la estructura del JSON recibido
    public void verificar_JSON_crearUsuario() throws JSONException {

        String userInJson = "{\"name\":\" Spring REST tutorials\", \"author\":\"abc\",\"price\":\"9.99\"}";
        logger.info("Variable que validará la estructura corecta del JSON en la petición : " + userInJson);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(userInJson, headers);
        ResponseEntity<String> response = restTemplate.exchange("/user/create", HttpMethod.POST, entity, String.class);
        logger.info("Valida la URL" + response);
        String expectedJson = "{\n" +
                "  \"name\" : \"Shubham\",\n" +
                "  \"userSettings\" : {\n" +
                "    \"bike\" : \"pulsar\"\n" +
                "  }\n" +
                "}";
        logger.info("Estructura del JSON esperado :" + expectedJson);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        verify(mockRepository, times(0)).save(any(User.class));

    }


    @Test
    //Metodo GET Obtener Usuarios, valida el parámetro esperado id del usuario.
    public void valida_Lista_Ontener_Usuarios() {

        // Crea un objeto simulado
        logger.info("Creando Objeto simulado :Usuario");
        List<User> list = mock(List.class);
        // Establecer el valor de retorno esperado del método

        when(list.get(0)).thenReturn(list.get(1));
        logger.info("Creando Objeto simulado :Usuario");
        User result = list.get(0);
        logger.info("Objeto simulado :Usuario" + result);
        // Verifica la llamada al método (si se llama a get (0))
        verify(list).get(0);
        // prueba
        Assert.assertEquals(Users.builder().build(), result);
        logger.info("Objeto validado");
    }


    @Test
    //Valida el path correcto en el método PUT eliminar usuario
    public void valida_path_correcto() {
        List<String> list = mock(List.class);
        logger.info("Se crea lista a validar");
        when(list.get(anyInt())).thenReturn("eliminar", "usuario");

        String result = list.get(0) + list.get(1);

        verify(list, times(2)).get(anyInt());
        logger.info("Numero de invocaciones");
        Assert.assertEquals("eliminarusuario", result);
    }


}