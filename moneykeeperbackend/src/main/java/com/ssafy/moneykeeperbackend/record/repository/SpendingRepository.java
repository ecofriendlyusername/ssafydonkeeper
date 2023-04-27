package com.ssafy.moneykeeperbackend.record.repository;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.record.entity.Spending;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpendingRepository extends JpaRepository<Spending, Long> {
    List<Spending> findByMemberAndDateBetween(Member member, LocalDate start, LocalDate end);
}
