package com.ssafy.moneykeeperbackend.record.repository;

import com.ssafy.moneykeeperbackend.record.entity.IncomeClassification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeClassificationRepository extends JpaRepository<IncomeClassification, Long> {
}
