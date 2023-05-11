package com.ssafy.moneykeeperbackend.accountbook.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpendingClassificationResponse {
	private Long id;

	private String name;

	private String majorSpendingClassificationName;
}
