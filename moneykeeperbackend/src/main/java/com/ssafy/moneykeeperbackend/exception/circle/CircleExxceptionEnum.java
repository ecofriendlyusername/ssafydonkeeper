package com.ssafy.moneykeeperbackend.exception.circle;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CircleExxceptionEnum {
	EXISTS_CIRCLE_NAME(HttpStatus.NOT_FOUND, "C0001", "이미 존재하는 그룹 이름입니다."),

	CIRCLE_ID_NULL(HttpStatus.NOT_FOUND, "C0002", "존재하지 않는 그룹 아이디입니다."),

	CIRCLE_ID_AND_MEMBER_RELATION_NULL(HttpStatus.NOT_FOUND, "C0003", "해당 그룹 아이디와 현재 로그인된 멤버의 연관 관계가 존재하지 않습니다."),

	MEMBER_IS_NOT_CIRCLE_LEADER(HttpStatus.NOT_FOUND, "C0004", "현재 멤버가 CIRCLE 생성자가 아닙니다. 권한이 없습니다.");
	private final HttpStatus httpStatus;
	private final String httpCode;
	private final String errorMessage;

	public static Map<String, Object> convertMap(CircleExxceptionEnum ex) {
		Map<String, Object> map = new HashMap<>();
		map.put("status", ex.getHttpStatus().value());
		map.put("code", ex.getHttpCode());
		map.put("message", ex.getErrorMessage());

		return map;
	}
}
