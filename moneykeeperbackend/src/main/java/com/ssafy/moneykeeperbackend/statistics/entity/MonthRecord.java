package com.ssafy.moneykeeperbackend.statistics.entity;
import com.ssafy.moneykeeperbackend.record.entity.SpendingClassification;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MonthRecord {
    @Id
    @GeneratedValue
    private Long id;
    Long testId;
    @ManyToOne
    private SpendingClassification spendingClassification;
    private LocalDate ymonth;
    private int amount;
    @Builder
    public MonthRecord(Long testId, LocalDate month, SpendingClassification spendingClassification, int amount) {
        this.spendingClassification = spendingClassification;
        this.amount = amount;
        this.ymonth = month;
        this.testId = testId;
    }
}
