package com.ssafy.moneykeeperbackend.accountbook.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ssafy.moneykeeperbackend.common.BaseEntity;
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
	private int id;

	@NotNull
	private String name;

	@OneToMany(mappedBy = "majorSpendingClassification")
	List<SpendingClassification> spendingClassifications;
}
