package com.ssafy.moneykeeperbackend.security.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
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

	@Value("${jwt.token-validity-in-milliseconds}")
	private long accessTokenValidityTime;

	private final TokenProvider tokenProvider;

	/*
	 * 인증이 필요한 페이지에서만 인증 로직을 탄다.
	 * 쿠키와 세션에서 access token, refresh token을 찾고
	 * valid check 한다.
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 * */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

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

				String accessTokenId = UUID.randomUUID().toString();
				HttpSession session = request.getSession(true);    // true를 주면 session이 없을 때 session을 만들고 시작함

				session.setAttribute(accessTokenId, accessToken);    // session에 access_token 값을 저장

				// access token 쿠키 생성
				Cookie accessTokenCookie = new Cookie("access_token_id", accessTokenId);
				accessTokenCookie.setPath("/");
				accessTokenCookie.setHttpOnly(true);    // http를 통해서만 쿠키가 보내짐
				accessTokenCookie.setMaxAge((int)accessTokenValidityTime);
				// accessTokenCookie.setSecure(true);    // https에서만 쿠키가 보내지도록

				response.addCookie(accessTokenCookie);

				Authentication auth = tokenProvider.getAuthentication(accessToken);
				SecurityContextHolder.getContext().setAuthentication(auth);
			} else {
				// 둘 다 만료되었거나 존재하지 않는 경우, 커스텀 예외를 발생시킵니다.
				throw new AuthRuntimeException(AuthExceptionEnum.REQUIRED_RE_LOGIN);
			}
		}

		filterChain.doFilter(request, response);
	}

	/*
	 * 인증이 필요한 엔드포인트를 확인한다.
	 * @return 인증이 필요하다면 true, 아니면 false
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 * */
	private boolean requiresAuthentication(HttpServletRequest request) {

		String requestURI = request.getRequestURI();
		return requestURI.startsWith("/api/account-book");
	}

}
