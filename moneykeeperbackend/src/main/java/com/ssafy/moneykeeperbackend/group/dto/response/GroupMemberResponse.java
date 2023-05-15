package com.ssafy.moneykeeperbackend.group.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupMemberResponse {

	private Long member_id;

	private String email;

	private String nickname;

	private int thisMonthTotalAmount;

}
