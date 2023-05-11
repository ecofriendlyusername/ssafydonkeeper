package com.ssafy.moneykeeperbackend.accountbook.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingClassificationRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.SpendingClassificationResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface SpendingClassificationService {

	List<SpendingClassificationResponse> getAllSpendingClassification(Member member);

	SpendingClassificationResponse addSpendingClassification(Member member, SpendingClassificationRequest spendingClassificationRequest);

	SpendingClassificationResponse updateSpendingClassification(Member member, SpendingClassificationRequest spendingClassificationRequest,
		Long spendingclassificationId);

	void deleteSpendingClassification(Long spendingclassificationId);
}
