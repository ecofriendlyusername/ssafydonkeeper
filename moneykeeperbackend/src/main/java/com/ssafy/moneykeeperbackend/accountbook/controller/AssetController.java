package com.ssafy.moneykeeperbackend.accountbook.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moneykeeperbackend.accountbook.service.AssetService;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/account-book/asset")
public class AssetController {

	private final AssetService assetService;

	/*
	 * 자산 분류 전체 가져오기
	 *
	 * @date 2023.05.09
	 * @author 정민지
	 * */
	@GetMapping()
	public ResponseEntity<?> getAllAsset(@AuthenticationPrincipal CustomUserDetails member) {
		return new ResponseEntity<>(assetService.getAllAsset(member.getMember()), HttpStatus.OK);
	}
}
