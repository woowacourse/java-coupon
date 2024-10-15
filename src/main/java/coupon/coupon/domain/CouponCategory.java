package coupon.coupon.domain;

import lombok.Getter;

@Getter
public enum CouponCategory {
    FASHION("패션"),
    ELECTRONICS("가전"),
    FURNITURE("가구"),
    FOOD("식품");

    private final String description;

    CouponCategory(String description) {
        this.description = description;
    }
}

