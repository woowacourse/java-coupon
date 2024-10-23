package coupon.domain;

import java.util.Arrays;

public enum Category {
    FASHION,
    ELECTRONICS,
    FURNITURE,
    FOOD;

    public static Category from(String value) {
        return Arrays.stream(values())
                .filter(category -> category.name().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }
}
