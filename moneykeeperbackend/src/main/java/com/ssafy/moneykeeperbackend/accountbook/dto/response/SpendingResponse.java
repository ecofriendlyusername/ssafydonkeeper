package com.ssafy.moneykeeperbackend.accountbook.dto.response;

import com.ssafy.moneykeeperbackend.accountbook.entity.Asset;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpendingResponse {

	private String date;

	private String assetName;

	private String spendingClassificationName;

	private int amount;

	private String detail;

	private String memo;

}
