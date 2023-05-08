package com.ssafy.moneykeeperbackend.security.config;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ssafy.moneykeeperbackend.security.TokenProvider;
import com.ssafy.moneykeeperbackend.security.exception.CustomAccessDeniedHandler;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableSpringHttpSession
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final TokenProvider tokenProvider;
	private final CustomAccessDeniedHandler customAccessDeniedHandler;

	private final CustomUserDetailService customUserDetailService;

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration
	) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	/*
	 * spring session을 인메모리 형식으로 사용하기 위한 설정
	 *
	 * @date 2023.04.27
	 * @author 정민지
	 * */
	@Bean
	public SessionRepository<?> sessionRepository() {
		return new MapSessionRepository(new ConcurrentHashMap<>());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.cors()

			.and()
			.csrf().disable()

			.httpBasic()

			.and()
			.headers()
			.frameOptions()
			.sameOrigin()

			.and()
			.formLogin().disable()
			.authorizeRequests()
			.antMatchers("/api/auth/kakao/callback").permitAll()
					.anyRequest().permitAll()
			.and()
			.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)

			.and()
			.apply(new JwtSecurityConfig(tokenProvider))

			.and()
			.userDetailsService(customUserDetailService);

		http.headers().frameOptions().disable();
		return http.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
					.allowedOrigins("http://127.0.0.1:8081", "http://localhost:8081", "http://localhost:8080", "http://127.0.0.1:8080",
						"http://127.0.0.1:3000", "http://localhost:3000")
					.allowedMethods("*")
					.allowedHeaders("*")
					.allowCredentials(true);
			}
		};
	}


}

//			.antMatchers("/api/auth/kakao/callback").permitAll()
//					.anyRequest().permitAll()
