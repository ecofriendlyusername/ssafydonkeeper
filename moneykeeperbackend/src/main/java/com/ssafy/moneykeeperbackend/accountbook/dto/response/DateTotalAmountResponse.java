package com.ssafy.moneykeeperbackend.accountbook.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DateTotalAmountResponse {

	private String date;

	private int incomeAmount;

	private int spendingAmount;
}
