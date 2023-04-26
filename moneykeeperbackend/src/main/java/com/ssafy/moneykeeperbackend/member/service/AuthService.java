package com.ssafy.moneykeeperbackend.member.service;

import com.ssafy.moneykeeperbackend.member.dto.common.MemberDto;

public interface AuthService {

	MemberDto getKakao(String code);
}
