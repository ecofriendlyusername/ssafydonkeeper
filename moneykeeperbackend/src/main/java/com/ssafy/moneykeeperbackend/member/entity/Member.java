package com.ssafy.moneykeeperbackend.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	public void setNickname(String name) {
		this.nickname = name;
	}

	public void setOauthAceessToken(String name) {
		this.nickname = name;
	}

}
