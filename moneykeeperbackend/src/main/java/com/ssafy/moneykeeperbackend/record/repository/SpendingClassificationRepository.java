package com.ssafy.moneykeeperbackend.record.repository;

import com.ssafy.moneykeeperbackend.record.entity.Spending;
import com.ssafy.moneykeeperbackend.record.entity.SpendingClassification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpendingClassificationRepository extends JpaRepository<SpendingClassification, Long> {
    SpendingClassification findByName(String name);
}
