package com.ssafy.moneykeeperbackend.accountbook.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
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

	/*
	 * 자산 분류 생성
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@PostMapping()
	public ResponseEntity<?> addAsset(@AuthenticationPrincipal CustomUserDetails member,
		@RequestBody IdNameDTO idNameDTO) {
		return new ResponseEntity<>(
			assetService.addAsset(member.getMember(), idNameDTO),
			HttpStatus.OK);
	}

	/*
	 * 특정 자산 분류 수정
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@PatchMapping("/{assetId}")
	public ResponseEntity<?> updateIncomeClassification(@AuthenticationPrincipal CustomUserDetails member,
		@RequestBody IdNameDTO idNameDTO,
		@PathVariable Long assetId) {
		return new ResponseEntity<>(
			assetService.updateAsset(member.getMember(),
				idNameDTO, assetId),
			HttpStatus.OK);
	}

	/*
	 * 특정 자산 분류 삭제
	 *
	 * @date 2023.05.11
	 * @author 정민지
	 * */
	@DeleteMapping("/{assetId}")
	public ResponseEntity<?> deleteSpendingClassification(@PathVariable Long assetId) {
		assetService.deleteAsset(assetId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
