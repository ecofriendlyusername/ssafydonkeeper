package com.ssafy.moneykeeperbackend.record.repository;

import com.ssafy.moneykeeperbackend.record.entity.Spending;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpendingRepository extends JpaRepository<Spending, Long> {
}
