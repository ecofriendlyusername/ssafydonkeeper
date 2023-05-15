package com.ssafy.moneykeeperbackend.member.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.moneykeeperbackend.member.dto.response.MemberResponse;
import com.ssafy.moneykeeperbackend.member.repository.MemberRepository;
import com.ssafy.moneykeeperbackend.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Override
	public List<MemberResponse> getMembersByEmail(String email) {

		return memberRepository.findByEmailLike(email + "%").stream().map(member -> MemberResponse.builder()
				.id(member.getId())
				.nickname(member.getNickname())
				.email(member.getEmail())
				.build())
			.collect(Collectors.toList());
	}
}
