package coupon.coupon.domain;

import java.util.Arrays;

public enum CouponCategory {
    FASHION, ELECTRONICS, FURNITURE, FOOD;

    public static CouponCategory getCategory(String category) {
        return Arrays.stream(CouponCategory.values())
                .filter(value -> value.name().equals(category))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("일치하는 카테고리가 없습니다."));
    }
}
