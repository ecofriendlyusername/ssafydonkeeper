package com.ssafy.moneykeeperbackend.security.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.moneykeeperbackend.exception.auth.AuthRuntimeException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String token = tokenProvider.resolveToken(request);
		try {
			if (token != null && tokenProvider.validateToken(token)) {
				Authentication auth = tokenProvider.getAuthentication(token);
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
			filterChain.doFilter(request, response);
		} catch (AuthRuntimeException e) {
			SecurityContextHolder.clearContext();
			response.setStatus(e.getErrorEnum().getHttpStatus().value());
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			try (OutputStream os = response.getOutputStream()) {
				ObjectMapper objectMapper = new ObjectMapper();

				Map<String, Object> errorResponse = new HashMap<>();
				errorResponse.put("status", e.getErrorEnum().getHttpStatus().value());
				errorResponse.put("message", e.getMessage());

				objectMapper.writeValue(os, errorResponse);
				os.flush();
			}
		} catch (Exception e) {
			filterChain.doFilter(request, response);
		}
	}

}
