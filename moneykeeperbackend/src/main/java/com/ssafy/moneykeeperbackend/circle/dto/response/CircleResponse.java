package com.ssafy.moneykeeperbackend.circle.dto.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CircleResponse {
	private Long circle_id;

	private String name;

	private Long leader_id;

	private List<CircleMemberResponse> top3Members;

	private List<CircleMemberResponse> allMembers;
}
