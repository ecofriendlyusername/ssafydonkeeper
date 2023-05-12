package com.ssafy.moneykeeperbackend.accountbook.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IncomeRequest {

	private String date;

	private Long assetId;

	private Long classificationId;

	private int amount;

	private String detail;

	private String memo;

}
