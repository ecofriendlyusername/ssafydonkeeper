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

import com.ssafy.moneykeeperbackend.accountbook.dto.request.IncomeRequest;
import com.ssafy.moneykeeperbackend.accountbook.dto.request.SpendingRequest;
import com.ssafy.moneykeeperbackend.accountbook.service.IncomeService;
import com.ssafy.moneykeeperbackend.accountbook.service.SpendingService;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account-book/income")
public class IncomeController {

	private final IncomeService incomeService;


	/*
	 * 소비 내역 입력
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@PostMapping()
	public ResponseEntity<?> addIncomeRecord(@AuthenticationPrincipal CustomUserDetails member,
		@RequestBody IncomeRequest incomeRequest) {
		return new ResponseEntity<>(incomeService.addIncomeRecord(incomeRequest, member.getMember()),
			HttpStatus.OK);
	}

	/*
	 * 전체 수입 내역 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@GetMapping()
	public ResponseEntity<?> getAllIncome(@AuthenticationPrincipal CustomUserDetails member,
		@RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("date"), Sort.Order.asc("createdAt")));
		return new ResponseEntity<>(incomeService.getAllIncome(member.getMember(), pageable),
			HttpStatus.OK);
	}

	/*
	 * 특정 달 수입 내역 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@GetMapping("/{year}/{month}")
	public ResponseEntity<?> getMonthIncome(@AuthenticationPrincipal CustomUserDetails member, @PathVariable int year,
		@PathVariable int month, @RequestParam(defaultValue = "0") int page,
		@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("date"), Sort.Order.asc("createdAt")));
		return new ResponseEntity<>(
			incomeService.getMonthIncome(member.getMember(), year, month),
			HttpStatus.OK);
	}

	/*
	 * 소비 내역 디테일 가져오기
	 *
	 * @date 2023.05.04
	 * @author 정민지
	 * */
	@GetMapping("/{incomeId}")
	public ResponseEntity<?> getDetailIncome(@AuthenticationPrincipal CustomUserDetails member,
		@PathVariable Long incomeId) {
		return new ResponseEntity<>(incomeService.getDetailIncome(member.getMember(), incomeId), HttpStatus.OK);
	}

	/*
	 * 특정 수입 내역 수정
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@PatchMapping("/{incomeId}")
	public ResponseEntity<?> updateIncome(@AuthenticationPrincipal CustomUserDetails member,
		@PathVariable Long incomeId, @RequestBody IncomeRequest incomeRequest) {
		return new ResponseEntity<>(incomeService.updateIncome(member.getMember(), incomeId, incomeRequest), HttpStatus.OK);
	}

	/*
	 * 특정 수입 내역 삭제
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@DeleteMapping("/{incomeId}")
	public ResponseEntity<?> deleteIncome(@PathVariable Long incomeId) {
		incomeService.deleteIncome(incomeId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
