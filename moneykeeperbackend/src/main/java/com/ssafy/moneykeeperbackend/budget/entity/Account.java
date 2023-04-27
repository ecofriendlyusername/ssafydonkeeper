package com.ssafy.moneykeeperbackend.budget.entity;

import com.ssafy.moneykeeperbackend.member.entity.Member;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    @Id
    @GeneratedValue
    private Long id;
    @NonNull
    private int balance;

    @Builder
    public Account(int balance, Member member) {
        this.balance = balance;
        this.member = member;
    }
    @OneToOne
    private Member member;
}
