package com.evaluacion.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class corsConfig {

	@Bean
	 public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		
		//permitir solicitudes desde todos los origenes
		config.addAllowedOrigin("*");
		//config.addAllowedOrigin("http://127.0.0.1:5501/");
		//config.addAllowedOrigin("http://127.0.0.1:5501/");
		
		//Permitir solicitudes con estos metodos HTTP
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		
		//PERMITIR EL ENVIO DE CIERTOS ENCABEZADOS EN LAS SOLICITUDES
		config.addAllowedHeader("Authorization");
		config.addAllowedHeader("Content-Type");
		
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
		
	}
}