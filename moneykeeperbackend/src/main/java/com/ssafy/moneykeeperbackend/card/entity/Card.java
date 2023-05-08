package com.ssafy.moneykeeperbackend.card.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {
    @Id
    private Long id;

    private String company;

    private String name;

    private int annualFee;

    private int minimumSpending;

    private String benefitDetail;

    private String benefits;

    @Builder
    public Card(int below) {
    }
}
