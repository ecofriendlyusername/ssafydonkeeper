package com.ssafy.moneykeeperbackend.accountbook.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.ssafy.moneykeeperbackend.common.BaseEntity;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Asset extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "asset_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@NotNull
	private String name;

	@NotNull
	private Long total_account;

	@OneToMany(mappedBy = "asset")
	List<Spending> spendings;

}
