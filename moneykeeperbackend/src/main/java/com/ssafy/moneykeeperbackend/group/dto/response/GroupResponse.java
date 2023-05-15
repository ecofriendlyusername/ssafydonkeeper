package com.ssafy.moneykeeperbackend.group.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupResponse {
	private Long group_id;

	private String name;

	private List<GroupMemberResponse> top3Members;

	private List<GroupMemberResponse> allMembers;
}
