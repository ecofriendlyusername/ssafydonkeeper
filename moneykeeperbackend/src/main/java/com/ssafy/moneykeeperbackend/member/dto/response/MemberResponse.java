package com.ssafy.moneykeeperbackend.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponse {

	private Long id;

	private String email;

	private String nickname;
}
