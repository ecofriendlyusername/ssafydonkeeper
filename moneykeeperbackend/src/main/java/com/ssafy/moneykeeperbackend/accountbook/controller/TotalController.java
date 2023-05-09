package com.ssafy.moneykeeperbackend.accountbook.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moneykeeperbackend.accountbook.service.TotalService;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account-book/total")
public class TotalController {

	private final TotalService totalService;

	/*
	 * 특정 달의 매일 소비/ 수입 금액과 총 소비/ 수입 금액 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@GetMapping("/amount/{year}/{month}")
	public ResponseEntity<?> getMonthTotalAmmount(@AuthenticationPrincipal CustomUserDetails member,
		@PathVariable int year, @PathVariable int month) {
		return new ResponseEntity<>(totalService.getMonthTotalAmmount(member.getMember(), year, month),
			HttpStatus.OK);
	}

	/*
	 * 특정 날의 소비/ 수입 금액 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@GetMapping("/amount/{year}/{month}/{day}")
	public ResponseEntity<?> getDateTotalAmmount(@AuthenticationPrincipal CustomUserDetails member,
		@PathVariable int year, @PathVariable int month, @PathVariable int day) {
		return new ResponseEntity<>(totalService.getDateTotalAmmount(member.getMember(), LocalDate.of(year, month, day)),
			HttpStatus.OK);
	}

}
