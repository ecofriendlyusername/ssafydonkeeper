package com.ssafy.moneykeeperbackend.exception.group;

import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookExceptionEnum;

import lombok.Getter;

@Getter
public class GroupRuntimeException extends RuntimeException{

	private GroupExceptionEnum errorEnum;

	public GroupRuntimeException(GroupExceptionEnum errorEnum) {
		super(errorEnum.getErrorMessage());
		this.errorEnum = errorEnum;
	}

}
