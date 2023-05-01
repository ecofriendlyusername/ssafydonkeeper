package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
import com.ssafy.moneykeeperbackend.member.serviceImpl.MemberServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpendingServiceImpl implements SpendingService {

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
			.spendingClassification(findSpendingClassificationById(spendingRequest.getSpendingClassificationId()))
			.date(LocalDate.parse(spendingRequest.getDate(), DateTimeFormatter.ISO_DATE))
			.asset(findAssetById(spendingRequest.getAssetId()))
			.amount(spendingRequest.getAmount())
			.memo(spendingRequest.getMemo())
			.detail(spendingRequest.getDetail())
			.build();

		spending = spendingRepository.saveAndFlush(spending);

		return SpendingResponse.builder()
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
			.orElseThrow(() -> new AccountBookRuntimeException(AccountBookExceptionEnum.SPENDING_CLASSIFICATION_ID_NULL));
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
}
