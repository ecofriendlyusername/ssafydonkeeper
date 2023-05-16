package com.ssafy.moneykeeperbackend.member.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.member.dto.response.MemberResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface MemberService {

	List<MemberResponse> getMembersByEmail(Member member, String email);
}
