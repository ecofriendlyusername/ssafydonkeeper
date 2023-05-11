package com.ssafy.moneykeeperbackend.accountbook.entity;

import java.time.LocalDate;
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

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncomeClassification extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "income_classification_id")
	private Long id;

	@NotNull
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@OneToMany(mappedBy = "incomeClassification")
	List<Income> incomes;

	public void setName(String name) {
		this.name = name;
	}

}
