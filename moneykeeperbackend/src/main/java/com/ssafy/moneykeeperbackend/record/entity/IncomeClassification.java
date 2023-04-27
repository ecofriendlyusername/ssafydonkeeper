package com.ssafy.moneykeeperbackend.record.entity;

import com.ssafy.moneykeeperbackend.statistics.entity.MonthRecord;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncomeClassification {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private String name;

    @OneToMany(mappedBy = "incomeClassification", fetch = FetchType.LAZY)
    private List<Income> incomeList;
}
