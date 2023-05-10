package com.ssafy.moneykeeperbackend.statistics.entity;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncomeGroup {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private int below;

    @NonNull
    private int base;

    @OneToMany(mappedBy = "incomeGroup", fetch = FetchType.LAZY)
    private List<Member> member;

    @OneToMany(mappedBy = "incomeGroup", fetch = FetchType.LAZY)
    private List<GroupSpending> groupSpending;

    @Builder
    public IncomeGroup(int below, int base) {
        this.base = base;
        this.below = below;
    }
}
