package com.baz.wizeline.javamaven.servicios.impl;

import com.baz.wizeline.javamaven.model.ApiPublicaModel;
import com.baz.wizeline.javamaven.servicios.ApiPublicaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * The Class OpenApiServiceImpl.
 */
@Service("ApiPublicaService")
public class ApiPublicaServiceImpl implements ApiPublicaService {

	public static final Logger logger = LoggerFactory.getLogger(ApiPublicaServiceImpl.class);
	@Autowired
	private RestTemplate restTemplate;
	@Override
	public ResponseEntity<List<ApiPublicaModel>> buscarComentariosConExchange() {

		logger.info("Inicia Busqueda Comentarios Con Exchange");

		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

		HttpEntity<ApiPublicaModel> entity = new HttpEntity<>(headers);

		ResponseEntity<List<ApiPublicaModel>> response = restTemplate.exchange(
				"https://jsonplaceholder.typicode.com/comments", HttpMethod.GET, entity,
				new ParameterizedTypeReference<List<ApiPublicaModel>>() {
				});

		logger.info("Response: {}", response.getBody());

		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ApiPublicaModel> buscarComentariosConForEntity() {

		logger.info("Inicia Busqueda Comentarios Con For Entity");

		restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				return execution.execute(request, body);
			}
		});

		ResponseEntity<List> response = restTemplate.getForEntity("https://jsonplaceholder.typicode.com/comments",
				List.class);

		logger.info("Response: {}", response.getBody());

		List<ApiPublicaModel> object = response.getBody();

		return object;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ApiPublicaModel> buscarComentariosConForObject() {

		logger.info("Inicia Busqueda Comentarios Con For Object");

		restTemplate.getInterceptors().add(new ClientHttpRequestInterceptor() {
			@Override
			public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
					throws IOException {
				request.getHeaders().setContentType(MediaType.APPLICATION_JSON);
				request.getHeaders().setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				return execution.execute(request, body);
			}
		});

		List<ApiPublicaModel> response = restTemplate.getForObject("https://jsonplaceholder.typicode.com/comments",
				List.class);

		logger.info("Response: {}", response);

		List<ApiPublicaModel> object = response;

		return object;
	}

}
