package com.ssafy.moneykeeperbackend.member.entity;

import javax.persistence.*;

import com.ssafy.moneykeeperbackend.accountbook.entity.Asset;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.common.BaseEntity;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {

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

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Spending> spendings;

	@OneToMany(mappedBy = "member")
	List<SpendingClassification> spendingClassifications;

	@OneToMany(mappedBy = "member")
	List<Asset> assets;

	public void setNickname(String name) {
		this.nickname = name;
	}

	public void setOauthAceessToken(String name) {
		this.nickname = name;
	}

}
