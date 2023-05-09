package com.ssafy.moneykeeperbackend.accountbook.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingClassificationRequest;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface SpendingClassificationService {

	List<IdNameDTO> getAllSpendingClassification(Member member);

	IdNameDTO addSpendingClassification(Member member, SpendingClassificationRequest spendingClassificationRequest);
}
