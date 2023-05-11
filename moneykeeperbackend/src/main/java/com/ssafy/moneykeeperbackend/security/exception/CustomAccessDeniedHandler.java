package com.ssafy.moneykeeperbackend.security.exception;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.moneykeeperbackend.exception.auth.AuthExceptionEnum;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
		AccessDeniedException accessDeniedException) throws IOException, ServletException {

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(HttpStatus.FORBIDDEN.value());

		Map<String, String> errorMap = new HashMap<>();
		// errorMap.put("message", AuthExceptionEnum.REQUIRED_RE_LOGIN.getErrorMessage());
		errorMap.put("message", "안냥");
		errorMap.put("status", String.valueOf(AuthExceptionEnum.REQUIRED_RE_LOGIN.getHttpStatus()));
		errorMap.put("code", String.valueOf(AuthExceptionEnum.REQUIRED_RE_LOGIN.getHttpCode()));

		ObjectMapper objectMapper = new ObjectMapper();
		OutputStream out = response.getOutputStream();
		objectMapper.writeValue(out, errorMap);
		out.flush();
	}
}
