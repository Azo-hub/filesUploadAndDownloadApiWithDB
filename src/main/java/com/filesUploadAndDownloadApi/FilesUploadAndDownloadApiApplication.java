package com.filesUploadAndDownloadApi;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class FilesUploadAndDownloadApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilesUploadAndDownloadApiApplication.class, args);
	}
	
	@Value("${angularFrontendLocalHostUrl}")
	private String angularFrontendLocalHostUrl;

	@Value("${angularFrontendRemoteUrl}")
	private String angularFrontendRemoteUrl;

	@Bean
	CorsFilter corsFilter() {

		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowCredentials(true);
		// corsConfiguration.setAllowedOrigins(Collections.singletonList(angularFrontendLocalHostUrl));
		corsConfiguration.setAllowedOrigins(Arrays.asList(angularFrontendLocalHostUrl, angularFrontendRemoteUrl));
		corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
				"MediaType", "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
				"Access-Control-Request-Method", "Access-Control-Request-Headers"));
		corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Jwt-Token", "MediaType",
				"Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials","File-Name"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
		return new CorsFilter(urlBasedCorsConfigurationSource);

	}


}
