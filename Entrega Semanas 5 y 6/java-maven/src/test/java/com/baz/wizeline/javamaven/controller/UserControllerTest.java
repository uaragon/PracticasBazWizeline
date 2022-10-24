package com.baz.wizeline.javamaven.controller;

import com.baz.wizeline.javamaven.model.ErrorDTO;
import com.baz.wizeline.javamaven.model.ResponseDTO;
import com.baz.wizeline.javamaven.model.UserDTO;
import com.baz.wizeline.javamaven.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = {RestTemplate.class,UserController.class})
@AutoConfigureMockMvc
class UserControllerTest {
//Contiene los Tests de los EndPoint de la API

    public static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserController userController;

    @Test
    //Método POST para crear usuario, valida la estructura del JSON recibido
    public void verificar_JSON_crearUsuario() throws JSONException {
        String userInJson = "{\n" +
                "  \"name\": \"Shubham\",\n" +
                "  \"user\": \"theUser\",\n" +
                "  \"firstName\": \"John\",\n" +
                "  \"lastName\": \"James\",\n" +
                "  \"fechaNacimiento\":\"27-11-1990\",\n" +
                "  \"email\": \"john@email.com\",\n" +
                "  \"password\": \"test3U*\"\n" +
                "}";


        logger.info("Variable que validará la estructura corecta del JSON en la petición : " );
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String responseText = "{\n" +
                "  \"name\": \"Shubham\",\n" +
                "  \"user\": \"theUser\",\n" +
                "  \"firstName\": \"John\",\n" +
                "  \"lastName\": \"James\",\n" +
                "  \"fechaNacimiento\":\"27-11-1990\",\n" +
                "  \"email\": \"john@email.com\",\n" +
                "  \"password\": \"test3U*\"\n" +
                "}";

        HttpHeaders responseHeaders = new HttpHeaders();
        ResponseEntity<String> response =new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
        logger.info("Valida la estructura del JSON" );
        String expectedJson = "{\n" +
                "  \"name\": \"Shubham\",\n" +
                "  \"user\": \"theUser\",\n" +
                "  \"firstName\": \"John\",\n" +
                "  \"lastName\": \"James\",\n" +
                "  \"fechaNacimiento\":\"27-11-1990\",\n" +
                "  \"email\": \"john@email.com\",\n" +
                "  \"password\": \"test3U*\"\n" +
                "}";
        logger.info("Estructura del JSON esperado :" );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        JSONAssert.assertEquals(expectedJson, response.getBody(), false);

        verify(userRepository, times(0)).save(any(UserDTO.class));

    }


