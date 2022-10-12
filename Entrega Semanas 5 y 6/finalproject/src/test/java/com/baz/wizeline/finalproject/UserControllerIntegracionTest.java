package com.baz.wizeline.finalproject;

import com.baz.wizeline.finalproject.DTO.JsonplaceHolderDTO;
import com.baz.wizeline.finalproject.DTO.ResponseDTO;
import com.baz.wizeline.finalproject.DTO.UsuarioDTO;
import com.baz.wizeline.finalproject.ENTITY.Usuario;
import com.baz.wizeline.finalproject.SERVICE.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static java.nio.file.Paths.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegracionTest {
    /* Pruebas de integracion */
    /* Generación de logs por prueba*/
    private static final Logger log = LoggerFactory.getLogger(UserControllerIntegracionTest.class);

    @MockBean
    private UserServiceImpl userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("crearUsuarioController")
    public void crearUsuarioController() throws Exception {

        ResponseDTO respuesta = new ResponseDTO();
        respuesta.setCode("OK000");
        respuesta.setStatus("Usuario creado");

        Mockito.when(userService.crearUsuarioService(Mockito.any(UsuarioDTO.class))).thenReturn(ResponseEntity.ok(respuesta));

        MvcResult resultadoPeticion = mockMvc.perform(post("/api/crearusuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new UsuarioDTO("usuarioNuevo","contraseniaNueva"))))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Resultado: " + resultadoPeticion.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("loginUserControllerTest")
    public void loginUserServiceTest() throws Exception {
        String usuario = "k5";
        String contrasenia = "12345";

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("usuario", usuario);
        requestParams.add("contrasenia", contrasenia);

        ResponseDTO respuesta = new ResponseDTO();
        respuesta.setCode("OK000");
        respuesta.setStatus("Login exitoso");
        respuesta.setResultado(new UsuarioDTO(usuario, contrasenia));

        Mockito.when(userService.loginService(Mockito.anyString(), Mockito.anyString())).thenReturn(ResponseEntity.ok(respuesta));

        MvcResult resultadoPeticion = mockMvc.perform(post("/api/login")
                        .params(requestParams))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Resultado: " + resultadoPeticion.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("obtenerusuariosControllerTest")
    public void obtenerusuariosControllerTest() throws Exception {
        List<Usuario> listaUsuarios = new ArrayList<>();
        listaUsuarios.add(new Usuario("usuario1Encontrado", "contrasenia1Encontrada"));
        listaUsuarios.add(new Usuario("usuario2Encontrado", "contrasenia2Encontrada"));

        ResponseDTO respuesta = new ResponseDTO();
        respuesta.setCode("OK000");
        respuesta.setStatus("Usuarios encontrados");
        respuesta.setResultado(listaUsuarios);

        Mockito.when(userService.obtenerUsuarios()).thenReturn(ResponseEntity.ok(respuesta));

        MvcResult resultadoPeticion = mockMvc.perform((RequestBuilder) get("/api/obtenerusuarios"))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Resultado: " + resultadoPeticion.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("actualizarContraseniaControllerTest")
    public void actualizarContraseniaControllerTest() throws Exception {
        String idUsuario = "12345";
        String nuevaContrasenia = "xd";

        ResponseDTO respuesta = new ResponseDTO();
        respuesta.setCode("OK000");
        respuesta.setStatus("Contrasenia de usuario actualizado");
        respuesta.setResultado("Total modificado: " + 1);

        Mockito.when(userService.cambiarContrasenia(Mockito.anyString(), Mockito.anyString())).thenReturn(ResponseEntity.ok(respuesta));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idUsuario", idUsuario);
        requestParams.add("nuevaContrasenia", nuevaContrasenia);

        MvcResult resultadoPeticion = mockMvc.perform(put("/api/actualizarcontrasenia")
                        .params(requestParams))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Resultado: " + resultadoPeticion.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("obtenerUsuarioPorIdControllerTest")
    public void obtenerUsuarioPorIdControllerTest() throws Exception {
        String idUsuario = "12345";

        ResponseDTO respuesta = new ResponseDTO();
        respuesta.setCode("OK000");
        respuesta.setStatus("Usuario encontrado");
        respuesta.setResultado(new UsuarioDTO("nombreEjemplo","contraseniaEjemplo"));

        Mockito.when(userService.obtenerUsuarioPoId(Mockito.anyString())).thenReturn(ResponseEntity.ok(respuesta));

        LinkedMultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("idUsuario", idUsuario);

        MvcResult resultadoPeticion = mockMvc.perform((RequestBuilder) get("/api/obtenerusuario/{idUsuario}", idUsuario))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Resultado: " + resultadoPeticion.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("borrarUsuarioPorIdControllerTest")
    public void borrarUsuarioPorIdControllerTest() throws Exception {
        String idUsuario = "12345";

        ResponseDTO respuesta = new ResponseDTO();
        respuesta.setCode("OK000");
        respuesta.setStatus("Usuario borrado!");
        respuesta.setResultado("Total eliminado: "+1);

        Mockito.when(userService.borrarUsuarioId(Mockito.anyString())).thenReturn(ResponseEntity.ok(respuesta));

        LinkedMultiValueMap<String, String> requestParam = new LinkedMultiValueMap<>();
        requestParam.add("idUsuario", idUsuario);

        MvcResult resultadoPeticion = mockMvc.perform(delete("/api/borrarusuario/{idUsuario}", idUsuario))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Resultado: " + resultadoPeticion.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("consumirApiPublicaControllerTest")
    public void consumirApiPublicaControllerTest() throws Exception {
        ResponseDTO respuesta = new ResponseDTO();
        respuesta.setCode("OK000");
        respuesta.setStatus("Api consumida");
        respuesta.setResultado(new JsonplaceHolderDTO(1L, 1L, "", true));

        Mockito.when(userService.consumirApiPublica(Mockito.anyString())).thenReturn(ResponseEntity.ok(respuesta));

        MvcResult resultadoPeticion = mockMvc.perform((RequestBuilder) get("/api/apipublica/jsonplaceholder"))
                .andExpect(status().isOk())
                .andReturn();

        log.info("Resultado: " + resultadoPeticion.getResponse().getContentAsString());
    }
}