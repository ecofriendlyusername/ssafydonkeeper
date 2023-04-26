package com.ssafy.moneykeeperbackend.statistics.repository;

import com.ssafy.moneykeeperbackend.record.entity.SpendingClassification;
import com.ssafy.moneykeeperbackend.statistics.entity.MonthRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MonthRecordRepository extends JpaRepository<MonthRecord, Long> {
    List<MonthRecord> findByTestIdAndYmonthBetween(Long testId, LocalDate firstMonth, LocalDate lastMonth);
    List<MonthRecord> findByTestIdAndYmonth(Long testId, LocalDate curMonth);
    MonthRecord findByTestIdAndSpendingClassificationAndYmonth(Long testId, SpendingClassification spendingClassification, LocalDate curMonth);
}