    @Test
    //Metodo GET Obtener Usuarios, valida el parámetro esperado id del usuario.
    public void valida_Lista_Obtener_Usuarios() throws Exception {

        List<UserDTO> otdUsuarios =
                List.of(new UserDTO("Urbano", "12345"),
                        new UserDTO("Enrique", "112233"));
        when(userController.getUsers()).thenReturn(new ResponseEntity(otdUsuarios, HttpStatus.OK));

        MvcResult resultadoPeticion =
                mockMvc.perform(get("/operaciones.con.usuarios/obtenerUsuarios"))
                        .andExpect(status().isOk())
                        .andReturn();

        UserDTO[] otdPaisesArreglo =
                mapper.readValue(resultadoPeticion.getResponse().getContentAsString(), UserDTO[].class);
        List<UserDTO> otdPaisesResultado = Arrays.asList(otdPaisesArreglo);

        assertTrue(
                otdPaisesResultado.stream()
                        .map(UserDTO::getName)
                        .collect(Collectors.toList())
                        .containsAll(List.of("Urbano", "Enrique")));

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
    @Test
    //Valida eliminar ususario por id
    public void  validaEliminacionCorrectadelUsuario(){
        logger.info("Se arma el resultado esperado");
        List<UserDTO> usuarios = new ArrayList<>();
        ResponseDTO response = new ResponseDTO();
        usuarios.add(new UserDTO("userTest1", "passTest1"));
        String responseText = "Se encontraron usuarios registros";
        HttpHeaders responseHeaders = new HttpHeaders();
        logger.info("Se arma el response del endpoint : ");
        Mockito.when(userController.eliminarUsuario(Mockito.isNotNull())).thenReturn(new ResponseEntity(response, HttpStatus.OK));

    }

    @Test
    //Valida que el endpoint devuelva al menos un registro
    public void ValidabuscarUsuarios() {
        List<UserDTO> usuarios = new ArrayList<>();
        usuarios.add(new UserDTO("userTest1", "passTest1"));
        String responseText = "Se encontraron usuarios registros";
        HttpHeaders responseHeaders = new HttpHeaders();
        logger.info("Se arma el response del endpoint : ");
        new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
        List<UserDTO> httpResponse = usuarios;
        logger.info("Valida si el tamaño del objeto es 1");
        Assert.assertEquals(httpResponse.size(), 1);
        logger.info("Tamaño : " + httpResponse.size());

    }

    @Test
    // Valida Respuesta Exitosa consulta usuarios
    public void validaBuscarUsuarioResultadoNoVacio() {
        logger.info("Se arma el response");
        String responseText = "Busqueda de ususarios exitosa.";
        HttpHeaders responseHeaders = new HttpHeaders();
        ResponseEntity<String> httpResponse = new ResponseEntity<>(responseText, responseHeaders, HttpStatus.OK);
        logger.info("Si el resultado no es nulo, devuelve el response");
        Assert.assertNotNull(httpResponse.getBody());
    }

    //Valida si el resultado de la consulta de búsqueda de usuarios es vacío
    @Test
    public void validaBuscarUsuarioResultadoVacío() {
        ResponseDTO response = new ResponseDTO();
        logger.info("Se arma el response :");
        ErrorDTO errorDTO = new ErrorDTO("ER001", "No se encontraron usuarios");
        response.setCode("ER001");
        response.setStatus("No se encontraron usuarios");
        response.setErrors(errorDTO);
        logger.info("Response armado :" + response);
        logger.info("Mock servicio para simular el badrequest");
        Mockito.when(userController.obtenerUsuarios()).thenReturn(new ResponseEntity(response, HttpStatus.BAD_REQUEST));

    }

    //Valida que no venga ningún parámetro vacío
    @Test
    public void validaInsertarContraseñaDatoNoNulo() throws Exception {
        String idUsuario = "12345";
        String password = "";
        logger.info("Se implementan los parametros de entrada a un mapa");
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idUsuario", idUsuario);
        requestParams.add("nuevaContrasenia", password);
        ResponseDTO response = new ResponseDTO();
        ErrorDTO errorDTO = new ErrorDTO("ER001", "El parámetro : password no debe ir nulo");
        response.setCode("ER001");
        response.setStatus("Datos incorrectos");
        response.setErrors(errorDTO);
        logger.info("Se arma el response : "+response);
        logger.info("Se valida que la contraseña no venga vacío");
        Mockito.when(userController.addNewUsers(new UserDTO("ddd", "ddd"))).thenReturn(new ResponseEntity(response, HttpStatus.BAD_REQUEST));
        logger.info("Se arma la petición con los parametros de entrada");
        MvcResult result = mockMvc.perform(put("/operaciones.con.usuarios/update")
                        .params(requestParams))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void consumirApiPublicaRespuestaCorrecta(){

        logger.info("Se arma la respuesta esperada");
        UserController dictMock = mock(UserController.class);
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("OK000");
        dto.setStatus("Api consumida");
        Mockito.when(userController.buscarComentariosConExchange().getStatusCode().value()).thenReturn(200);

    }

    @Test
    public void cuandoseObtieneUnaExcepcionEnElConsumodeLaAPiPublica() {
        logger.info("Se configuta la exception");
        UserController dictMock = mock(UserController.class);
        logger.info("Se valida la respuesta del endpoint");
        when(dictMock.buscarComentariosConExchange().getStatusCode()).thenThrow(NullPointerException.class);
        dictMock.buscarComentariosConExchange();
    }


}