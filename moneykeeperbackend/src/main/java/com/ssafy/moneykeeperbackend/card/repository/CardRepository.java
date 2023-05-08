package com.ssafy.moneykeeperbackend.card.repository;

import com.ssafy.moneykeeperbackend.card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findByIsCreditAndAnnualFeeLessThanAndMinimumSpendingLessThan(int isCredit, int annualFee, int spendingAvgTotal);
}
