package coupon.domain;

public enum Category {

    FASHION("패션"), ELECTRONIC("가전"), FURNITURE("가구"), FOOD("식품");

    public final String value;

    Category(String value) {
        this.value = value;
    }
}
