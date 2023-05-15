package com.ssafy.moneykeeperbackend.accountbook.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.ssafy.moneykeeperbackend.common.BaseEntity;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Budget extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "budget_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@NotNull
	private int year;

	@NotNull
	private int month;

	@NotNull
	private int amount;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spending_classification_id")
	private SpendingClassification spendingClassification;

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setSpendingClassification(SpendingClassification spendingClassification) {
		this.spendingClassification = spendingClassification;
	}
}
