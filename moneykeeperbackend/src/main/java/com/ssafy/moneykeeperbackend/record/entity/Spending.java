package com.ssafy.moneykeeperbackend.record.entity;

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
public class Spending {
    @Id
    @GeneratedValue
    private Long id;

    private Long testId;
    @ManyToOne
    @NonNull
    private SpendingClassification spendingClassification;
    @NonNull
    private int amount;
    @NonNull
    private LocalDate date;
    private String detail;
    private String memo;
    @Builder
    public Spending(SpendingClassification spendingClassification, int amount, LocalDate date, String detail, String memo, Long testId) {
        this.spendingClassification = spendingClassification;
        this.amount = amount;
        this.detail = detail;
        this.memo = memo;
        this.date = date;
        this.testId = testId;
    }
}
