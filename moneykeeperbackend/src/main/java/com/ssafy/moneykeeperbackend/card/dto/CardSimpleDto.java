package com.ssafy.moneykeeperbackend.card.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CardSimpleDto {
    Long id;

    String company;

    String name;

    String imgPath;


    @Builder
    public CardSimpleDto(Long id, String company, String name, String imgPath) {
        this.id = id;
        this.company = company;
        this.name = name;
        this.imgPath = imgPath;
    }
}
