package com.ssafy.moneykeeperbackend.accountbook.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
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
		return new ResponseEntity<>(spendingService.addSpendingRecord(spendingRequest, member.getMember()),
			HttpStatus.OK);
	}

	/*
	 * 전체 소비내역 가져오기
	 *
	 * @date 2023.05.02
	 * @author 정민지
	 * */
	@GetMapping()
	public ResponseEntity<?> getAllSpending(@AuthenticationPrincipal CustomUserDetails member,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("date"), Sort.Order.asc("createdAt")));
		return new ResponseEntity<>(spendingService.getAllSpending(member.getMember(), pageable).getContent(),
			HttpStatus.OK);
	}

	/*
	 * 특정 달 소비내역 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@GetMapping("/{year}/{month}")
	public ResponseEntity<?> getMonthSpending(@AuthenticationPrincipal CustomUserDetails member, @PathVariable int year,
		@PathVariable int month, @RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("date"), Sort.Order.asc("createdAt")));
		return new ResponseEntity<>(
			spendingService.getMonthSpending(member.getMember(), year, month, pageable).getContent(),
			HttpStatus.OK);
	}

	/*
	 * 소비내역 디테일 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@GetMapping("/{spendingId}")
	public ResponseEntity<?> getDetailSpending(@AuthenticationPrincipal CustomUserDetails member,
		@PathVariable Long spendingId) {
		return new ResponseEntity<>(spendingService.getDetailSpending(member.getMember(), spendingId), HttpStatus.OK);
	}

	/*
	 * 특정 달 소비 금액 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@GetMapping("/amount/{year}/{month}")
	public ResponseEntity<?> getMonthSpendingAmount(@AuthenticationPrincipal CustomUserDetails member,
		@PathVariable int year, @PathVariable int month) {
		return new ResponseEntity<>(spendingService.getMonthSpendingAmount(member.getMember(), year, month),
			HttpStatus.OK);
	}

	/*
	 * 특정 소비 내역 수정
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@PatchMapping("/{spendingId}")
	public ResponseEntity<?> updateSpending(@AuthenticationPrincipal CustomUserDetails member,
		@PathVariable Long spendingId, @RequestBody SpendingRequest spendingRequest) {
		return new ResponseEntity<>(spendingService.updateSpending(member.getMember(), spendingId, spendingRequest), HttpStatus.OK);
	}

	/*
	 * 특정 소비 내역 삭제
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@DeleteMapping("/{spendingId}")
	public ResponseEntity<?> deleteSpending(@PathVariable Long spendingId) {
		spendingService.deleteSpending(spendingId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
