package com.ssafy.moneykeeperbackend.card.enums;

public enum Category {
    FOOD("food", "식비"),
    BAR("bar", "술/유흥"),
    CAFE("cafe", "카페"),
    LIFE("life", "생활"),
    ONLINESHOPPING("onlineshopping", "온라인/쇼핑"),
    FASHIONSHOPPING("fashionshopping", "패션/쇼핑"),
    BEAUTY("beauty", "뷰티/미용"),
    TRANSPORTATION("transportation", "교통"),
    CAR("car", "자동차"),
    BILLS("bills", "주거/통신"),
    HEALTHCARE("healthcare", "의료/건강"),
    FINANCE("finance", "금융"),
    ENTERTAINMENT("entertainment", "문화/여가"),
    TRAVEL("travel", "여행/숙박"),
    EDUCATION("education", "교육/학습"),
    CHILDREN("children", "자녀/육아"),
    PETS("pets", "반려동물"),
    PEOPLE("people", "경조/선물");
    private final String key;
    private final String name;

    Category(String key, String name) {
        this.key = key;
        this.name = name;
    }
}
