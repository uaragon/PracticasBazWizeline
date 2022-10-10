package com.baz.wizeline.javamaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication(scanBasePackages={"com.baz.wizeline.javamaven"})
@ComponentScan("com.baz.wizeline.javamaven")
@EnableFeignClients
@EntityScan("com.baz.wizeline.javamaven")

public class JavaMavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaMavenApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(3000);
		factory.setReadTimeout(3000);
		return new RestTemplate(factory);
	}

}
