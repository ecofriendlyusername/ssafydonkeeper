package com.ssafy.moneykeeperbackend.group.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GroupRequest {

	private String name;

	private List<Long> member_ids;

}
