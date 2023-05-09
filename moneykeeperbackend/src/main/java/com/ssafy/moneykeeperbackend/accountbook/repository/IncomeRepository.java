package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.Income;
import com.ssafy.moneykeeperbackend.accountbook.entity.IncomeClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.member.entity.Member;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

	Optional<Income> findById(Long id);

	List<Income> findAllByMemberOrderByDateDescCreatedAtDesc(Member member);

	List<Income> findAllByMemberAndDateBetweenOrderByDateDescCreatedAtDesc(Member member, LocalDate startDate, LocalDate endDate);

	@Query("SELECT COALESCE(SUM(s.amount), 0) FROM Income s WHERE s.date = :date AND s.member = :member")
	int getTotalIncomeOnDateForMember(@Param("date") LocalDate date, @Param("member") Member member);
}
