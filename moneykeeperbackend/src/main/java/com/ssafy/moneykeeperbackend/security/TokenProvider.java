package com.ssafy.moneykeeperbackend.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.crypto.SecretKey;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.ssafy.moneykeeperbackend.member.dto.common.TokenDto;
import com.ssafy.moneykeeperbackend.security.userDetail.CustomUserDetailService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


/*
* 인증 토큰을 생성, 검증, 토큰에서 사용자 정보를 추출하는 클래스
* */
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

	private SecretKey key;     // JWT에서 대칭 키 암호화를 사용하는 경우 SecretKey를 사용하면 코드의 의도가 더 명확해질 것

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret_key.getBytes(StandardCharsets.UTF_8));
	}

	/*
	 * access token과 refresh token을 전부 발급하는 메서드.
	 * session에 랜덤한 이름으로 access token과 refresh token을 담고
	 * cookie에 session 아이디를 담는다.
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 */
	public TokenDto createToken(String email, String authorities, HttpServletRequest request,
		HttpServletResponse response) {

		long now = (new Date()).getTime();

		String accessToken = createAccessToken(email, authorities, request, response); // access tokne 발급

		String refreshToken = Jwts.builder()
			.claim("auth", authorities)
			.claim("email", email)
			.setExpiration(new Date(now + refreshTokenValidityTime))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		return new TokenDto(accessToken, refreshToken);
	}

	/*
	 * access token을 발급하는 메서드.
	 * session에 랜덤한 이름으로 access token을 담고
	 * cookie에 session 아이디를 담는다.
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 */
	public String createAccessToken(String email, String authorities, HttpServletRequest request,
		HttpServletResponse response) {

		long now = (new Date()).getTime();

		String accessToken = Jwts.builder()
			.claim("email", email)
			.claim("auth", authorities)
			.setExpiration(new Date(now + accessTokenValidityTime))
			.signWith(key, SignatureAlgorithm.HS512)
			.compact();

		return accessToken;
	}

	/*
	 * 이미 UsernamePasswordAuthenticationToken 이 인증된 경우에 UsernamePasswordAuthenticationToken를 다시 발급하는 메소드
	 * ex) jwt filter에서 access token이 유효할 때, refresh token으로 access token을 재발급할 때.
	 * @tip refresh token으로 access token을 재발급할 때는 refresh token이 이미 인증되었기 때문에 인증된 상태이다.
	 * @tip 인증된 UsernamePasswordAuthenticationToken을 생성할 때 비밀번호 대신 빈 문자열을 사용하는 이유
	 * 		-> 보안, 불필요한 정보 제거
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 * */
	public Authentication getAuthentication(String token) {
		String username = Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody().getSubject();
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);

		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	/*
	 * jwt token에서 email과 authorities를 가져온다.
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 * */
	public Map<String, String> getEmailAndAuthFromToken(String token) {
		Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
		Claims claims = claimsJws.getBody();

		List<String> authorityList = claims.get("auth", List.class);
		String authorities = String.join(",", authorityList);

		String email = claims.get("email", String.class);

		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("email", email);
		resultMap.put("auth", authorities);

		return resultMap;
	}

	/*
	 * HttpServletRequest의 Cookie와 HttpSession에서 원하는 token을 찾는다.
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 * */
	public String findToken(HttpServletRequest request, String cookie_id) {

		// token id 가 cookie에 있는지 찾는다.
		Cookie[] cookies = request.getCookies();
		String cookieValue = getCookieValue(cookies, cookie_id);

		if (cookieValue == null) {
			return null;
		}

		// 세션을 가져옵니다. 세션이 없으면 null을 반환합니다.
		HttpSession session = request.getSession(false);
		if (session == null) {
			return null;
		}

		return (String)session.getAttribute(cookieValue);
	}

	private String getCookieValue(Cookie[] cookies, String cookieName) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/*
	 * jwt token의 유효성을 검사한다.
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 * */
	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret_key).parseClaimsJws(token);
		} catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
			log.info("잘못된 JWT 서명입니다.");
			// throw new AuthRuntimeException(AuthExceptionEnum.AUTH_JWT_SIGNATURE_EXCEPTION);
			return false;
		} catch (ExpiredJwtException e) {
			log.info("만료된 JWT 토큰입니다.");
			// throw new AuthRuntimeException(AuthExceptionEnum.AUTH_JWT_EXPIRED_EXCEPTION);
			return false;
		} catch (UnsupportedJwtException e) {
			log.info("지원하지 않는 JWT토큰입니다.");
			// throw new AuthRuntimeException(AuthExceptionEnum.AUTH_JWT_SUPPORT_EXCEPTION);
			return false;
		} catch (IllegalArgumentException e) {
			log.info("JWT토큰이 잘못되었습니다.");
			// throw new AuthRuntimeException(AuthExceptionEnum.AUTH_JWT_SIGNATURE_EXCEPTION);
			return false;
		}
		return true;
	}

}
