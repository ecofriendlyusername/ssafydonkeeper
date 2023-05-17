package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.moneykeeperbackend.statistics.service.ProcessRecordService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.SpendingResponse;
import com.ssafy.moneykeeperbackend.accountbook.entity.Asset;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.AssetRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookRuntimeException;
import com.ssafy.moneykeeperbackend.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpendingServiceImpl implements SpendingService {

	private final ProcessRecordService processRecordService;

	private final SpendingRepository spendingRepository;

	private final SpendingClassificationRepository spendingClassificationRepository;

	private final AssetRepository assetRepository;

	/*
	 * 소비 내역 입력
	 *
	 * @date 2023.04.28
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public SpendingResponse addSpendingRecord(SpendingRequest spendingRequest, Member member) {

		Spending spending = Spending.builder()
			.member(member)
			.spendingClassification(findSpendingClassificationById(spendingRequest.getClassificationId()))
			.date(LocalDate.parse(spendingRequest.getDate(), DateTimeFormatter.ISO_DATE))
			.asset(findAssetById(spendingRequest.getAssetId()))
			.amount(spendingRequest.getAmount())
			.memo(spendingRequest.getMemo())
			.detail(spendingRequest.getDetail())
			.build();

		spending = spendingRepository.saveAndFlush(spending);

		// TODO: 가영님 주석 풀기 필요
		processRecordService.processNewSpending(spending,member);

		return SpendingResponse.builder()
			.spendingId(spending.getId())
			.amount(spending.getAmount())
			.spendingClassificationName(spending.getSpendingClassification().getName())
			.assetName(spending.getAsset().getName())
			.date(spending.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.detail(spending.getDetail())
			.memo(spending.getMemo())
			.build();
	}

	/*
	 * id로 SpendingClassification 찾기
	 *
	 * @date 2023.04.28
	 * @author 정민지
	 * */
	private SpendingClassification findSpendingClassificationById(Long id) {
		return spendingClassificationRepository.findById(id)
			.orElseThrow(
				() -> new AccountBookRuntimeException(AccountBookExceptionEnum.SPENDING_CLASSIFICATION_ID_NULL));
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
	 * 전체 소비내역 가져오기
	 *
	 * @date 2023.05.02
	 * @author 정민지
	 * */
	@Override
	public List<SpendingResponse> getAllSpending(Member member) {
		List<Spending> spendings = spendingRepository.findAllByMemberOrderByDateDescCreatedAtDesc(member);
		return spendings.stream()
			.map(spending -> SpendingResponse.builder()
			.spendingId(spending.getId())
			.spendingClassificationName(spending.getSpendingClassification().getName())
			.amount(spending.getAmount())
			.assetName(spending.getAsset().getName())
			.date(spending.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.detail(spending.getDetail())
			.memo(spending.getMemo())
			.build())
			.collect(Collectors.toList());
	}

	/*
	 * 특정 달 소비내역 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@Override
	public List<SpendingResponse> getMonthSpending(Member member, int year, int month) {
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

		List<Spending> spendings = spendingRepository.findAllByMemberAndDateBetweenOrderByDateDescCreatedAtDesc(member, startDate, endDate);
		return spendings.stream().map(spending -> SpendingResponse.builder()
			.spendingId(spending.getId())
			.spendingClassificationName(spending.getSpendingClassification().getName())
			.amount(spending.getAmount())
			.assetName(spending.getAsset().getName())
			.date(spending.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.detail(spending.getDetail())
			.memo(spending.getMemo())
			.build())
			.collect(Collectors.toList());
	}

	/*
	 * 소비내역 디테일 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@Override
	public SpendingResponse getDetailSpending(Member member, Long spendingId) {

		Spending spending = spendingRepository.findById(spendingId).orElseThrow(() -> new AccountBookRuntimeException(AccountBookExceptionEnum.SPENDING_ID_NULL));

		return SpendingResponse.builder()
			.spendingId(spending.getId())
			.amount(spending.getAmount())
			.spendingClassificationName(spending.getSpendingClassification().getName())
			.assetName(spending.getAsset().getName())
			.date(spending.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.detail(spending.getDetail())
			.memo(spending.getMemo())
			.build();
	}

	/*
	 * 특정 달 소비 금액 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@Override
	public int getMonthSpendingAmount(Member member, int year, int month) {
		LocalDate startDate = LocalDate.of(year, month, 1);
		LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

		return spendingRepository.getTotalAmountByMemberAndDateBetween(member, startDate, endDate);
	}


	/*
	 * 특정 소비 내역 수정
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public SpendingResponse updateSpending(Member member, Long spendingId, SpendingRequest spendingRequest) {
		Spending spending = spendingRepository.findById(spendingId).orElseThrow(() -> new AccountBookRuntimeException(AccountBookExceptionEnum.SPENDING_ID_NULL));

		if (spendingRequest.getDate() != null && !spending.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).equals(spendingRequest.getDate())) {
			spending.setDate(LocalDate.parse(spendingRequest.getDate(), DateTimeFormatter.ISO_DATE));
		}
		if (spendingRequest.getAmount() != 0 && spendingRequest.getAmount() != spending.getAmount()) {
			spending.setAmount(spendingRequest.getAmount());
		}
		if (spendingRequest.getAssetId() != null && spendingRequest.getAssetId() != spending.getAsset().getId()) {
			spending.setAsset(findAssetById(spendingRequest.getAssetId()));
		}
		if (spendingRequest.getClassificationId() != null && spendingRequest.getClassificationId() != spending.getSpendingClassification().getId()) {
			spending.setSpendingClassification(findSpendingClassificationById(spendingRequest.getClassificationId()));
		}
		if (spendingRequest.getDetail() != null && !spendingRequest.getDetail().equals(spending.getDetail())) {
			spending.setDetail(spendingRequest.getDetail());
		}
		if (spendingRequest.getMemo() != null && !spendingRequest.getMemo().equals(spending.getMemo())) {
			spending.setMemo(spendingRequest.getMemo());
		}

		Spending resultSpending = spendingRepository.save(spending);

		processRecordService.processUpdatedSpending(spending, resultSpending);

		return SpendingResponse.builder()
			.spendingId(resultSpending.getId())
			.amount(resultSpending.getAmount())
			.spendingClassificationName(resultSpending.getSpendingClassification().getName())
			.assetName(resultSpending.getAsset().getName())
			.date(resultSpending.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
			.detail(resultSpending.getDetail())
			.memo(resultSpending.getMemo())
			.build();
	}

	/*
	 * 특정 소비 내역 삭제
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public void deleteSpending(Long spendingId) {
		processRecordService.processDeletedSpending(spendingId);
		spendingRepository.deleteById(spendingId);
	}
}
