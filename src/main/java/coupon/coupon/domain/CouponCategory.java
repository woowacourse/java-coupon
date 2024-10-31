package coupon.coupon.domain;

public enum CouponCategory {

    FASHION("패션"),
    FURNITURE("가전"),
    APPLIANCE("가구"),
    FOOD("식품"),
    ;

    private final String category;

    CouponCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
