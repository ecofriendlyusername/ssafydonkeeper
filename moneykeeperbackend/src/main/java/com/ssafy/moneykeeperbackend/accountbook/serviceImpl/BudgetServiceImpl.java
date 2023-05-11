package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.BudgetDTO;
import com.ssafy.moneykeeperbackend.accountbook.entity.Budget;
import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.BudgetRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.BudgetService;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookRuntimeException;
import com.ssafy.moneykeeperbackend.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BudgetServiceImpl implements BudgetService {

	private final BudgetRepository budgetRepository;

	private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;

	/*
	 * 예산 입력
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public BudgetDTO addMonthBudget(BudgetDTO budgetDTO, Member member) {

		Budget budget = budgetRepository.saveAndFlush(Budget.builder()
			.year(budgetDTO.getYear())
			.month(budgetDTO.getMonth())
			.amount(budgetDTO.getAmount())
			.member(member)
			.build());

		if (budgetDTO.getMajorClassificationId() != null) {
			budget.setMajorSpendingClassification(majorSpendingClassificationRepository.findById(
				budgetDTO.getMajorClassificationId()).orElseThrow(() -> new AccountBookRuntimeException(
				AccountBookExceptionEnum.MAJOR_SPENDING_CLASSIFICATION_ID_NULL)));
		}

		BudgetDTO result = BudgetDTO.builder()
			.year(budget.getYear())
			.month(budget.getMonth())
			.amount(budget.getAmount())
			.build();

		if (budget.getMajorSpendingClassification() != null) {
			result.setMajorClassificationId(budget.getMajorSpendingClassification().getId());
			result.setMajorClassificationName(budget.getMajorSpendingClassification().getName());
		}

		return result;
	}

	/*
	 * 월별 전체 예산 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Override
	public BudgetDTO getMonthBudget(Member member, int year, int month) {
		Budget budget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationIsNull(member, year,
			month).orElse(null);

		return BudgetDTO.builder()
			.year(year)
			.month(month)
			.amount(budget == null ? -1 : budget.getAmount())
			.build();
	}

	/*
	 * 월별 전체 예산 수정
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public BudgetDTO updateBudget(Member member, BudgetDTO budgetDTO) {
		Budget budget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationIsNull(member,
				budgetDTO.getYear(), budgetDTO.getMonth())
			.orElse(null);

		if (budget != null && budget.getAmount() != budgetDTO.getAmount()) {
			budget.setAmount(budgetDTO.getAmount());
		}

		Budget resultBudget = budgetRepository.save(budget);

		return BudgetDTO.builder()
			.year(resultBudget.getYear())
			.month(resultBudget.getMonth())
			.amount(resultBudget.getAmount())
			.build();
	}

	/*
	 * 월별 전체 예산 삭제
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public void deleteBudget(Member member, int year, int month) {
		budgetRepository.deleteByMemberAndYearAndMonth(member, year, month);
	}

	/*
	 * 월별 특정 소비 분류 예산 가져오기
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public BudgetDTO addClassificationMonthBudget(BudgetDTO budgetDTO, Member member) {
		Budget budget = budgetRepository.saveAndFlush(Budget.builder()
			.year(budgetDTO.getYear())
			.month(budgetDTO.getMonth())
			.amount(budgetDTO.getAmount())
			.member(member)
			.majorSpendingClassification(majorSpendingClassificationRepository.findById(
				budgetDTO.getMajorClassificationId()).orElseThrow(() -> new AccountBookRuntimeException(
				AccountBookExceptionEnum.MAJOR_SPENDING_CLASSIFICATION_ID_NULL)))
			.build());

		return BudgetDTO.builder()
			.year(budget.getYear())
			.month(budget.getMonth())
			.amount(budget.getAmount())
			.majorClassificationId(budget.getMajorSpendingClassification().getId())
			.majorClassificationName(budget.getMajorSpendingClassification().getName())
			.build();
	}
}
