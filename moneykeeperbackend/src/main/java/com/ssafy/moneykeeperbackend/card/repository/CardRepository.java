package com.ssafy.moneykeeperbackend.card.repository;

import com.ssafy.moneykeeperbackend.card.entity.Card;
import com.ssafy.moneykeeperbackend.statistics.entity.IncomeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {
}
