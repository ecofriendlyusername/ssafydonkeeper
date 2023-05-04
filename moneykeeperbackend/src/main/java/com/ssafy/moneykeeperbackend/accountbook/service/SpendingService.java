package com.ssafy.moneykeeperbackend.accountbook.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.SpendingResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface SpendingService {

	SpendingResponse addSpendingRecord(SpendingRequest spendingRequest, Member member);

	Page<SpendingResponse> getAllSpending(Member member, Pageable pageable);

	Page<SpendingResponse> getMonthSpending(Member member, int year, int month, Pageable pageable);

	SpendingResponse getDetailSpending(Member member, Long spendingId);

	int getMonthSpendingAmount(Member member, int year, int month);

	SpendingResponse updateSpending(Member member, Long spendingId, SpendingRequest spendingRequest);

	void deleteSpending(Long spendingId);


}
