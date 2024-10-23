package coupon.domain;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum CouponCategory {
    FASHION("패션"),
    ELECTRONICS("가전"),
    FURNITURE("가구"),
    FOOD("식품"),
    ;

    private final String name;

    CouponCategory(String name) {
        this.name = name;
    }

    public CouponCategory from(String name) {
        return Arrays.stream(CouponCategory.values())
                .filter(category -> category.name.equals(name))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }
}
