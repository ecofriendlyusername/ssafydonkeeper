package com.ssafy.moneykeeperbackend.budget.repository;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.budget.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
    Budget findByMember(Member member);

    Budget findByMemberAndDate(Member member, LocalDate date);
}
