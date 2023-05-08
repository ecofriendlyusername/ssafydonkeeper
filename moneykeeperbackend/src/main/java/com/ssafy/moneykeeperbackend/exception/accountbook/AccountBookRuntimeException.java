package com.ssafy.moneykeeperbackend.exception.accountbook;

import lombok.Getter;

@Getter
public class AccountBookRuntimeException extends RuntimeException{
	private AccountBookExceptionEnum errorEnum;

	public AccountBookRuntimeException(AccountBookExceptionEnum errorEnum) {
		super(errorEnum.getErrorMessage());
		this.errorEnum = errorEnum;
	}
}
