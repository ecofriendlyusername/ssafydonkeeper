package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.BudgetDTO;
import com.ssafy.moneykeeperbackend.accountbook.dto.MajorSpendingClassificationAmountDTO;
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
	 * 예산 입력 또는 수정
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public BudgetDTO upsertMonthBudget(BudgetDTO budgetDTO, Member member) {
		List<MajorSpendingClassificationAmountDTO> majorSpendingClassificationAmountDTOS = budgetDTO.getDatas();

		// 전체 예산이 있으면 수정, 없으면 등록
		Budget budget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationIsNull(member,
			budgetDTO.getYear(), budgetDTO.getMonth()).orElse(null);

		if (budget == null) {
			budgetRepository.saveAndFlush(Budget.builder()
				.year(budgetDTO.getYear())
				.month(budgetDTO.getMonth())
				.amount(budgetDTO.getTotal_amount())
				.member(member)
				.build());
		} else {
			budget.setAmount(budgetDTO.getTotal_amount());
			budgetRepository.saveAndFlush(budget);
		}

		// 소비 분류 별 예산이 있으면 수정, 없으면 등록
		for (MajorSpendingClassificationAmountDTO majorSpendingClassificationAmountDTO : majorSpendingClassificationAmountDTOS) {
			Budget classificationBudget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationId(
				member, budgetDTO.getYear(), budgetDTO.getMonth(),
				majorSpendingClassificationAmountDTO.getClassificationId()).orElse(null);

			if (classificationBudget == null) {
				budgetRepository.saveAndFlush(Budget.builder()
					.year(budgetDTO.getYear())
					.month(budgetDTO.getMonth())
					.member(member)
					.amount(majorSpendingClassificationAmountDTO.getAmount())
					.majorSpendingClassification(majorSpendingClassificationRepository.findById(
							majorSpendingClassificationAmountDTO.getClassificationId())
						.orElseThrow(() -> new AccountBookRuntimeException(
							AccountBookExceptionEnum.MAJOR_SPENDING_CLASSIFICATION_ID_NULL)))
					.build());
			} else {
				classificationBudget.setAmount(majorSpendingClassificationAmountDTO.getAmount());
				budgetRepository.saveAndFlush(classificationBudget);
			}
		}

		Budget resultTotalBudget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationIsNull(
			member,
			budgetDTO.getYear(), budgetDTO.getMonth()).orElse(null);

		BudgetDTO resultBudgetDTO = BudgetDTO.builder()
			.year(budgetDTO.getYear())
			.month(budgetDTO.getMonth())
			.total_amount(resultTotalBudget != null ? resultTotalBudget.getAmount() : 0)
			.build();

		List<Budget> resultClassificationBudget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationIsNotNull(
			member, budgetDTO.getYear(), budgetDTO.getMonth());

		List<MajorSpendingClassificationAmountDTO> resultMajorSpendingClassificationAmountDTOs = new ArrayList<>();
		for (Budget forBudget : resultClassificationBudget) {
			resultMajorSpendingClassificationAmountDTOs.add(
				MajorSpendingClassificationAmountDTO.builder()
					.classificationId(forBudget.getMajorSpendingClassification().getId())
					.amount(forBudget.getAmount())
					.build()
			);
		}

		resultBudgetDTO.setDatas(resultMajorSpendingClassificationAmountDTOs);
		return resultBudgetDTO;
	}

	/*
	 * 월별 예산 전체 가져오기
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Override
	public BudgetDTO getMonthAllBudget(Member member, int year, int month) {
		Budget resultTotalBudget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationIsNull(
			member, year, month).orElse(null);

		BudgetDTO resultBudgetDTO = BudgetDTO.builder()
			.year(year)
			.month(month)
			.total_amount(resultTotalBudget != null ? resultTotalBudget.getAmount() : 0)
			.build();

		List<Budget> resultClassificationBudget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationIsNotNull(
			member, year, month);

		List<MajorSpendingClassificationAmountDTO> resultMajorSpendingClassificationAmountDTOs = new ArrayList<>();
		for (Budget forBudget : resultClassificationBudget) {
			resultMajorSpendingClassificationAmountDTOs.add(
				MajorSpendingClassificationAmountDTO.builder()
					.classificationId(forBudget.getMajorSpendingClassification().getId())
					.amount(forBudget.getAmount())
					.build()
			);
		}

		resultBudgetDTO.setDatas(resultMajorSpendingClassificationAmountDTOs);
		return resultBudgetDTO;
	}

	/*
	 * 예산 삭제
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public void deleteBudget(Member member, int year, int month, Long majorSpendingClassificationId) {

		if (majorSpendingClassificationId == null) {
			budgetRepository.deleteByMemberAndYearAndMonth(member, year, month);
		} else {
			budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationId(member, year, month,
					majorSpendingClassificationId)
				.orElseThrow(() -> new AccountBookRuntimeException(AccountBookExceptionEnum.BUDGET_NULL));

			budgetRepository.deleteByMemberAndYearAndMonthAndMajorSpendingClassificationId(member, year, month,
				majorSpendingClassificationId);
		}

	}
}
