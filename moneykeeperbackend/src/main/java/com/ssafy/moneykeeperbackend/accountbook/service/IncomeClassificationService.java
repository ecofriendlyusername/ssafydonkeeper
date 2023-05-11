package com.ssafy.moneykeeperbackend.accountbook.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameClassificationDTO;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface IncomeClassificationService {

	List<IdNameClassificationDTO> getAllIncomeClassification(Member member);
}
