package com.ssafy.moneykeeperbackend.group.service;

import com.ssafy.moneykeeperbackend.group.dto.request.GroupRequest;
import com.ssafy.moneykeeperbackend.group.dto.response.GroupResponse;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface GroupService {

	GroupResponse addGroup(GroupRequest groupRequest, Member member);

	GroupResponse getGroupInfo(Long groupId, int year, int month);
}
