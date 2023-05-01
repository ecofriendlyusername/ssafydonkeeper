package com.ssafy.moneykeeperbackend.accountbook.service;

import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.response.SpendingResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;

public interface SpendingService {

	SpendingResponse addSpendingRecord(SpendingRequest spendingRequest, Member member);
}
