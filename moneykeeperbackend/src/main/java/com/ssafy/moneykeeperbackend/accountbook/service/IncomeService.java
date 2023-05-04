package com.ssafy.moneykeeperbackend.accountbook.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ssafy.moneykeeperbackend.accountbook.dto.request.IncomeRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.IncomeResponse;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.SpendingResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface IncomeService {

	IncomeResponse addIncomeRecord(IncomeRequest incomeRequest, Member member);

	Page<IncomeResponse> getAllIncome(Member member, Pageable pageable);

	Page<IncomeResponse> getMonthIncome(Member member, int year, int month, Pageable pageable);

	IncomeResponse getDetailIncome(Member member, Long incomeId);
	//
	// int getMonthSpendingAmount(Member member, int year, int month);
	//
	// SpendingResponse updateSpending(Member member, Long spendingId, SpendingRequest spendingRequest);
	//
	// void deleteSpending(Long spendingId);


}
