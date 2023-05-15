package com.ssafy.moneykeeperbackend.member.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.member.dto.response.MemberResponse;

public interface MemberService {

	List<MemberResponse> getMembersByEmail(String email);
}
