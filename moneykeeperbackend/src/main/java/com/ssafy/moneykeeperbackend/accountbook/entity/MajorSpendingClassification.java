package com.ssafy.moneykeeperbackend.accountbook.entity;

import java.util.List;

import javax.persistence.*;

import com.ssafy.moneykeeperbackend.common.BaseEntity;
import com.ssafy.moneykeeperbackend.statistics.entity.GroupSpending;
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
public class MajorSpendingClassification extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "major_spending_classification_id")
	private Long id;

	@NotNull
	private String name;

	@OneToMany(mappedBy = "majorSpendingClassification")
	List<SpendingClassification> spendingClassifications;

	@OneToMany(mappedBy = "majorSpendingClassification", fetch = FetchType.LAZY)
	private List<Budget> budgets;

	@OneToMany(mappedBy = "majorSpendingClass", fetch = FetchType.LAZY)
	private List<GroupSpending> groupSpending;
}
