package com.ssafy.moneykeeperbackend.card.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Card {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String imgPath;

    @Column(nullable = false)
    private String company;

    @Column(nullable = false)
    private int annualFee;

    @Column(nullable = false)
    private int minimumSpending;

    @Column(nullable = false)
    private String benefitDetail;

    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private int isCredit;

    private String benefitImportant;

//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int food;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int bar;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int cafe;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int life;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int onlineshopping;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int fashionshopping;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int beauty;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int transportation;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int car;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int bills;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int healthcare;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int finance;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int entertainment;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int travel;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int education;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int children;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int pets;
//
//    @Column(columnDefinition = "TINYINT DEFAULT 0")
//    private int people;

    @Builder
    public Card(int isCredit, String name, String company, int annualFee, int minimumSpending, String benefitDetail, String benefitImportant, String imgPath) {
        this.name = name;
        this.company = company;
        this.annualFee = annualFee;
        this.minimumSpending = minimumSpending;
        this.benefitDetail = benefitDetail;
        this.benefitImportant = benefitImportant;
        this.isCredit = isCredit;
        this.imgPath = imgPath;
//        this.benefits = benefits;
//        this.food = food;
//        this.bar = bar;
//        this.cafe = cafe;
//        this.life = life;
//        this.onlineshopping = onlineshopping;
//        this.fashionshopping = fashionshopping;
//        this.beauty = beauty;
//        this.transportation = transportation;
//        this.car = car;
//        this.bills = bills;
//        this.healthcare = healthcare;
//        this.finance = finance;
//        this.entertainment = entertainment;
//        this.travel = travel;
//        this.education = education;
//        this.children = children;
//        this.pets = pets;
//        this.people = people;
    }
}
// int food, int bar, int cafe, int life, int onlineshopping, int fashionshopping, int beauty, int transportation, int car, int bills, int healthcare, int finance, int entertainment, int travel, int education, int children, int pets, int people