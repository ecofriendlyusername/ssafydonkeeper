package com.ssafy.moneykeeperbackend.accountbook.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.service.MajorSpendingClassificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MajorSpendingClassificationServiceImpl implements MajorSpendingClassificationService {

	private final MajorSpendingClassificationRepository majorSpendingClassificationRepository;

	@Override
	public List<IdNameDTO> getAllMajorSpendingClassification() {
		return majorSpendingClassificationRepository.findAll()
			.stream()
			.map(majorSpendingClassification -> IdNameDTO.builder()
				.id(majorSpendingClassification.getId())
				.name(majorSpendingClassification.getName())
				.build())
			.collect(Collectors.toList());
	}
}
