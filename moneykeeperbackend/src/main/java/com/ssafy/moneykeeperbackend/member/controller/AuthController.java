package com.ssafy.moneykeeperbackend.member.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.moneykeeperbackend.member.dto.common.MemberDto;
import com.ssafy.moneykeeperbackend.member.dto.common.TokenDto;
import com.ssafy.moneykeeperbackend.member.dto.response.MemberResponse;
import com.ssafy.moneykeeperbackend.member.service.AuthService;
import com.ssafy.moneykeeperbackend.security.TokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;

	private final TokenProvider tokenProvider;

	@Value("${jwt.token-validity-in-milliseconds}")
	private long accessTokenValidityTime;

	@Value("${jwt.refresh-token-validity-in-milliseconds}")
	private long refreshTokenValidityTime;

	/*
	 * 카카오 로그인
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 * */
	@PostMapping("/kakao/callback")
	public ResponseEntity<?> getKakao(@RequestBody Map<String, String> code, HttpServletRequest request,
		HttpServletResponse response) {
		MemberDto memberDto = authService.getKakao(code.get("code"));
		TokenDto tokenDto = authorize(memberDto.getEmail(), memberDto.getPassword(), request, response);

		String accessTokenId = UUID.randomUUID().toString();
		String refreshTokenId = UUID.randomUUID().toString();    // 랜덤한 refresh token id 생성
		HttpSession session = request.getSession();    // true를 주면 session이 없을 때 session을 만들고 시작함

		session.setAttribute(accessTokenId, tokenDto.getAccessToken());    // session에 access_token 값을 저장
		session.setAttribute(refreshTokenId, tokenDto.getRefreshToken());   // session에 refresh_token 값을 저장

		Cookie cookie = new Cookie("access_token_id", accessTokenId);
		cookie.setPath("/");
		cookie.setMaxAge((int)accessTokenValidityTime); // 쿠키의 유효 기간을 24시간으로 설정
		cookie.setHttpOnly(true); // HttpOnly 속성 설정
		cookie.setSecure(false); // Secure 속성을 비활성화 (HTTP 환경)
		// cookie.setSecure(true); // https 환경
		response.addCookie(cookie);

		// SameSite 속성을 Lax로 설정
		// response.setHeader("Set-Cookie", String.format("%s; %s", cookie.toString(), "SameSite=Lax"));
		response.setHeader("Set-Cookie", String.format("%s; %s", cookie.toString(), "SameSite=None")); // https 환경

		// access token 쿠키 생성
		Cookie accessTokenCookie = new Cookie("access_token_id", accessTokenId);
		accessTokenCookie.setPath("/");
		accessTokenCookie.setHttpOnly(true);    // http를 통해서만 쿠키가 보내짐
		accessTokenCookie.setMaxAge((int)accessTokenValidityTime);
		// accessTokenCookie.setSecure(true);    // https에서만 쿠키가 보내지도록

		response.addCookie(accessTokenCookie);

		// refresh token 쿠키 생성
		Cookie refreshTokenCookie = new Cookie("refresh_token_id", refreshTokenId);
		refreshTokenCookie.setPath("/");
		refreshTokenCookie.setHttpOnly(true);
		refreshTokenCookie.setMaxAge((int)refreshTokenValidityTime);

		response.addCookie(refreshTokenCookie);

		MemberResponse memberResponse = MemberResponse.builder()
			.id(memberDto.getId())
			.email(memberDto.getEmail())
			.nickname(memberDto.getNickname())
			.build();

		return new ResponseEntity<>(memberResponse, HttpStatus.OK);
	}

	/*
	 * 로그인 시 권한 정보 확인
	 * @tip UsernamePasswordAuthenticationToken 에는 원본 비밀번호로 입력해야 한다.
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 * */
	public TokenDto authorize(String email, String password, HttpServletRequest request,
		HttpServletResponse response) {

		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email,
			password);

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String authorities = getAuthorities(authentication);

		return tokenProvider.createToken(email, authorities, request, response);
	}

	public String getAuthorities(Authentication authentication) {
		return authentication.getAuthorities()
			.stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));
	}

	@GetMapping("/session")
	public ResponseEntity<?> getSessionData(HttpServletRequest request) {

		HttpSession session = request.getSession(false); // 세션을 가져옵니다. 없으면 null 반환

		if (session == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}


		String accessTokenId = "";
		String refreshTokenId = "";

		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("access_token_id")) {
				accessTokenId =  cookie.getValue();
			}

			if (cookie.getName().equals("refresh_token_id")) {
				refreshTokenId =  cookie.getValue();
			}
		}

		String accessToken = (String) session.getAttribute(accessTokenId);
		String refreshToken = (String) session.getAttribute(refreshTokenId);

		// 응답 객체를 생성합니다.
		Map<String, String> sessionData = new HashMap<>();
		sessionData.put("access_token", accessToken);
		sessionData.put("refresh_token", refreshToken);

		return ResponseEntity.ok(sessionData);
	}

}
