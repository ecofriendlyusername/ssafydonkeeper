package com.ssafy.moneykeeperbackend.accountbook.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.BudgetDTO;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface BudgetService {

	BudgetDTO upsertMonthBudget(BudgetDTO budgetDTO, Member member);

	BudgetDTO getMonthAllBudget(Member member, int year, int month);

	void deleteBudget(Member member, int year, int month, Long majorSpendingClassificationId);
}
