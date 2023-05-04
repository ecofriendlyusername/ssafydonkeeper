package com.ssafy.moneykeeperbackend.accountbook.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.Income;
import com.ssafy.moneykeeperbackend.accountbook.entity.IncomeClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;
import com.ssafy.moneykeeperbackend.member.entity.Member;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

	Optional<Income> findById(Long id);

	Page<Income> findAllByMember(Member member, Pageable pageable);

	Page<Income> findAllByMemberAndDateBetween(Member member, LocalDate startDate, LocalDate endDate, Pageable pageable);

}
