package com.ssafy.moneykeeperbackend.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.ssafy.moneykeeperbackend.security.TokenProvider;
import com.ssafy.moneykeeperbackend.security.exception.CustomAccessDeniedHandler;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetailService;

import lombok.RequiredArgsConstructor;

@Configuration
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

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http
			.formLogin().disable()
			.authorizeRequests()
			.antMatchers("/**").permitAll()
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

}
//			.antMatchers("/api/auth/kakao/callback").permitAll()
//			.anyRequest().authenticated()

//			.antMatchers("/**").permitAll()
//					.anyRequest().permitAll()