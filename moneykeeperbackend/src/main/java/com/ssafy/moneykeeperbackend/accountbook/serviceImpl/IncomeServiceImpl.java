package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.request.IncomeRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.IncomeResponse;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.SpendingResponse;
import com.ssafy.moneykeeperbackend.accountbook.entity.Asset;
import com.ssafy.moneykeeperbackend.accountbook.entity.Income;
import com.ssafy.moneykeeperbackend.accountbook.entity.IncomeClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.AssetRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.IncomeClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.IncomeRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.IncomeService;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookRuntimeException;
import com.ssafy.moneykeeperbackend.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomeServiceImpl implements IncomeService {

	private final IncomeClassificationRepository incomeClassificationRepository;

	private final AssetRepository assetRepository;

	private final IncomeRepository incomeRepository;


	/*
	 * 소비 내역 입력
	 *
	 * @date 2023.04.28
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public IncomeResponse addIncomeRecord(IncomeRequest incomeRequest, Member member) {

		Income income = Income.builder()
			.incomeClassification(findIncomeClassificationById(incomeRequest.getIncomeClassificationId()))
			.asset(findAssetById(incomeRequest.getAssetId()))
			.memo(incomeRequest.getMemo())
			.amount(incomeRequest.getAmount())
			.date(LocalDate.parse(incomeRequest.getDate(), DateTimeFormatter.ISO_DATE))
			.detail(incomeRequest.getDetail())
			.member(member)
			.build();

		Income resultIncome = incomeRepository.saveAndFlush(income);

		return IncomeResponse.builder()
			.incomeId(resultIncome.getId())
			.amount(resultIncome.getAmount())
			.incomeClassificationName(resultIncome.getIncomeClassification().getName())
			.assetName(resultIncome.getAsset().getName())
			.date(resultIncome.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.detail(resultIncome.getDetail())
			.memo(resultIncome.getMemo())
			.build();

	}

	/*
	 * id로 IncomeClassification 찾기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	private IncomeClassification findIncomeClassificationById(Long id) {
		return incomeClassificationRepository.findById(id)
			.orElseThrow(
				() -> new AccountBookRuntimeException(AccountBookExceptionEnum.INCOME_CLASSIFICATION_ID_NULL));
	}

	/*
	 * id로 Asset 찾기
	 *
	 * @date 2023.04.28
	 * @author 정민지
	 * */
	private Asset findAssetById(Long id) {
		return assetRepository.findById(id)
			.orElseThrow(() -> new AccountBookRuntimeException(AccountBookExceptionEnum.ASSET_ID_NULL));
	}

	/*
	 * 전체 수입 내역 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@Override
	public Page<IncomeResponse> getAllIncome(Member member, Pageable pageable) {
		Page<Income> incomes = incomeRepository.findAllByMember(member, pageable);
		Page<IncomeResponse> incomeResponses = incomes.map(income -> IncomeResponse.builder()
			.incomeId(income.getId())
			.incomeClassificationName(income.getIncomeClassification().getName())
			.amount(income.getAmount())
			.assetName(income.getAsset().getName())
			.date(income.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.detail(income.getDetail())
			.memo(income.getMemo())
			.build());
		return incomeResponses;
	}

	/*
	 * 특정 달 소비내역 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@Override
	public Page<IncomeResponse> getMonthIncome(Member member, int year, int month, Pageable pageable) {
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

		Page<Income> incomes = incomeRepository.findAllByMemberAndDateBetween(member, startDate, endDate, pageable);
		Page<IncomeResponse> incomeResponses = incomes.map(income -> IncomeResponse.builder()
			.incomeId(income.getId())
			.incomeClassificationName(income.getIncomeClassification().getName())
			.amount(income.getAmount())
			.assetName(income.getAsset().getName())
			.date(income.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.detail(income.getDetail())
			.memo(income.getMemo())
			.build());
		return incomeResponses;
	}

	/*
	 * 소비내역 디테일 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@Override
	public IncomeResponse getDetailIncome(Member member, Long incomeId) {

		Income income = incomeRepository.findById(incomeId).orElseThrow(() -> new AccountBookRuntimeException(AccountBookExceptionEnum.INCOME_ID_NULL));

		return IncomeResponse.builder()
			.incomeId(income.getId())
			.amount(income.getAmount())
			.incomeClassificationName(income.getIncomeClassification().getName())
			.assetName(income.getAsset().getName())
			.date(income.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.detail(income.getDetail())
			.memo(income.getMemo())
			.build();
	}
	//
	// /*
	//  * 특정 달 소비 금액 가져오기
	//  *
	//  * @date 2023.05.04
	//  * @author 정민지
	//  * */
	// @Override
	// public int getMonthSpendingAmount(Member member, int year, int month) {
	// 	LocalDate startDate = LocalDate.of(year, month, 1);
	// 	LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
	//
	// 	return spendingRepository.getTotalAmountByMemberAndDateBetween(member, startDate, endDate);
	// }
	//
	//
	// /*
	//  * 특정 소비 내역 수정
	//  *
	//  * @date 2023.05.04
	//  * @author 정민지
	//  * */
	// @Transactional
	// @Override
	// public SpendingResponse updateSpending(Member member, Long spendingId, SpendingRequest spendingRequest) {
	// 	Spending spending = spendingRepository.findById(spendingId).orElseThrow(() -> new AccountBookRuntimeException(AccountBookExceptionEnum.SPENDING_ID_NULL));
	//
	// 	if (spendingRequest.getDate() != null && !spending.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(spendingRequest.getDate())) {
	// 		spending.setDate(LocalDate.parse(spendingRequest.getDate(), DateTimeFormatter.ISO_DATE));
	// 	}
	// 	if (spendingRequest.getAmount() != 0 && spendingRequest.getAmount() != spending.getAmount()) {
	// 		spending.setAmount(spendingRequest.getAmount());
	// 	}
	// 	if (spendingRequest.getAssetId() != null && spendingRequest.getAssetId() != spending.getAsset().getId()) {
	// 		spending.setAsset(findAssetById(spendingRequest.getAssetId()));
	// 	}
	// 	if (spendingRequest.getSpendingClassificationId() != null && spendingRequest.getSpendingClassificationId() != spending.getSpendingClassification().getId()) {
	// 		spending.setSpendingClassification(findSpendingClassificationById(spendingRequest.getSpendingClassificationId()));
	// 	}
	// 	if (spendingRequest.getDetail() != null && !spendingRequest.getDetail().equals(spending.getDetail())) {
	// 		spending.setDetail(spendingRequest.getDetail());
	// 	}
	// 	if (spendingRequest.getMemo() != null && !spendingRequest.getMemo().equals(spending.getMemo())) {
	// 		spending.setMemo(spendingRequest.getMemo());
	// 	}
	//
	// 	Spending resultSpending = spendingRepository.save(spending);
	//
	// 	return SpendingResponse.builder()
	// 		.spendingId(resultSpending.getId())
	// 		.amount(resultSpending.getAmount())
	// 		.spendingClassificationName(resultSpending.getSpendingClassification().getName())
	// 		.assetName(resultSpending.getAsset().getName())
	// 		.date(resultSpending.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
	// 		.detail(resultSpending.getDetail())
	// 		.memo(resultSpending.getMemo())
	// 		.build();
	// }
	//
	// /*
	//  * 특정 소비 내역 삭제
	//  *
	//  * @date 2023.05.04
	//  * @author 정민지
	//  * */
	// public void deleteSpending(Long spendingId) {
	// 	spendingRepository.deleteById(spendingId);
	// }
}
