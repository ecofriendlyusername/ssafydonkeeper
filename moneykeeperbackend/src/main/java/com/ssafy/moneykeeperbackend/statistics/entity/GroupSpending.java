package com.ssafy.moneykeeperbackend.statistics.entity;

import com.ssafy.moneykeeperbackend.accountbook.entity.MajorSpendingClassification;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"MAJOR_SPENDING_CLASS_MAJOR_SPENDING_CLASSIFICATION_ID", "INCOME_GROUP_ID","ymonth"})
})
public class GroupSpending {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private MajorSpendingClassification majorSpendingClass;
    @ManyToOne
    private IncomeGroup incomeGroup;
    private LocalDate ymonth;
    private int months;
    private int total;

    @Builder
    public GroupSpending(LocalDate ymonth, int months, int total, IncomeGroup incomeGroup, MajorSpendingClassification majorSpendingClass) {
        this.ymonth = ymonth;
        this.months = months;
        this.total = total;
        this.incomeGroup = incomeGroup;
        this.majorSpendingClass = majorSpendingClass;
    }
}
