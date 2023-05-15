package com.ssafy.moneykeeperbackend.member.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moneykeeperbackend.member.service.MemberService;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

	private final MemberService memberService;

	/*
	 * 이메일 포함 멤버 가져오기
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@GetMapping("/search")
	public ResponseEntity<?> getMembersByEmail(@RequestParam("email") String email) {
		return new ResponseEntity<>(memberService.getMembersByEmail(email),
			HttpStatus.OK);
	}
}
