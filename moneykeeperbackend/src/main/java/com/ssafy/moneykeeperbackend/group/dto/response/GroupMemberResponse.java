package com.ssafy.moneykeeperbackend.group.dto.response;

import com.ssafy.moneykeeperbackend.group.entity.GroupRole;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupMemberResponse {

	private Long member_id;

	private String email;

	private String nickname;

	private String groupRole;

	private int thisMonthTotalAmount;

}
