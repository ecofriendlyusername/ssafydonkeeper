package com.ssafy.moneykeeperbackend.accountbook.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IdNameClassificationDTO {

	private Long id;

	private String name;

	private String majorSpendingClassificationName;
}
