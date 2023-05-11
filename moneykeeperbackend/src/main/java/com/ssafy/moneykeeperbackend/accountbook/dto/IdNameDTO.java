package com.ssafy.moneykeeperbackend.accountbook.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdNameDTO {

	private Long id;

	private String name;

}
