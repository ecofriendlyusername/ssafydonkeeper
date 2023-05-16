package com.ssafy.moneykeeperbackend.circle.controller;

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

import com.ssafy.moneykeeperbackend.circle.dto.request.CircleRequest;
import com.ssafy.moneykeeperbackend.circle.service.CircleService;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/circle")
public class CircleController {

	private final CircleService circleService;

	/*
	 * 그룹 조회
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@GetMapping("/{circleId}/{year}/{month}")
	public ResponseEntity<?> getCircleInfo(@PathVariable Long circleId, @PathVariable int year,
		@PathVariable int month) {
		return new ResponseEntity<>(circleService.getCircleInfo(circleId, year, month),
			HttpStatus.OK);
	}

	/*
	 * 로그인한 멤버가 가입된 그룹 전체 조회
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@GetMapping()
	public ResponseEntity<?> getAllMembersCircle(@AuthenticationPrincipal CustomUserDetails member) {
		return new ResponseEntity<>(circleService.getAllMembersCircle(member.getMember()),
			HttpStatus.OK);
	}

	/*
	 * 그룹명 중복 체크
	 *
	 * @date 2023.05.16
	 * @author 정민지
	 * */
	@GetMapping("/exists")
	public ResponseEntity<?> existsCircleName(@RequestParam String name) {
		return new ResponseEntity<>(circleService.existsCircleName(name), HttpStatus.OK);
	}

	/*
	 * 그룹 생성
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@PostMapping()
	public ResponseEntity<?> addCircle(@AuthenticationPrincipal CustomUserDetails member,
		@RequestBody CircleRequest circleRequest) {
		return new ResponseEntity<>(circleService.addCircle(circleRequest, member.getMember()),
			HttpStatus.OK);
	}

	/*
	 * 그룹 탈퇴
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@DeleteMapping("/secession")
	public ResponseEntity<?> deleteMemberCircle(@AuthenticationPrincipal CustomUserDetails member,
		@RequestParam Long circleId) {
		circleService.deleteMemberCircle(circleId, member.getMember());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/*
	 * 그룹 삭제
	 *
	 * @date 2023.05.15
	 * @author 정민지
	 * */
	@DeleteMapping()
	public ResponseEntity<?> deleteCircle(@AuthenticationPrincipal CustomUserDetails member,
		@RequestParam Long circleId) {
		circleService.deleteCircle(circleId, member.getMember());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/*
	 * 그룹 멤버 초대
	 *
	 * @date 2023.05.16
	 * @author 정민지
	 * */
	@PatchMapping("/invite")
	public ResponseEntity<?> addMember(@AuthenticationPrincipal CustomUserDetails member,
		@RequestParam Long circleId, @RequestBody CircleRequest circleRequest) {
		return new ResponseEntity<>(circleService.addMember(circleId, circleRequest, member.getMember()),
			HttpStatus.OK);
	}

	/*
	 * 그룹 이름 수정
	 *
	 * @date 2023.05.16
	 * @author 정민지
	 * */
	@PatchMapping()
	public ResponseEntity<?> updateName(@AuthenticationPrincipal CustomUserDetails member,
		@RequestParam Long circleId, @RequestParam String name) {
		return new ResponseEntity<>(circleService.updateName(circleId, name, member.getMember()),
			HttpStatus.OK);
	}

}
