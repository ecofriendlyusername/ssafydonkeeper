package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

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

		if (budgetDTO.getMajorSpendingClassificationId() != null) {
			budget.setMajorSpendingClassification(majorSpendingClassificationRepository.findById(
				budgetDTO.getMajorSpendingClassificationId()).orElseThrow(() -> new AccountBookRuntimeException(
				AccountBookExceptionEnum.MAJOR_SPENDING_CLASSIFICATION_ID_NULL)));

			budgetRepository.save(budget);
		}

		BudgetDTO result = BudgetDTO.builder()
			.year(budget.getYear())
			.month(budget.getMonth())
			.amount(budget.getAmount())
			.build();

		if (budget.getMajorSpendingClassification() != null) {
			result.setMajorSpendingClassificationId(budget.getMajorSpendingClassification().getId());
			result.setMajorSpendingClassificationName(budget.getMajorSpendingClassification().getName());
		}

		return result;
	}

	/*
	 * 월별 소비 분류 별 예산 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Override
	public BudgetDTO getMonthBudget(Member member, int year, int month, Long majorSpendingClassificationId) {

		if (majorSpendingClassificationId != null) {

			MajorSpendingClassification majorSpendingClassification = majorSpendingClassificationRepository.findById(
				majorSpendingClassificationId).orElseThrow(() -> new AccountBookRuntimeException(
				AccountBookExceptionEnum.MAJOR_SPENDING_CLASSIFICATION_ID_NULL));

			Budget budget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationId(member, year,
				month, majorSpendingClassificationId).orElse(null);

			BudgetDTO budgetDTO = BudgetDTO.builder()
				.year(year)
				.month(month)
				.amount(budget != null ? budget.getAmount() : 0)
				.majorSpendingClassificationId(majorSpendingClassification.getId())
				.majorSpendingClassificationName(majorSpendingClassification.getName())
				.build();

			return budgetDTO;

		} else {
			Budget budget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationIsNull(member,
				year, month).orElse(null);

			return BudgetDTO.builder()
				.year(year)
				.month(month)
				.amount(budget == null ? -1 : budget.getAmount())
				.build();
		}
	}

	/*
	 * 월별 예산 전체 가져오기
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public List<BudgetDTO> getMonthAllBudget(Member member, int year, int month) {
		return budgetRepository.findByMemberAndYearAndMonth(member, year, month).stream().map(
			budget -> BudgetDTO.builder()
				.majorSpendingClassificationName(budget.getMajorSpendingClassification() != null ?
					budget.getMajorSpendingClassification().getName() : null)
				.majorSpendingClassificationId(
					budget.getMajorSpendingClassification() != null ? budget.getMajorSpendingClassification().getId() :
						null)
				.month(budget.getMonth())
				.year(budget.getYear())
				.amount(budget.getAmount())
				.build()
		).collect(Collectors.toList());
	}

	/*
	 * 예산 수정
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public BudgetDTO updateBudget(Member member, BudgetDTO budgetDTO) {

		Budget budget = null;

		if (budgetDTO.getMajorSpendingClassificationId() != null) {
			budget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationId(member,
				budgetDTO.getYear(), budgetDTO.getMonth(), budgetDTO.getMajorSpendingClassificationId()).orElse(null);
		} else {
			budget = budgetRepository.findByMemberAndYearAndMonthAndMajorSpendingClassificationIsNull(member,
				budgetDTO.getYear(), budgetDTO.getMonth()).orElse(null);
		}

		if (budget == null) {
			throw new AccountBookRuntimeException(AccountBookExceptionEnum.BUDGET_NULL);
		}

		if (budget.getAmount() != budgetDTO.getAmount()) {
			budget.setAmount(budgetDTO.getAmount());
		}

		Budget resultBudget = budgetRepository.save(budget);

		return BudgetDTO.builder()
			.year(resultBudget.getYear())
			.month(resultBudget.getMonth())
			.amount(resultBudget.getAmount())
			.majorSpendingClassificationId(resultBudget.getMajorSpendingClassification().getId())
			.majorSpendingClassificationName(resultBudget.getMajorSpendingClassification().getName())
			.build();
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
