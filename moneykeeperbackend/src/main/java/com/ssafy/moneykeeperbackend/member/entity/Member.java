package com.ssafy.moneykeeperbackend.member.entity;

import javax.persistence.*;

import com.ssafy.moneykeeperbackend.accountbook.entity.Asset;
import com.ssafy.moneykeeperbackend.accountbook.entity.Budget;
import com.ssafy.moneykeeperbackend.accountbook.entity.Income;
import com.ssafy.moneykeeperbackend.accountbook.entity.IncomeClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.common.BaseEntity;
import com.ssafy.moneykeeperbackend.statistics.entity.IncomeGroup;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecord;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecordByClass;
import com.ssafy.moneykeeperbackend.statistics.entity.SpendingGroup;
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

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Income> incomes;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	List<SpendingClassification> spendingClassifications;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	List<IncomeClassification> incomeClassifications;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Asset> assets;

	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Budget> budgets;

	@ManyToOne
	private IncomeGroup incomeGroup;
	@ManyToOne
	private SpendingGroup spendingGroup;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	List<MonthSpendingRecordByClass> monthSpendingRecordByClasses;

	@OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
	List<MonthSpendingRecord> monthSpendingRecords;

	public void setSpendingGroup(SpendingGroup spendingGroup) { this.spendingGroup = spendingGroup; }
	public void setIncomeGroup(IncomeGroup incomeGroup) { this.incomeGroup = incomeGroup; }

	public void setNickname(String name) {
		this.nickname = name;
	}

	public void setOauthAceessToken(String name) {
		this.nickname = name;
	}
}
