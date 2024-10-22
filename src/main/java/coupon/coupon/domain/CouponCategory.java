package coupon.coupon.domain;

import java.util.Arrays;

public enum CouponCategory {

    FASHION,
    ELECTRONICS,
    FURNITURE,
    FOOD,
    ;

    public static CouponCategory from(String categoryName) {
        return Arrays.stream(values())
                .filter(category -> category.name().equalsIgnoreCase(categoryName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다: %s".formatted(categoryName)));
    }
}
