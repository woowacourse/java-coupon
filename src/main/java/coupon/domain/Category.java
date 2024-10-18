package coupon.domain;

public enum Category {

    FOOD("식품"),
    FURNITURE("가구"),
    HOME_APPLIANCES("가전"),
    FASHION("패션"),
    ;

    public final String type;

    Category(final String type) {
        this.type = type;
    }
}
