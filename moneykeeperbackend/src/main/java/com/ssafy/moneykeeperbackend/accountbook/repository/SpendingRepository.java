package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.member.entity.Member;

@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long> {

	List<Spending> findAllByMemberOrderByDateDescCreatedAtDesc(Member member);

	List<Spending> findAllByMemberAndDateBetweenOrderByDateDescCreatedAtDesc(Member member, LocalDate startDate, LocalDate endDate);

	@Query("SELECT SUM(s.amount) FROM Spending s WHERE s.member = :member AND s.date BETWEEN :startDate AND :endDate")
	int getTotalAmountByMemberAndDateBetween(Member member, LocalDate startDate, LocalDate endDate);

	@Query("SELECT COALESCE(SUM(s.amount), 0) FROM Spending s WHERE s.date = :date AND s.member = :member")
	int getTotalSpendingOnDateForMember(@Param("date") LocalDate date, @Param("member") Member member);

}
