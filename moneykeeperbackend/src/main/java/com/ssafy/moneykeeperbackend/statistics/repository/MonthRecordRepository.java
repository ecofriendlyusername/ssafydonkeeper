package com.ssafy.moneykeeperbackend.statistics.repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MonthRecordRepository extends JpaRepository<MonthRecord, Long> {
    List<MonthRecord> findByMemberAndYmonthBetween(Member member, LocalDate firstMonth, LocalDate lastMonth);
    List<MonthRecord> findByMemberAndYmonth(Member member, LocalDate curMonth);
    MonthRecord findByMemberAndSpendingClassificationAndYmonth(Member member, SpendingClassification spendingClassification, LocalDate curMonth);
}
