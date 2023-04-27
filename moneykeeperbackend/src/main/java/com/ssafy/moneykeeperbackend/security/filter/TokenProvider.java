package com.ssafy.moneykeeperbackend.security.filter;

import java.security.Key;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.ssafy.moneykeeperbackend.member.dto.common.TokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

	@Value("${jwt.secret}")
	private String secret_key;

	@Value("${jwt.token-validity-in-milliseconds}")
	private long accessTokenValidityTime;

	@Value("${jwt.refresh-token-validity-in-milliseconds}")
	private long refreshTokenValidityTime;

	@Autowired
	private UserDetailsService userDetailsService;

	private Key key;

	/*
	 * 검증된 이메일에 대해 토큰을 생성하는 메서드
	 * AccessToken의 Claim으로는 email과 nickname을 넣습니다.
	 */
	public TokenDto createToken(String email,
		String authorities) {

		long now = (new Date()).getTime();

		String accessToken = createAccessToken(email, authorities);

		String refreshToken = Jwts.builder()
			.claim("auth", authorities)
			.claim("email", email)
			.setExpiration(new Date(now + refreshTokenValidityTime))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		return new TokenDto(accessToken, refreshToken);
	}

	/*
	 * accessToken 재발급
	 * */
	public String createAccessToken(String email, String authorities) {

		long now = (new Date()).getTime();

		String accessToken = Jwts.builder()
			.claim("email", email)
			.claim("auth", authorities)
			.setExpiration(new Date(now + accessTokenValidityTime))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		return accessToken;
	}


	public Authentication getAuthentication(String token) {
		String username = Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token).getBody().getSubject();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token);

		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
			// throw new AuthRuntimeException(AuthExceptionEnum.AUTH_JWT_SIGNATURE_EXCEPTION);
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
			// throw new AuthRuntimeException(AuthExceptionEnum.AUTH_JWT_EXPIRED_EXCEPTION);
		} catch (UnsupportedJwtException e) {
			log.info("지원하지 않는 JWT토큰입니다.");
			// throw new AuthRuntimeException(AuthExceptionEnum.AUTH_JWT_SUPPORT_EXCEPTION);
		} catch (IllegalArgumentException e) {
			log.info("JWT토큰이 잘못되었습니다.");
			// throw new AuthRuntimeException(AuthExceptionEnum.AUTH_JWT_SIGNATURE_EXCEPTION);
		}

		return true;
	}

}
