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
    @OneToMany(mappedBy = "spendingGroup", fetch = FetchType.LAZY)
    private List<Member> member;

    @Builder
    public IncomeGroup(int below) {
        this.below = below;
    }
}
