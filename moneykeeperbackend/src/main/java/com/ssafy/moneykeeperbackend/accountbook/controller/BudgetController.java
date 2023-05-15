package com.ssafy.moneykeeperbackend.accountbook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moneykeeperbackend.accountbook.dto.BudgetDTO;
import com.ssafy.moneykeeperbackend.accountbook.service.BudgetService;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account-book/budget")
public class BudgetController {

	private final BudgetService budgetService;

	/*
	 * 예산 입력 또는 수정
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@PatchMapping()
	public ResponseEntity<?> upsertMonthBudget(@AuthenticationPrincipal CustomUserDetails member,
		@RequestBody BudgetDTO budgetDTO) {
		return new ResponseEntity<>(budgetService.upsertMonthBudget(budgetDTO, member.getMember()),
			HttpStatus.OK);
	}

	/*
	 * 월별 예산 전체 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@GetMapping("/all/{year}/{month}")
	public ResponseEntity<?> getMonthAllBudget(@AuthenticationPrincipal CustomUserDetails member,
		@PathVariable int year, @PathVariable int month) {
		return new ResponseEntity<>(budgetService.getMonthAllBudget(member.getMember(), year, month),
			HttpStatus.OK);
	}

	/*
	 * 예산 삭제
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@DeleteMapping("/{year}/{month}")
	public ResponseEntity<?> deleteBudget(@AuthenticationPrincipal CustomUserDetails member, @PathVariable int year,
		@PathVariable int month, @RequestParam(required = false) Long spendingClassificationId) {
		budgetService.deleteBudget(member.getMember(), year, month, spendingClassificationId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
