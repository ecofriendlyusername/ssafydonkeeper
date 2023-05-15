package com.ssafy.moneykeeperbackend.accountbook.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.ssafy.moneykeeperbackend.common.BaseEntity;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Spending extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spending_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asset_id")
	private Asset asset;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "spending_classification_id")
	private SpendingClassification spendingClassification;

	@NotNull
	private LocalDate date;

	@NotNull
	private int amount;

	private String detail;

	private String memo;

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setSpendingClassification(
		SpendingClassification spendingClassification) {
		this.spendingClassification = spendingClassification;
	}

}
