package com.ssafy.moneykeeperbackend.accountbook.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BudgetDTO {

	private int year;

	private int month;

	private int amount;
}
