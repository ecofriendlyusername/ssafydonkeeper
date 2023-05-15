package com.ssafy.moneykeeperbackend.accountbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SpendingClassificationNameAmountDTO {

	private Long classificationId;

	private String name;
	private int amount;
}
