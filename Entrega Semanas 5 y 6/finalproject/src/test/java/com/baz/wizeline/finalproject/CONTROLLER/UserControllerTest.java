package com.baz.wizeline.finalproject.CONTROLLER;

import com.baz.wizeline.finalproject.DAO.UserDAO;
import com.baz.wizeline.finalproject.DTO.ErrorDTO;
import com.baz.wizeline.finalproject.DTO.ResponseDTO;
import com.baz.wizeline.finalproject.DTO.UsuarioDTO;
import com.baz.wizeline.finalproject.ENTITY.Usuario;
import com.baz.wizeline.finalproject.SERVICE.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    /* Generación de logs por prueba*/
    private static final Logger log = LoggerFactory.getLogger(UserControllerTest.class);

    /* Uso de Mockito en cada prueba*/
    @MockBean
    private UserServiceImpl userService;

    @MockBean
    private UserDAO userDAO;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    /* Prueba unitaria de cada endpoint de la API - Crear Usuario */
    @Test
    @DisplayName("Crear Usuario")
    public void DadoElServicioCrearUsuario_CuandoTieneValoresAceptables_RegresaStatus200() throws Exception {
        /* Pruebas para Happy Path */
        log.info("Se crea el cuerpo que viajará por la petición de prueba, con nombreDeUsuario y contrasenia");
        UsuarioDTO usuarioDTO2 = new UsuarioDTO("k10", "1234567890");

        log.info("Se crea un objeto que regresa UsuarioEntidad");
        Usuario usuarioEntidad = new Usuario();

        log.info("Se copian los valores que contiene el cuerpo original al cuerpo esperado");
        BeanUtils.copyProperties(usuarioDTO2, usuarioEntidad);

        log.info("Se mockea la capa dao para cuando se mande a llamar este con un cuerpo de cierto tipo, responda con la respuesta esperada");
        Mockito.when(userDAO.crearUsuario(Mockito.any(Usuario.class))).thenReturn(usuarioEntidad);

        log.info("Se arma la respuesta esperada del servicio cargandole todos los parametros esperados");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("OK000");
        dto.setStatus("Usuario creado");
        dto.setResultado(userDAO.crearUsuario(usuarioEntidad));

        log.info("Se mockea el servicio para devolver la respuesta esperada, en este caso un exito");
        Mockito.when(userService.crearUsuarioService(Mockito.any(UsuarioDTO.class))).thenReturn(ResponseEntity.ok(dto));

        log.info("Se arma la peticion como si fuera un postman con la ruta y el cuerpo de entrada");
        MvcResult result = mockMvc.perform(post("/api/crearusuario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioDTO2))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK000"))
                .andExpect(jsonPath("$.status").value("Usuario creado"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult cuerpo de respuesta: " + result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Edge Case - CrearUsuarioController - ValidaRequestBodyNulo")
    public void DadoElServicioCrearUsuario_CuandoTieneBodyNulo_RegresaRespuestaGeneralErroneaControlada() throws Exception {
        log.info("Instanciamos serializador de objetos");
        Gson gson = new Gson();

        log.info("serializamos un cuerpo nulo para la simulacion");
        String json = gson.toJson(null);

        log.info("Se arma la peticion como si fuera un postman con la ruta y el cuerpo de entrada, todo con el resultado esperado");
        MvcResult result = mockMvc.perform(post("/api/login")
                        .content(json))
                .andExpect(status().isBadRequest())
                .andReturn();

        log.info("Valor peticion: "+result.getResponse().getErrorMessage());
    }

    /* Prueba unitaria de cada endpoint de la API - Login */
    @Test
    @DisplayName("Login")
    public void DadoElServicioLogin_CuandoIngresaUsuarioYContrasenia_BuscaEnBDExistencia() throws Exception {
        /* Pruebas para Happy Path - UserControllerTest - LoginUserController */
        log.info("Se inicializan parametros de entrada");
        String usuario = "k5";
        String contrasenia = "12345";

        log.info("Se inicializan los cuerpos de entrada y el esperado");
        UsuarioDTO usuarioDTO = new UsuarioDTO(usuario, contrasenia);
        Usuario nuevoUsuarioEntidad = new Usuario();

        log.info("Se copian los valores del cuerpo de entrada al cuerpo esperado");
        BeanUtils.copyProperties(usuarioDTO, nuevoUsuarioEntidad);

        log.info("Se mockea el componente dao, para responder con el cuerpo esperado y la prueba pase");
        Mockito.when(userDAO.login(Mockito.anyString(), Mockito.anyString())).thenReturn(nuevoUsuarioEntidad);

        log.info("Se estructura la respuesta esperada con sus respectivos valores");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("OK000");
        dto.setStatus("Usuario encontrado");
        dto.setResultado(userDAO.login(usuario, contrasenia));

        log.info("Se mockea el servicio a consumir cuando inicie el proceso de peticion y devuelve la respuesta esperada");
        Mockito.when(userService.loginService(Mockito.anyString(), Mockito.anyString())).thenReturn(ResponseEntity.ok(dto));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("usuario", "user");
        requestParams.add("contrasenia", "123");

        log.info("Se arma la petición con parametros de entrada con el resultado esperado");
        MvcResult result = mockMvc.perform(post("/api/login")
                        .params(requestParams))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK000"))
                .andExpect(jsonPath("$.status").value("Usuario encontrado"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult result login: " + result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Edge Case")
    public void DadoElServicioLogin_SiRecibeLosParametrosVaciosoNulos_RegresaBadRequest() throws Exception {
        log.info("Se inicializan los parametros de entrada");
        //Cambiar valor de idUsuario y nuevaContrasenia a valores como: "" o null
        String usuario = "";
        String contrasenia = "";

        log.info("Se cargan los parametros inicializados a un mapa");
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("usuario", usuario);
        requestParams.add("contrasenia", contrasenia);

        log.info("Se estructura y cargan los valores de la mala respuesta general esperada");
        ErrorDTO errorDTO = new ErrorDTO("ER001", "Usuario no encontrado");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("ER001");
        dto.setStatus("Los parametros son requeridos");
        dto.setErrors(errorDTO);

        log.info("Se mockea el servicio que responderá de mala manera cuando el servicio sea llamado");
        Mockito.when(userService.loginService(usuario, contrasenia)).thenReturn(new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST));

        log.info("Se arma la petición con parámetros de entrada, todo con el resultado esperado");
        MvcResult result = mockMvc.perform(post("/api/login")
                        .params(requestParams))
                .andExpect(status().isBadRequest())
                .andReturn();

        log.info("Result login: "+result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Edge Case - LoginController - UsuarioNoExistente")
    public void SiRecibeLosParametrosDeUsuarioNoValido_RegresaRespuestaGeneralControlada() throws Exception {
        log.info("Se inicializan los parámetros de entrada");
        String usuario = "nombreUsuarioNoExistente";
        String contrasenia = "contraseniaUsuarioIncorrecta";

        log.info("Se arma la respuesta general mala esperada con sus respectivos valores");
        ErrorDTO errorDTO = new ErrorDTO("ER001", "Credenciales invalidas");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("ER001");
        dto.setStatus("Credenciales invalidas");
        dto.setErrors(errorDTO);

        log.info("Se mockea el servicio que será llamado que responderá con un badRequest");
        Mockito.when(userService.loginService(usuario, contrasenia)).thenReturn(new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST));

        log.info("Se cargan los parametros en un mapa multi valor");
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("usuario", usuario);
        requestParams.add("contrasenia", contrasenia);

        log.info("Se arma la petición con los parametros de entrada, todo con el resultado esperado");
        MvcResult result = mockMvc.perform(post("/api/login")
                        .params(requestParams))
                .andExpect(status().isBadRequest())
                .andReturn();

        log.info("Login de usuario inválido: "+result.getResponse().getContentAsString());
    }

    /* Prueba unitaria de cada endpoint de la API - Obtener Usuarios */
    @Test
    @DisplayName("Obtener Usuarios")
    public void DevuelveListaDeUsuariosExistentes() throws Exception {
        /* Pruebas para Happy Path - UserControllerTest - ObtenerUsuariosUserController */
        log.info("Se inicializa y cargan valores de la lista esperada por la api");
        List<Usuario> usuariosEntidad = new ArrayList<>();
        usuariosEntidad.add(new Usuario("usuario1","pass1"));
        usuariosEntidad.add(new Usuario("usuario2","pass2"));
        usuariosEntidad.add(new Usuario("usuario3","pass3"));

        log.info("Se mockea el componente que responderá la lista anterior");
        Mockito.when(userDAO.obtenerUsuarios()).thenReturn(usuariosEntidad);

        log.info("Se estructura la respuesta general esperada, asi como sus valores correspondientes");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("OK000");
        dto.setStatus("Usuario encontrado");
        dto.setResultado(userDAO.obtenerUsuarios());

        log.info("Se mockea el servicio que respondera con la respuesta generalk esperado");
        Mockito.when(userService.obtenerUsuarios()).thenReturn(ResponseEntity.ok(dto));

        log.info("Se arma la petición con el resultado esperado");
        MvcResult result = mockMvc.perform(get("/api/obtenerusuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK000"))
                .andExpect(jsonPath("$.status").value("Usuario encontrado"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult result obtenerUsuarios: " + result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Edge Case - ObtenerUsuariosController - RespuestaErroneaControlada")
    public void DadoElServicio_CuandoEsLlamadoYNoExistenUsuarios_DevuelveUnErrorGeneralControlado() throws Exception {
        log.info("Se estructura la respuesta general mala esperada con sus respectivos valores");
        ErrorDTO errorDTO = new ErrorDTO("ER001", "No se encontraron usuarios");

        ResponseDTO dto = new ResponseDTO();
        dto.setCode("ER001");
        dto.setStatus("No se encontraron usuarios");
        dto.setErrors(errorDTO);

        log.info("Se mockea el servicio que simulará el badrequest");
        Mockito.when(userService.obtenerUsuarios()).thenReturn(new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST));

        log.info("Se arma la petición como si fuera un postman con la ruta, todo con el resultado esperado");
        MvcResult result = mockMvc.perform(get("/api/obtenerusuarios"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ER001"))
                .andExpect(jsonPath("$.status").value("No se encontraron usuarios"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult result no se encontraron usuarios: " + result.getResponse().getContentAsString());
    }

    /* Prueba unitaria de cada endpoint de la API - Actualizar Contrasenia */
    @Test
    @DisplayName("Actualizar Contraseña")
    public void SiRecibeLosParametrosNecesarios_ActualizaContraseniaDeUsuario() throws Exception {
        /* Pruebas para Happy Path - UserControllerTest - ActualizarContraseniaUserController */
        log.info("Se inicializan los parametros necesarios");
        String idUsuario = "k5";
        String nuevaContrasenia = "12345";

        log.info("Se mockea el componente que responderá con el valor esperado");
        Mockito.when(userDAO.cambiarContrasenia(Mockito.anyString(), Mockito.anyString())).thenReturn(1L);

        ResponseDTO dto = new ResponseDTO();
        dto.setCode("OK000");
        dto.setStatus("Contrasenia actualizada");
        dto.setResultado(userDAO.cambiarContrasenia(idUsuario, nuevaContrasenia));

        Mockito.when(userService.cambiarContrasenia(Mockito.anyString(), Mockito.anyString())).thenReturn(ResponseEntity.ok(dto));
        log.info("Se implemente los parametros de entrada dentro de un mapa");
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idUsuario", "k5");
        requestParams.add("nuevaContrasenia", "iop");

        log.info("Se arma la petición con los parametros de entrada y el resultado esperado");
        MvcResult result = mockMvc.perform(put("/api/actualizarcontrasenia")
                        .params(requestParams))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK000"))
                .andExpect(jsonPath("$.status").value("Contrasenia actualizada"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult result cambio de contrasenia: " + result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Edge Case - ActualizarContraseniaController - ValidaParametroNuloOVacio")
    public void DadoElServicioActualizaContrasenia_SiRecibeLosParametrosVacios_RegresaBadRequest() throws Exception {
        log.info("Se inicializan los valores de los parametros de entrada");
        //Cambiar valor de idUsuario y nuevaContrasenia a valores como: "" o null
        String idUsuario = "";
        String nuevaContrasenia = "";

        log.info("Se implementan los parametros de entrada a un mapa");
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idUsuario", idUsuario);
        requestParams.add("nuevaContrasenia", nuevaContrasenia);

        log.info("Se estructura la respuesta general mala esperada");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("ER001");
        dto.setStatus("Los parametros son requeridos");

        log.info("Se mockea el servicio que simula el badRequest");
        Mockito.when(userService.cambiarContrasenia("", "")).thenReturn(new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST));

        log.info("Se arma la petición con los parametros de entrada y el resultado esperado");
        MvcResult result = mockMvc.perform(put("/api/actualizarcontrasenia")
                        .params(requestParams))
                .andExpect(status().isBadRequest())
                .andReturn();

        log.info("MvcResult result cambio de contrasenia: "+result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Edge Case - ActualizarContraseniaController - UsuarioNoExistente")
    public void SiRecibeLosParametrosVacios_RegresaRespuestaGeneralErroneaControlada() throws Exception {
        log.info("Se inicializan los parametros simulados no existentes");
        String idUsuarioNoExistente = "000";
        String nuevaContraseniaNoExistente = "43543";

        log.info("Se pasan los parametros a un mapa");
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idUsuario", idUsuarioNoExistente);
        requestParams.add("nuevaContrasenia", nuevaContraseniaNoExistente);

        log.info("Se estructura la respuesta general mala esperada");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("ER001");
        dto.setStatus("Usuario y/o contrasenia no valida");
        dto.setResultado(userDAO.cambiarContrasenia(idUsuarioNoExistente, nuevaContraseniaNoExistente));

        log.info("Componente que devolverá el valor esperado");
        Mockito.when(userDAO.cambiarContrasenia(idUsuarioNoExistente, nuevaContraseniaNoExistente)).thenReturn(0L);

        log.info("Servicio que devolverá el badreuest");
        Mockito.when(userService.cambiarContrasenia(idUsuarioNoExistente, nuevaContraseniaNoExistente)).thenReturn(new ResponseEntity<ResponseDTO>(dto, HttpStatus.BAD_REQUEST));

        log.info("Se arma la petición con los parametros de entrada, todo con el resultado esperado");
        MvcResult result = mockMvc.perform(put("/api/actualizarcontrasenia")
                        .params(requestParams))
                .andExpect(status().isBadRequest())
                .andReturn();

        log.info("MvcResult usuario y/o contrasenia no valida: "+result.getResponse().getContentAsString());
    }

    /* Prueba unitaria de cada endpoint de la API - Busca Usuario por Id */
    @Test
    @DisplayName("Busca Usuario por Id")
    public void DadoElServicioObtenerUsuarioPorId_AlIngresarElIdDelUsuario_BuscaEnLaBDLaExistencia() throws Exception {
        /* Pruebas para Happy Path - UserControllerTest - BuscaUsuarioPorIdUserController */
        log.info("Se crea el usuario esperado");
        Usuario usuarioEntidad = new Usuario("usuarioEncontrado","contraseniaEncontrada");

        Mockito.when(userDAO.obtenerUsuario(Mockito.anyString())).thenReturn(usuarioEntidad);

        log.info("Se estructura la respuesta general esperada");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("OK000");
        dto.setStatus("Usuario encontrado");
        dto.setResultado(userDAO.obtenerUsuario("1"));

        log.info("Servicio con la respuesta general correcta");
        Mockito.when(userService.obtenerUsuarioPoId(Mockito.anyString())).thenReturn(ResponseEntity.ok(dto));

        log.info("Se arma la petición con los parámetros de entrada, todo con el resultado esperado");
        MvcResult result = mockMvc.perform(get("/api/obtenerusuario/{idUsuario}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK000"))
                .andExpect(jsonPath("$.status").value("Usuario encontrado"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult result encontrar usuario por id: " + result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Edge Case - BuscaUsuarioporIdController - ValidaParametroNuloOVacio")
    public void DadoElServicioObtenerUsuarioPorId_AlIngresarElIdDelUsuarioNuloOVacio_RegresaNotFoundElServicio() throws Exception {
        log.info("Se inicializa el parametro del servicio con un valor nulo o vacío");
        // Este servicio responde con NotFound, ya que de inicio no completa la ruta por falta de la pathVariable al ser nulo o ""
        String idUsuario = null;

        log.info("Se arma la petición con los parametros de entrada, todo con el estatus esperado");
        mockMvc.perform(get("/api/obtenerusuario/{idUsuario}", idUsuario))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("Edge Case - BuscaUsuarioporIdController - RespuestaErroneaControlada")
    public void DadoElServicioObtenerUsuarioPorId_AlIngresarElIdDelUsuarioNoExistente_RegresaErrorGeneralControlado() throws Exception {
        log.info("Se inicializa el parametro del servicio");
        String idUsuarioNoExistenteEjemplo = "456";

        log.info("Se estructura la respuesta general con los datos esperados");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("ER001");
        dto.setStatus("No existe este usuario  "+idUsuarioNoExistenteEjemplo);

        log.info("Se mockea el servicio que devolvera la respuesta esperada");
        Mockito.when(userService.obtenerUsuarioPoId(idUsuarioNoExistenteEjemplo)).thenReturn(new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST));

        log.info("Se arma la petición con los parametros de entrada y el resultado esperado");
        MvcResult result = mockMvc.perform(get("/api/obtenerusuario/{idUsuario}", idUsuarioNoExistenteEjemplo))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ER001"))
                .andExpect(jsonPath("$.status").value("No existe este usuario con este id: "+idUsuarioNoExistenteEjemplo))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult result buscar usuario por id: " + result.getResponse().getContentAsString());
    }

    /* Prueba unitaria de cada endpoint de la API - Eliminar Usuario por Id */
    @Test
    @DisplayName("Eliminar Usuario por Id")
    public void DadoElServicioEliminarUsuarioPorId_AlIngresarElIdDelUsuario_BuscaYEliminaDeLaBD() throws Exception {
        /* Pruebas para Happy Path - UserControllerTest - EliminaUsuarioPorIdUserController */
        log.info("Se mockea el componente que devolverá el resultado esperado");
        Mockito.when(userDAO.borrarUsuario(Mockito.anyString())).thenReturn(1L);

        log.info("Se estructura la respuesta general esperada, con sus datos");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("OK000");
        dto.setStatus("Usuario eliminado");
        dto.setResultado(userDAO.borrarUsuario("1"));

        log.info("Servicio que responderá correctamente");
        Mockito.when(userService.borrarUsuarioId(Mockito.anyString())).thenReturn(ResponseEntity.ok(dto));

        log.info("Se arma la petición colos  parámetros de entrada y el resultado esperado");
        MvcResult result = mockMvc.perform(delete("/api/borrarusuario/{idUsuario}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK000"))
                .andExpect(jsonPath("$.status").value("Usuario eliminado"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult result eliminar usuario por id: " + result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Edge Case - EliminaUsuarioPorIdController - ValidaParametroNuloOVacio")
    public void DadoElServicioEliminarUsuarioPorId_AlIngresarUnIdDeUsuarioNuloOVacio_DevuelveNotFoundDelServicio() throws Exception {
        log.info("Se inicializa el parametro esperado con valor nulo o vacío ''");
        // Este servicio responde con NotFound, ya que de inicio no completa la ruta por falta de la pathVariable al ser nulo o ""
        String idUsuario = null;

        log.info("Se arma la petición como si fuera un postman con la ruta y el parametro de entrada, todo con el status esperado");
        mockMvc.perform(delete("/api/borrarusuario/{idUsuario}", idUsuario))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    @DisplayName("Edge Case - EliminaUsuarioPorIdController - RespuestaErroneaControlada")
    public void DadoElServicioEliminarUsuarioPorId_AlIngresarUnIdDeUsuarioNoExistente_DevuelveRespuestaControlada() throws Exception {
        log.info("Se inicializa el parametro esperado");
        String idUsuarioNoExistenteEjemplo = "123";

        log.info("Se estructuira la respuesta general con sus valores esperados");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("ER001");
        dto.setStatus("No existe este usuario con este id: "+idUsuarioNoExistenteEjemplo);

        log.info("Servicio que respondera con la respuesta mala esperada");
        Mockito.when(userService.borrarUsuarioId(idUsuarioNoExistenteEjemplo)).thenReturn(new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST));

        log.info("Se arma la petición con los parámetros de entrada y el status esperado");
        MvcResult result = mockMvc.perform(delete("/api/borrarusuario/{idUsuario}", idUsuarioNoExistenteEjemplo))
                .andExpect(status().isBadRequest())
                .andReturn();

        log.info("MvcResult result eliminar usuario por id: " + result.getResponse().getContentAsString());
    }

    /* Prueba unitaria de cada endpoint de la API - Consumo de api publica por restTemplate */
    @Test
    @DisplayName("Consumo de api publica por restTemplate")
    public void DadoElServicioConsumirApiPublica_AlIngresar_ConsumeElApiNomasXD() throws Exception {
        /* Pruebas para Happy Path - UserControllerTest - ConsumoDeApiPublicaUserController */
        log.info("Se estructura la respuesta general esperada con sus datos necesarios");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("OK000");
        dto.setStatus("Api consumida");

        log.info("Servicio que responde con el valor esperado");
        Mockito.when(userService.consumirApiPublica(Mockito.anyString())).thenReturn(ResponseEntity.ok(dto));

        log.info("Se arma la petición con el resultado esperado");
        MvcResult result = mockMvc.perform(get("/api/apipublica/jsonplaceholder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("OK000"))
                .andExpect(jsonPath("$.status").value("Api consumida"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult result api consumida: " + result.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Edge Case - ConsumeApiPublicaController - RespuestaErroneaControlada")
    public void DadoElServicioConsumirApiPublica_AlIngresar_ConsumeElApiNomasXD1() throws Exception {
        log.info("Se estructura la respuesta esperada");
        ResponseDTO dto = new ResponseDTO();
        dto.setCode("ER001");
        dto.setStatus("Fallo al consumir la api publica");
        ErrorDTO errorDTO = new ErrorDTO("ER001", "Fallo al consumir la api publica");
        dto.setErrors(errorDTO);

        log.info("Servicio que responde con el badRequest");
        Mockito.when(userService.consumirApiPublica(Mockito.anyString())).thenReturn(new ResponseEntity<>(dto, HttpStatus.BAD_REQUEST));

        log.info("Se arma la petición con el resultado esperado");
        MvcResult result = mockMvc.perform(get("/api/apipublica/jsonplaceholder"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("ER001"))
                .andExpect(jsonPath("$.status").value("Fallo al consumir la api pública"))
                .andExpect(content().json(objectMapper.writeValueAsString(dto)))
                .andReturn();

        log.info("MvcResult result fallo al consumir api publica: " + result.getResponse().getContentAsString());
    }
}