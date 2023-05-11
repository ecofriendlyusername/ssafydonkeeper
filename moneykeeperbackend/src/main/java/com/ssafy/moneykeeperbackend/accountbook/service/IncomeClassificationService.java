package com.ssafy.moneykeeperbackend.accountbook.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface IncomeClassificationService {

	List<IdNameDTO> getAllIncomeClassification(Member member);

	IdNameDTO addIncomeclassification(Member member, IdNameDTO idNameDTO);

	IdNameDTO updateIncomeClassification(Member member, IdNameDTO idNameDTO, Long incomeclassificationId);

	void deleteIncomeClassification(Long incomeclassificationId);
}
