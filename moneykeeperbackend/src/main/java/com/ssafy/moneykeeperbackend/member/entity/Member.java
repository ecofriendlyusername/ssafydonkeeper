package com.ssafy.moneykeeperbackend.member.entity;

import javax.persistence.*;

import com.ssafy.moneykeeperbackend.record.entity.Spending;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthRecord;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "member_id")
	private Long id;

	@NotNull
	private String email;

	@NotNull
	private String nickname;

	@NotNull
	private String oauth;

	@NotNull
	private String oauthAceessToken;

	@NotNull
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	List<Spending> spendings;
	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	List<MonthRecord> monthRecords;
	public void setNickname(String name) {
		this.nickname = name;
	}

	public void setOauthAceessToken(String name) {
		this.nickname = name;
	}

}
