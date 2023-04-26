package com.ssafy.moneykeeperbackend.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.moneykeeperbackend.exception.auth.AuthRuntimeException;

@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler({AuthRuntimeException.class})
	private ResponseEntity<ExceptionResponseEntity> authExceptionHandler(
		final AuthRuntimeException runError) {
		return new ResponseEntity<>(
			new ExceptionResponseEntity(
				runError.getErrorEnum().getHttpStatus().value(),
				runError.getErrorEnum().getHttpCode(),
				runError.getMessage()
			),
			runError.getErrorEnum().getHttpStatus());
	}

}
