package com.ssafy.moneykeeperbackend.accountbook.dto.request;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class SpendingRequest {

	private String date;

	private Long assetId;

	private Long spendingClassificationId;

	private int amount;

	private String detail;

	private String memo;

}
