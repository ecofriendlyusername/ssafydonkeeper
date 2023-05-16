package com.ssafy.moneykeeperbackend.circle.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ssafy.moneykeeperbackend.common.BaseEntity;
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
public class Circle extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "circle_id")
	private Long id;

	@NotNull
	private String name;

	@OneToMany(mappedBy = "circle", cascade = CascadeType.ALL, orphanRemoval = true)
	List<MemberCircle> memberCircles;

	public void setName(String name) {
		this.name = name;
	}
}
