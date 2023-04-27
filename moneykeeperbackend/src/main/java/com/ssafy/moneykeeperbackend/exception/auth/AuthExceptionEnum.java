package com.ssafy.moneykeeperbackend.exception.auth;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AuthExceptionEnum {
	AUTH_AUTHORIZATION_EXCEPTION(HttpStatus.UNAUTHORIZED, "A0000", "인증되지 않았습니다."),
	KAKAO_SHOULD_HAVE_EMAIL(HttpStatus.UNAUTHORIZED, "A0001", "카카오 이메일 제공에 동의해주셔야 서비스 이용이 가능합니다.");
	private final HttpStatus httpStatus;
	private final String httpCode;
	private final String errorMessage;

	public static Map<String, Object> convertMap(AuthExceptionEnum ex) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", ex.getHttpStatus().value());
		map.put("code", ex.getHttpCode());
		map.put("message", ex.getErrorMessage());

		return map;
	}

}
