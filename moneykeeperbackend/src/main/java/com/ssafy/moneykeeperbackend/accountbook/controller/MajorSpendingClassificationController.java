package com.ssafy.moneykeeperbackend.accountbook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moneykeeperbackend.accountbook.service.MajorSpendingClassificationService;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account-book/majorspendingclassification")
public class MajorSpendingClassificationController {

	private final MajorSpendingClassificationService majorSpendingClassificationService;

	/*
	 * 소비 대분류 전체 가져오기
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@GetMapping()
	public ResponseEntity<?> getAllMajorSpendingClassification() {
		return new ResponseEntity<>(majorSpendingClassificationService.getAllMajorSpendingClassification(),
			HttpStatus.OK);
	}

}
