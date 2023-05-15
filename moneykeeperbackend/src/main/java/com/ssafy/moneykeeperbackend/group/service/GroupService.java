package com.ssafy.moneykeeperbackend.group.service;

import java.util.List;

import com.ssafy.moneykeeperbackend.accountbook.dto.IdNameDTO;
import com.ssafy.moneykeeperbackend.group.dto.request.GroupRequest;
import com.ssafy.moneykeeperbackend.group.dto.response.GroupResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface GroupService {

	GroupResponse addGroup(GroupRequest groupRequest, Member member);

	GroupResponse getGroupInfo(Long groupId, int year, int month);

	void deleteMemberGroup(Long groupId, Member member);

	void deleteGroup(Long groupId, Member member);

	List<IdNameDTO> getAllMembersGroup(Member member);
}
