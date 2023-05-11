package com.ssafy.moneykeeperbackend.accountbook.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameClassificationDTO;
import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingClassificationRequest;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface SpendingClassificationService {

	List<IdNameClassificationDTO> getAllSpendingClassification(Member member);

	IdNameClassificationDTO addSpendingClassification(Member member, SpendingClassificationRequest spendingClassificationRequest);

	IdNameClassificationDTO updateSpendingClassification(Member member, SpendingClassificationRequest spendingClassificationRequest,
		Long spendingclassificationId);

	void deleteSpendingClassification(Long spendingclassificationId);
}
