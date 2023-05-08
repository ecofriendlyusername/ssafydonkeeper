package com.ssafy.moneykeeperbackend.statistics.repository;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthIncomeRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MonthIncomeRecordRepository extends JpaRepository<MonthIncomeRecord, Long> {
    List<MonthIncomeRecord> findByMemberAndYmonthBetween(Member member, LocalDate start, LocalDate end);

    Optional<MonthIncomeRecord> findByMemberAndYmonth(Member member, LocalDate ymonth);
}
