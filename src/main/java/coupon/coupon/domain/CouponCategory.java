package coupon.coupon.domain;

import lombok.Getter;

@Getter
public enum CouponCategory {
    FASHION("패션"),
    ELECTRONICS("가전"),
    FURNITURE("가구"),
    FOOD("식품");

    CouponCategory(String categoryName) {
        this.categoryName = categoryName;
    }

    private final String categoryName;
}
