package com.ssafy.moneykeeperbackend.exception.circle;

import lombok.Getter;

@Getter
public class CircleRuntimeException extends RuntimeException{

	private CircleExxceptionEnum errorEnum;

	public CircleRuntimeException(CircleExxceptionEnum errorEnum) {
		super(errorEnum.getErrorMessage());
		this.errorEnum = errorEnum;
	}

}
