package com.ssafy.moneykeeperbackend;

import com.ssafy.moneykeeperbackend.record.entity.SpendingClassification;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class MoneykeeperbackendApplication {

	public static void main(String[] args) { SpringApplication.run(MoneykeeperbackendApplication.class, args); }

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("http://127.0.0.1:8081", "http://localhost:8081", "http://localhost:8080")
					.allowedMethods("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS")
					.allowedHeaders("*")
					.allowCredentials(true);
			}
		};
	}


}
