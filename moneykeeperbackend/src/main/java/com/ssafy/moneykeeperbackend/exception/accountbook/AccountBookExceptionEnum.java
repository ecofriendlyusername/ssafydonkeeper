package com.ssafy.moneykeeperbackend.exception.accountbook;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.ssafy.moneykeeperbackend.exception.auth.AuthExceptionEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum AccountBookExceptionEnum {

	SPENDING_CLASSIFICATION_ID_NULL(HttpStatus.NOT_FOUND, "B0000", "해당 아이디의 소비 분류가 없습니다. 확인해주세요."),

	ASSET_ID_NULL(HttpStatus.NOT_FOUND, "B0001", "해당 아이디의 자산 분류가 없습니다. 확인해주세요."),

	SPENDING_ID_NULL(HttpStatus.NOT_FOUND, "B0001", "해당 아이디의 소비 내역이 없습니다. 확인해주세요.");
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
