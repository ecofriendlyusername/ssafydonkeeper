package com.ssafy.moneykeeperbackend.card.enums;

public enum Category {
    FOOD("식비"),
    BAR("술/유흥"),
    CAFE("카페"),
    LIFE( "생활"),
    ONLINESHOPPING( "온라인/쇼핑"),
    FASHIONSHOPPING("패션/쇼핑"),
    BEAUTY("뷰티/미용"),
    TRANSPORTATION("교통"),
    CAR("자동차"),
    BILLS( "주거/통신"),
    HEALTHCARE("의료/건강"),
    FINANCE("금융"),
    ENTERTAINMENT( "문화/여가"),
    TRAVEL("여행/숙박"),
    EDUCATION("교육/학습"),
    CHILDREN("자녀/육아"),
    PETS("반려동물"),
    PEOPLE("경조/선물");
    private final String name;

    Category(String name) {
        this.name = name;
    }
}
