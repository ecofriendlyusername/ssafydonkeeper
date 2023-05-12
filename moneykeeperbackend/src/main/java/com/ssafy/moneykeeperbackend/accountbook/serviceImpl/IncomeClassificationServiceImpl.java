package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.accountbook.entity.IncomeClassification;
import com.ssafy.moneykeeperbackend.accountbook.repository.IncomeClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.IncomeClassificationService;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.accountbook.AccountBookRuntimeException;
import com.ssafy.moneykeeperbackend.member.entity.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncomeClassificationServiceImpl implements IncomeClassificationService {

	private final IncomeClassificationRepository incomeClassificationRepository;

	/*
	 * 수입 분류 전체 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@Override
	public List<IdNameDTO> getAllIncomeClassification(Member member) {
		return incomeClassificationRepository.findByMember(member).stream().map(
				incomeClassification -> IdNameDTO.builder()
					.id(incomeClassification.getId())
					.name(incomeClassification.getName())
					.build())
			.collect(Collectors.toList());
	}

	/*
	 * 수입 분류 생성
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public IdNameDTO addIncomeclassification(Member member, IdNameDTO idNameDTO) {

		IncomeClassification incomeClassification = incomeClassificationRepository.saveAndFlush(
			IncomeClassification.builder()
				.name(idNameDTO.getName())
				.member(member)
				.build());

		return IdNameDTO.builder()
			.id(incomeClassification.getId())
			.name(incomeClassification.getName())
			.build();
	}

	/*
	 * 수입 분류 수정
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public IdNameDTO updateIncomeClassification(Member member, IdNameDTO idNameDTO, Long incomeclassificationId) {

		IncomeClassification incomeClassification = incomeClassificationRepository.findById(incomeclassificationId)
			.orElseThrow(() -> new AccountBookRuntimeException(
				AccountBookExceptionEnum.INCOME_CLASSIFICATION_ID_NULL));

		if (idNameDTO.getName() != null && idNameDTO.getName() != incomeClassification.getName()) {
			incomeClassification.setName(idNameDTO.getName());
		}

		IncomeClassification resultIncomeClassification = incomeClassificationRepository.save(incomeClassification);

		return IdNameDTO.builder()
			.id(resultIncomeClassification.getId())
			.name(resultIncomeClassification.getName())
			.build();
	}

	/*
	 * 특정 수입 분류 삭제
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@Transactional
	@Override
	public void deleteIncomeClassification(Long incomeclassificationId) {
		incomeClassificationRepository.deleteById(incomeclassificationId);
	}
}
