package com.ssafy.moneykeeperbackend.statistics.entity;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SpendingGroup {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private int below;

    private int base;
    private int groupAvg;
    @OneToMany(mappedBy = "spendingGroup", fetch = FetchType.LAZY)
    private List<Member> member;

    @Builder
    public SpendingGroup(int below, int base) {
        this.base = base;
        this.below = below;
    }
}