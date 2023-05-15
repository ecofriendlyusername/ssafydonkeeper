package com.ssafy.moneykeeperbackend.statistics.repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import com.ssafy.moneykeeperbackend.accountbook.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.member.entity.Member;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthSpendingRecordByClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MonthSpendingRecordByClassRepository extends JpaRepository<MonthSpendingRecordByClass, Long> {
    List<MonthSpendingRecordByClass> findByMemberAndYmonthBetween(Member member, LocalDate firstMonth, LocalDate lastMonth);

    MonthSpendingRecordByClass findByMemberAndYmonthAndMajorSpendingClass(Member member, LocalDate curMonth, MajorSpendingClassification msc);

    List<MonthSpendingRecordByClass> findByMemberAndMajorSpendingClassAndYmonthBetween(Member member, MajorSpendingClassification msc, LocalDate firstMonth, LocalDate lastMonth);
}

