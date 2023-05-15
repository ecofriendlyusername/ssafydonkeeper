package com.ssafy.moneykeeperbackend.accountbook.dto.response;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.SpendingClassificationAmountDTO;
import com.ssafy.moneykeeperbackend.accountbook.dto.SpendingClassificationNameAmountDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class BudgetResponse {

	private int year;

	private int month;

	private int total_amount;

	private List<SpendingClassificationNameAmountDTO> datas;
}
