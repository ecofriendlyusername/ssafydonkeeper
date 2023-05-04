package com.ssafy.moneykeeperbackend.accountbook.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class IncomeResponse {

	private Long incomeId;

	private String date;

	private String assetName;

	private String incomeClassificationName;

	private int amount;

	private String detail;

	private String memo;

}
