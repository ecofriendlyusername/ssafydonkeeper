package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.accountbook.repository.IncomeClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.IncomeClassificationService;
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
}
