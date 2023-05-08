package com.ssafy.moneykeeperbackend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExceptionResponseEntity {
	private int status;
	private String code;
	private String message;

}
