package com.ssafy.moneykeeperbackend.circle.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.circle.dto.request.CircleRequest;
import com.ssafy.moneykeeperbackend.circle.dto.response.CircleResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface CircleService {

	CircleResponse addCircle(CircleRequest circleRequest, Member member);

	CircleResponse getCircleInfo(Long circleId, int year, int month);

	void deleteMemberCircle(Long circleId, Member member);

	void deleteCircle(Long circleId, Member member);

	List<IdNameDTO> getAllMembersCircle(Member member);

	boolean existsCircleName(String name);

	// boolean isLoginMemberisLeader(Long circleId, Member member);
}
