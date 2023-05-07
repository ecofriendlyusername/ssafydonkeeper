package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.member.entity.Member;

@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long> {

	Page<Spending> findAllByMember(Member member, Pageable pageable);

	Page<Spending> findAllByMemberAndDateBetween(Member member, LocalDate startDate, LocalDate endDate, Pageable pageable);

	@Query("SELECT SUM(s.amount) FROM Spending s WHERE s.member = :member AND s.date BETWEEN :startDate AND :endDate")
	int getTotalAmountByMemberAndDateBetween(Member member, LocalDate startDate, LocalDate endDate);

}
