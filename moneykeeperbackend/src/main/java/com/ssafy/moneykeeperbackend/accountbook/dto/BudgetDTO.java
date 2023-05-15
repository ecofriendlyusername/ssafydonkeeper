package com.ssafy.moneykeeperbackend.accountbook.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BudgetDTO {

	private int year;

	private int month;

	private int total_amount;

	private List<MajorSpendingClassificationAmountDTO> datas;

}
