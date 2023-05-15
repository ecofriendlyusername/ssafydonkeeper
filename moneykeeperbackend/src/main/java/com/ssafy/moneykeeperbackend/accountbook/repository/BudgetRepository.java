package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import com.ssafy.moneykeeperbackend.accountbook.entity.Budget;
import com.ssafy.moneykeeperbackend.member.entity.Member;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

	Optional<Budget> findByMemberAndYearAndMonthAndSpendingClassificationIsNull(Member member, int year,
		int month);

	Optional<Budget> findByMemberAndYearAndMonthAndSpendingClassificationId(Member member, int year, int month,
		Long spendingClassificationId);

	List<Budget> findByMemberAndYearAndMonth(Member member, int year, int month);

	List<Budget> findByMemberAndYearAndMonthAndSpendingClassificationIsNotNull(Member member, int year, int month);

	@Modifying
	void deleteByMemberAndYearAndMonth(Member member, int year, int month);

	@Modifying
	void deleteByMemberAndYearAndMonthAndSpendingClassificationId(Member member, int year, int month,
		Long spendingClassificationId);
}
