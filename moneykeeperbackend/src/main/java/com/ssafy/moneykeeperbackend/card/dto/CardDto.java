package com.ssafy.moneykeeperbackend.card.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardDto {

    Long id;

    String company;

    String name;

    int annualFee;

    int minimumSpending;

    String benefits;

    String imgPath;


    @Builder
    public CardDto(Long id, String company, String name, int annualFee, int minimumSpending, String benefits, String imgPath) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.annualFee = annualFee;
        this.minimumSpending = minimumSpending;
        this.benefits = benefits;
        this.imgPath = imgPath;
    }
}
