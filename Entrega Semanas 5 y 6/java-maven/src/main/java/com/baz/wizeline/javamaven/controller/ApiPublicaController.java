package com.baz.wizeline.javamaven.controller;

import com.baz.wizeline.javamaven.model.ApiPublicaModel;
import com.baz.wizeline.javamaven.servicios.ApiPublicaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador para invocar API Pública
 */
@RestController
@Component
public class ApiPublicaController {
    public static final Logger logger = LoggerFactory.getLogger(ApiPublicaController.class);
    @Autowired
    @Qualifier("ApiPublicaService")
    private ApiPublicaService apiPublicaService;


    /**
     * Buscar comentarios con exchange.
     */
    @GetMapping(path = "/buscarComentarios",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ApiPublicaModel>> buscarComentariosConExchange() {

        logger.info("Ingresa a metodo buscarComentariosConExchange");

        return apiPublicaService.buscarComentariosConExchange();
    }


    /**
     * Buscar comentarios.
     */
    @GetMapping(path = "/buscarComentariosConForEntity",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ApiPublicaModel>> buscarComentariosConForEntity() {

        logger.info("Ingresa a metodo buscarComentariosConForEntity");

        return new ResponseEntity<>(apiPublicaService.buscarComentariosConForEntity(), HttpStatus.OK);
    }

    /**
     * Buscar comentarios con for object.
     */
    @GetMapping(path = "/buscarComentariosConForObject",
            produces = {MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<ApiPublicaModel>> buscarComentariosConForObject() {

        logger.info("Ingresa a metodo buscarComentariosConForObject");

        return new ResponseEntity<>(apiPublicaService.buscarComentariosConForObject(), HttpStatus.OK);
    }




}
