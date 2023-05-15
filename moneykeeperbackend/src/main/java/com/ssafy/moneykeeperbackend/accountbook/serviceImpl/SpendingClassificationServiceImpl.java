package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingClassificationRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.SpendingClassificationResponse;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingClassificationRepository;
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
	public List<SpendingClassificationResponse> getAllSpendingClassification(Member member) {
		return spendingClassificationRepository.findByMember(member).stream().map(
				spendingClassification -> SpendingClassificationResponse.builder()
					.id(spendingClassification.getId())
					.name(spendingClassification.getName())
					.majorSpendingClassificationName(spendingClassification.getMajorSpendingClassification().getName())
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
	public SpendingClassificationResponse addSpendingClassification(Member member,
		SpendingClassificationRequest spendingClassificationRequest) {
		SpendingClassification spendingClassification = spendingClassificationRepository.saveAndFlush(
			SpendingClassification.builder()
				.member(member)
				.majorSpendingClassification(majorSpendingClassificationRepository.findById(
					spendingClassificationRequest.getMajorSpendingClassificationId()).orElseThrow(
					() -> new AccountBookRuntimeException(
						AccountBookExceptionEnum.MAJOR_SPENDING_CLASSIFICATION_ID_NULL)))
				.name(spendingClassificationRequest.getName())
				.build());

		return SpendingClassificationResponse.builder()
			.id(spendingClassification.getId())
			.name(spendingClassification.getName())
			.majorSpendingClassificationName(spendingClassification.getMajorSpendingClassification().getName())
			.build();
	}

	/*
	 * 특정 소비 분류 수정
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public SpendingClassificationResponse updateSpendingClassification(Member member,
		SpendingClassificationRequest spendingClassificationRequest, Long spendingclassificationId) {

		SpendingClassification spendingClassification = spendingClassificationRepository.findById(
				spendingclassificationId)
			.orElseThrow(
				() -> new AccountBookRuntimeException(AccountBookExceptionEnum.SPENDING_CLASSIFICATION_ID_NULL));

		if (spendingClassificationRequest.getName() != null
			&& spendingClassification.getName() != spendingClassificationRequest.getName()) {
			spendingClassification.setName(spendingClassificationRequest.getName());
		}

		if (spendingClassificationRequest.getMajorSpendingClassificationId() != null
			&& spendingClassificationRequest.getMajorSpendingClassificationId()
			!= spendingClassification.getMajorSpendingClassification().getId()) {
			spendingClassification.setMajorSpendingClassification(majorSpendingClassificationRepository.findById(
					spendingClassificationRequest.getMajorSpendingClassificationId())
				.orElseThrow(() -> new AccountBookRuntimeException(
					AccountBookExceptionEnum.MAJOR_SPENDING_CLASSIFICATION_ID_NULL)));
		}

		SpendingClassification resultSpendingClassification = spendingClassificationRepository.save(
			spendingClassification);

		return SpendingClassificationResponse.builder()
			.id(resultSpendingClassification.getId())
			.name(resultSpendingClassification.getName())
			.majorSpendingClassificationName(resultSpendingClassification.getMajorSpendingClassification().getName())
			.build();
	}

	/*
	 * 특정 소비 분류 삭제
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public void deleteSpendingClassification(Long spendingclassificationId) {
		spendingClassificationRepository.deleteById(spendingclassificationId);
	}
}
