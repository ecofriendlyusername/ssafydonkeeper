package com.ssafy.moneykeeperbackend.accountbook.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account-book/spending")
public class SpendingController {

	private final SpendingService spendingService;

	/*
	 * 소비 내역 입력
	 *
	 * @date 2023.04.28
	 * @author 정민지
	 * */
	@PostMapping()
	public ResponseEntity<?> addSpendingRecord(@AuthenticationPrincipal CustomUserDetails member,
		@RequestBody SpendingRequest spendingRequest, HttpServletRequest request, HttpServletResponse response) {
		return new ResponseEntity<>(spendingService.addSpendingRecord(spendingRequest, member.getMember()), HttpStatus.OK);
	}
}
