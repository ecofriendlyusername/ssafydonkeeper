package com.ssafy.moneykeeperbackend.statistics.repository;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MonthSpendingRecordRepository extends JpaRepository<MonthSpendingRecord, Long> {
    Optional<MonthSpendingRecord> findByMemberAndYmonth(Member member, LocalDate ymonth);

    List<MonthSpendingRecord> findByMemberAndYmonthBetween(Member member, LocalDate start, LocalDate end);
}
