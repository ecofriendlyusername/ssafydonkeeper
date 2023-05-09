package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.BudgetDTO;
import com.ssafy.moneykeeperbackend.accountbook.entity.Budget;
import com.ssafy.moneykeeperbackend.accountbook.repository.BudgetRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.BudgetService;
import com.ssafy.moneykeeperbackend.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

	private final BudgetRepository budgetRepository;

	/*
	 * 예산 입력
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public BudgetDTO addMonthBudget(BudgetDTO budgetDTO, Member member) {

		Budget resultBudget = budgetRepository.saveAndFlush(Budget.builder()
			.year(budgetDTO.getYear())
			.month(budgetDTO.getMonth())
			.amount(budgetDTO.getAmount())
			.member(member)
			.build());

		return BudgetDTO.builder()
			.year(resultBudget.getYear())
			.month(resultBudget.getMonth())
			.amount(resultBudget.getAmount())
			.build();
	}

	/*
	 * 특정 달 예산 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Override
	public BudgetDTO getMonthBudget(Member member, int year, int month) {
		Budget budget = budgetRepository.findByMemberAndYearAndMonth(member, year, month).orElse(null);

		return BudgetDTO.builder()
			.year(year)
			.month(month)
			.amount(budget == null ? -1 : budget.getAmount())
			.build();
	}

	/*
	 * 특정 달 예산 수정
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public BudgetDTO updateBudget(Member member, BudgetDTO budgetDTO) {
		Budget budget = budgetRepository.findByMemberAndYearAndMonth(member, budgetDTO.getYear(), budgetDTO.getMonth())
			.orElse(null);
		Budget resultBudget = null;

		if (budget != null && budget.getAmount() != budgetDTO.getAmount()) {
			budget.setAmount(budgetDTO.getAmount());
			resultBudget = budgetRepository.save(budget);
		}

		return BudgetDTO.builder()
			.year(budgetDTO.getYear())
			.month(budgetDTO.getMonth())
			.amount(resultBudget.getAmount())
			.build();
	}

	/*
	 * 특정 달 예산 삭제
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public void deleteBudget(Member member, int year, int month) {
		budgetRepository.deleteByMemberAndYearAndMonth(member, year, month);
	}
}
