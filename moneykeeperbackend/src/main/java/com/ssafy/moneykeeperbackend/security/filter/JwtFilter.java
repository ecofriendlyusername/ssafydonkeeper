package com.ssafy.moneykeeperbackend.security.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.moneykeeperbackend.exception.auth.AuthExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.auth.AuthRuntimeException;
import com.ssafy.moneykeeperbackend.security.TokenProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

	private final TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		// 세션에서 access token을 찾는다.

		// validateToken이 true면 인증하고 dofilter
		// false라면 refresh token을 찾는다.
		// refresh token이 validateToken이 true면 access token 재발급하고
		// 인증해서 넘겨주고
		// refresh token이 validateToken이 false면 인증하지 않고 dofilter 한다.

		// 인증
		// Authentication auth = tokenProvider.getAuthentication(token);
		// SecurityContextHolder.getContext().setAuthentication(auth);
		// filterChain.doFilter(request, response);

		// no 인증
		// filterChain.doFilter(request, response);

		// 인증이 필요한 경우에만 토큰 검증을 수행합니다.
		if (requiresAuthentication(request)) {
			String accessToken = tokenProvider.findToken(request, "access_token_id");
			String refreshToken = tokenProvider.findToken(request, "refresh_token_id");

			if (accessToken != null && tokenProvider.validateToken(accessToken)) {
				// Access token이 유효한 경우, 인증 정보를 설정하고 필터 체인을 계속 진행
				Authentication auth = tokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else if (refreshToken != null && tokenProvider.validateToken(refreshToken)) {
				// Refresh token이 유효한 경우, 새로운 access token을 생성하고 응답에 추가
				Map<String, String> resultMap = tokenProvider.getEmailAndAuthFromToken(refreshToken);
				accessToken = tokenProvider.createAccessToken(resultMap.get("email"), resultMap.get("auth"), request,
					response);
				Authentication auth = tokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else {
				// 둘 다 만료되었거나 존재하지 않는 경우, 커스텀 예외를 발생시킵니다.
				throw new AuthRuntimeException(AuthExceptionEnum.REQUIRED_RE_LOGIN);
			}
		}

		filterChain.doFilter(request, response);
	}

	private boolean requiresAuthentication(HttpServletRequest request) {
		// 인증이 필요한 엔드포인트를 확인하는 로직을 구현합니다.
		// 예를 들어, "/login" 및 "/register" 엔드포인트에 대해 인증이 필요하지 않다고 가정합니다.
		String requestURI = request.getRequestURI();
		if (requestURI.equals("/api/auth/kakao/callback")) {
			return false;
		}
		return requestURI.startsWith("/api");
//		return false;
	}

}
