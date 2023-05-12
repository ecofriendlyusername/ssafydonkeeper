package com.ssafy.moneykeeperbackend.accountbook.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BudgetDTO {

	private int year;

	private int month;

	private int amount;

	private String majorSpendingClassificationName;

	private Long majorSpendingClassificationId;

}
