package com.ssafy.moneykeeperbackend.accountbook.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SpendingClassificationRequest {

	private Long majorSpendingClassificationId;

	private String name;
}
