package com.ssafy.moneykeeperbackend.exception;

import com.ssafy.moneykeeperbackend.exception.statistics.FailedToGenerateRecordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookRuntimeException;
import com.ssafy.moneykeeperbackend.exception.auth.AuthRuntimeException;
import com.ssafy.moneykeeperbackend.exception.circle.CircleRuntimeException;

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

	@ExceptionHandler({CircleRuntimeException.class})
	public ResponseEntity<ExceptionResponseEntity> circleExceptionHandler(
		final CircleRuntimeException runError) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.error("circleExceptionHandler: ", runError);

		return new ResponseEntity<>(new ExceptionResponseEntity(
			runError.getErrorEnum().getHttpStatus().value(),
			runError.getErrorEnum().getHttpCode(),
			runError.getMessage()), runError.getErrorEnum().getHttpStatus());
	}

	@ExceptionHandler({FailedToGenerateRecordException.class})
	public ResponseEntity<ExceptionResponseEntity> failedToGenerateRecordExceptionHandler(
			final FailedToGenerateRecordException runError) {
		Logger logger = LoggerFactory.getLogger(this.getClass());
		logger.error("failedToGenerateExceptionHandler: ", runError);

		return new ResponseEntity<>(new ExceptionResponseEntity(
				500,
				"failed to generate exception",
				runError.getMessage()), null);
	}

}
