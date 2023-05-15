package com.ssafy.moneykeeperbackend.accountbook.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PostPersist;
import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssafy.moneykeeperbackend.accountbook.repository.MajorSpendingClassificationRepository;
import com.ssafy.moneykeeperbackend.accountbook.repository.SpendingClassificationRepository;
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
public class SpendingClassification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "spending_classification_id")
	private Long id;

	@NotNull
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "major_spending_classification_id")
	private MajorSpendingClassification majorSpendingClassification;

	@OneToMany(mappedBy = "spendingClassification", fetch = FetchType.LAZY)
	private List<Budget> budgets;

	@OneToMany(mappedBy = "spendingClassification")
	List<Spending> spendings;

	public void setMajorSpendingClassification(MajorSpendingClassification majorSpendingClassification) {
		this.majorSpendingClassification = majorSpendingClassification;
	}

	public void setName(String name) {
		this.name = name;
	}

}
