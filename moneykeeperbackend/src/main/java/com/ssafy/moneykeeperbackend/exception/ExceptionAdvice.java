package com.ssafy.moneykeeperbackend.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookRuntimeException;
import com.ssafy.moneykeeperbackend.exception.auth.AuthRuntimeException;
import com.ssafy.moneykeeperbackend.exception.group.GroupRuntimeException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class ExceptionAdvice {

	@ExceptionHandler({AuthRuntimeException.class})
	public ResponseEntity<ExceptionResponseEntity> authExceptionHandler(
		final AuthRuntimeException runError) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.error("authExceptionHandler: ", runError);

		return new ResponseEntity<>(new ExceptionResponseEntity(
			runError.getErrorEnum().getHttpStatus().value(),
			runError.getErrorEnum().getHttpCode(),
			runError.getMessage()), runError.getErrorEnum().getHttpStatus());
	}

	@ExceptionHandler({AccountBookRuntimeException.class})
	public ResponseEntity<ExceptionResponseEntity> accountBookExceptionHandler(
		final AccountBookRuntimeException runError) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.error("accountBookExceptionHandler: ", runError);

		return new ResponseEntity<>(new ExceptionResponseEntity(
			runError.getErrorEnum().getHttpStatus().value(),
			runError.getErrorEnum().getHttpCode(),
			runError.getMessage()), runError.getErrorEnum().getHttpStatus());
	}

	@ExceptionHandler({GroupRuntimeException.class})
	public ResponseEntity<ExceptionResponseEntity> groupExceptionHandler(
		final GroupRuntimeException runError) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.error("groupExceptionHandler: ", runError);

		return new ResponseEntity<>(new ExceptionResponseEntity(
			runError.getErrorEnum().getHttpStatus().value(),
			runError.getErrorEnum().getHttpCode(),
			runError.getMessage()), runError.getErrorEnum().getHttpStatus());
	}

}
