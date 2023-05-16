package com.ssafy.moneykeeperbackend.circle.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CircleMemberResponse {

	private Long member_id;

	private String email;

	private String nickname;

	private String circleRole;

	private int thisMonthTotalAmount;

}
