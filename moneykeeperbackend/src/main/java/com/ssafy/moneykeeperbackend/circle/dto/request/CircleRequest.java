package com.ssafy.moneykeeperbackend.circle.dto.request;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CircleRequest {

	private String name;

	private List<Long> member_ids;

}
