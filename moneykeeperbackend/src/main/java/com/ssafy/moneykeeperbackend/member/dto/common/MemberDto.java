package com.ssafy.moneykeeperbackend.member.dto.common;

import com.sun.istack.NotNull;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberDto {

	private Long id;

	private String email;

	private String nickname;

}
