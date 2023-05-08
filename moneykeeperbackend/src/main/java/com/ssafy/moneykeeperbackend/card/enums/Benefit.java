package com.ssafy.moneykeeperbackend.card.enums;


public enum Benefit {
    FOODAPP("식비"),
    FOOD("식비"),

    ICECREAM("카페"),
    CAFE("카페"),
    BAKERY("카페"),

    LIFE("생활"),
    MART("생활"),
    CONVENIENCESTORE("생활"),
    RENTAL("생활"),
    INTERIOR("생활"),

    ONLINESHOPPING("온라인/쇼핑"),
    SOCIALCOMMERCE("온라인/쇼핑"),
    JICKGU("온라인/쇼핑"),

    SHOPPING("패션/쇼핑"),

    BEAUTY("뷰티/미용"),
    HAIR("뷰티/미용"),
    COSMETIC("뷰티/미용"),

    TRAIN("교통"),
    TRAFFIC("교통"),
    CAR("교통"),
    TAXI("교통"),
    KOBUS("교통"),

    OIL("자동차"),
    CARMAINTENANCE("자동차"),
    CARINSURANCE("자동차"),
    HIPASS("자동차"),

    MOBILE("주거/통신"),
    BILL("주거/통신"),

    HOSPITAL("의료/건강"),
    FITNESS("의료/건강"),
    DRUGSTORE("의료/건강"),

    FINANCE("금융"),
    MONTHLYINSTALLMENT("금융"),


    MOVIE("문화/여가"),
    MUSIC("문화/여가"),
    BOOK("문화/여가"),
    THEMEPARK("문화/여가"),
    GOLF("문화/여가"),
    SUBSCRIBE("문화/여가"),
    GAME("문화/여가"),

    SPORTS("문화/여가"),
    STADIUM("문화/여가"),
    TICKET("문화/여가"),

    FLIGHTTICKET("여행/숙박"),
    TRAVEL("여행/숙박"),
    HOTEL("여행/숙박"),
    COMMISSION("여행/숙박"),
    AIRPORT("여행/숙박"),
    MILEAGE("여행/숙박"),
    LOUNGE("여행/숙박"),
    PREMIUM("여행/숙박"),
    FOREGINCARD("여행/숙박"),

    EDU("교육/학습"),
    CENTER("교육/학습"),

    DAYCARE("자녀/육아"),
    HAPPY("자녀/육아"),

    PETHOSPITAL("반려동물"),
    PET("반려동물");

    private final String label;


    private Benefit(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
