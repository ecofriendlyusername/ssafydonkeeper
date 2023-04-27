package com.ssafy.moneykeeperbackend.member.serviceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.ssafy.moneykeeperbackend.exception.auth.AuthExceptionEnum;
import com.ssafy.moneykeeperbackend.exception.auth.AuthRuntimeException;
import com.ssafy.moneykeeperbackend.member.dto.common.MemberDto;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.member.entity.Role;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.member.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private final MemberRepository memberRepository;

	@Value("${kakao.rest-api-key}")
	private String API_KEY;

	@Value("${kakao.redirect-uri}")
	private String REDIRECT_URI;

	private String TOKEN_REQUEST_URL = "https://kauth.kakao.com/oauth/token";

	private String USER_REQUEST_URL = "https://kapi.kakao.com/v2/user/me";

	/*
	 * 카카오 로그인
	 *
	 * @date 2023.04.26
	 * @author 정민지
	 * */
	@Override
	public MemberDto getKakao(String code) {

		try {
			URL url = new URL(TOKEN_REQUEST_URL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			//POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			//POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=" + API_KEY);
			sb.append("&redirect_uri=" + REDIRECT_URI);
			sb.append("&code=" + code);
			bw.write(sb.toString());
			bw.flush();

			//결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("토큰 가져올 때 responseCode : " + responseCode);
			if (responseCode != 200) {
				throw new AuthRuntimeException(AuthExceptionEnum.AUTH_AUTHORIZATION_EXCEPTION);
			}

			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}

			br.close();
			bw.close();

			//Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			return joinOrLogin(element.getAsJsonObject().get("access_token").getAsString());

		} catch (IOException e) {
			throw new AuthRuntimeException(AuthExceptionEnum.AUTH_AUTHORIZATION_EXCEPTION);
		}
	}

	public MemberDto joinOrLogin(String accessToken) {

		try {
			URL url = new URL(USER_REQUEST_URL);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Bearer " + accessToken); //전송할 header 작성, access_token전송

			//결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("유저 정보 가져올 때 responseCode : " + responseCode);

			if (responseCode != 200) {
				throw new AuthRuntimeException(AuthExceptionEnum.AUTH_AUTHORIZATION_EXCEPTION);
			}

			//요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			log.info("response body : " + result);

			//Gson 라이브러리로 JSON파싱
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			long id = element.getAsJsonObject().get("id").getAsLong();
			String nickname = element.getAsJsonObject()
				.get("properties")
				.getAsJsonObject()
				.get("nickname")
				.getAsString();
			boolean hasEmail = element.getAsJsonObject()
				.get("kakao_account")
				.getAsJsonObject()
				.get("has_email")
				.getAsBoolean();
			String email = "";

			if (hasEmail) {
				email = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString();
			} else {
				throw new AuthRuntimeException(AuthExceptionEnum.KAKAO_SHOULD_HAVE_EMAIL);
			}
			br.close();

			Member member = memberRepository.findByEmail(email).orElse(null);
			String password = passwordEncoder.encode("kakao" + String.valueOf(id));

			if (member == null) {
				member = Member.builder()
					.email(email)
					.nickname(nickname)
					.oauth("kakao")
					.password(password)
					.oauthAceessToken(accessToken)
					.role(Role.ROLE_USER)
					.build();

				memberRepository.saveAndFlush(member);
			} else {
				member.setNickname(nickname);
				member.setOauthAceessToken(accessToken);
			}

			return MemberDto.builder()
				.id(member.getId())
				.email(member.getEmail())
				.nickname(member.getNickname())
				.password("kakao" + String.valueOf(id))
				.build();

		} catch (IOException e) {
			throw new AuthRuntimeException(AuthExceptionEnum.AUTH_AUTHORIZATION_EXCEPTION);
		}
	}


}
