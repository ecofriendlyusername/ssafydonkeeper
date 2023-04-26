package com.ssafy.moneykeeperbackend.record.entity;

import com.ssafy.moneykeeperbackend.statistics.entity.MonthRecord;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpendingClassification {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;
    @OneToMany(mappedBy = "spendingClassification", fetch = FetchType.LAZY)
    private List<Spending> spendingList;

    @OneToMany(mappedBy = "spendingClassification", fetch = FetchType.LAZY)
    private List<MonthRecord> monthRecordList;

    @Builder
    public SpendingClassification(String name) {
        this.name = name;
    }
}
