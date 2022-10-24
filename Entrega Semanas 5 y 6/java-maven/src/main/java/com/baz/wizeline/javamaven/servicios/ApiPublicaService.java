package com.baz.wizeline.javamaven.servicios;

import com.baz.wizeline.javamaven.model.ApiPublicaModel;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * The Interface ApiPublicaService.
 */
public interface ApiPublicaService {

	/**
	 * Buscar comentarios con exchange.
	 */
	ResponseEntity<List<ApiPublicaModel>> buscarComentariosConExchange();

	/**
	 * Buscar comentarios con fonr entity.
	 */
	List<ApiPublicaModel> buscarComentariosConForEntity();

	/**
	 * Buscar comentarios con for object.
	 */
	List<ApiPublicaModel> buscarComentariosConForObject();

}
