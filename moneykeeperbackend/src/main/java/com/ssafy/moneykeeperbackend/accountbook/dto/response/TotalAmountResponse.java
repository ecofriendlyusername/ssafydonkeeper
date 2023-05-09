package com.ssafy.moneykeeperbackend.accountbook.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TotalAmountResponse {

	private int totalIncomeAmount;

	private int totalSpendingAmount;
}
