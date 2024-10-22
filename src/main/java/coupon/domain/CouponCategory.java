package coupon.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum CouponCategory {

    FASHION("패션"),
    ELECTRONICS("가전"),
    FURNITURE("가구"),
    FOOD("식품");

    private String name;

    CouponCategory(String name) {
        this.name = name;
    }

    public static CouponCategory from(String name) {
        return Arrays.stream(values())
                .filter(value -> value.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid category."));
    }
}
