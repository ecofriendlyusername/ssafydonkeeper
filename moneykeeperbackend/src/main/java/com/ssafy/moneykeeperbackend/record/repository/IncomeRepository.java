package com.ssafy.moneykeeperbackend.record.repository;

import com.ssafy.moneykeeperbackend.record.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
