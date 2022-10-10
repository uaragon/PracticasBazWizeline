package com.baz.wizeline.javamaven.controller;

import com.baz.wizeline.javamaven.configuration.MyWebConfig;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;


@ContextConfiguration(classes = MyWebConfig.class)
@WebAppConfiguration
class ApiPublicaControllerTest {
    public static final Logger logger = LoggerFactory.getLogger(ApiPublicaControllerTest.class);
    //Contiene el test del consumo de API Pública
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Test
    //Valida JSON del request
    public void testJsonController() throws Exception {
        logger.info("Se declara una variable para validar el JSON");
        long id = 1;
        logger.info("Se construye el request a validar");
        MockHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.put("/api/buscarComentarios/" + id)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(getArticleInJson(1));
        this.mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.status()
                        .isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("Comentario encontrado."))
                .andDo(MockMvcResultHandlers.print());
        logger.info("Se imprime el resultado");
    }

    private String getArticleInJson(long id) {
        logger.info("Se envía id del comentario para prueba");
        return "{\"id\":\"" + id + "\", \"content\":\"test data\"}";

    }
}