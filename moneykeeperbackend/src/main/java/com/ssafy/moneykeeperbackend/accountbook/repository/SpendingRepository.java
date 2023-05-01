package com.ssafy.moneykeeperbackend.accountbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssafy.moneykeeperbackend.accountbook.entity.Spending;

@Repository
public interface SpendingRepository extends JpaRepository<Spending, Long> {
}
