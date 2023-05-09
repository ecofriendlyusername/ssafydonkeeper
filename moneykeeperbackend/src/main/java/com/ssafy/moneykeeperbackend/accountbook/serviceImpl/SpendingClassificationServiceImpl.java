package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingClassificationRequest;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.IncomeClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.IncomeClassificationService;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingClassificationService;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookRuntimeException;
import com.ssafy.moneykeeperbackend.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpendingClassificationServiceImpl implements SpendingClassificationService {

	private final SpendingClassificationRepository spendingClassificationRepository;

	private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;

	/*
	 * 소비 분류 전체 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Override
	public List<IdNameDTO> getAllSpendingClassification(Member member) {
		return spendingClassificationRepository.findByMember(member).stream().map(
				spendingClassification -> IdNameDTO.builder()
					.id(spendingClassification.getId())
					.name(spendingClassification.getName())
					.build())
			.collect(Collectors.toList());
	}

	/*
	 * 소비 분류 생성
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public IdNameDTO addSpendingClassification(Member member,
		SpendingClassificationRequest spendingClassificationRequest) {
		SpendingClassification spendingClassification = spendingClassificationRepository.saveAndFlush(
			SpendingClassification.builder()
				.member(member)
				.majorSpendingClassification(majorSpendingClassificationRepository.findById(
					spendingClassificationRequest.getMajorSpendingClassificationId()).orElseThrow(
						() -> new AccountBookRuntimeException(AccountBookExceptionEnum.MAJOR_SPENDING_CLASSIFICATION_ID_NULL)))
				.name(spendingClassificationRequest.getName())
				.build());

		return IdNameDTO.builder()
			.id(spendingClassification.getId())
			.name(spendingClassification.getName())
			.build();
	}
}
