package com.ssafy.moneykeeperbackend.accountbook.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MonthTatalAmountResponse {

	private String month;

	private List<DateTotalAmountResponse> details;

	private TotalAmountResponse total;
}
