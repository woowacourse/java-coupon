package coupon.domain.coupon;

import java.util.Arrays;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import lombok.Getter;

@Getter
public enum CouponCategory {
    FASHION("패션"),
    ELECTRONICS("가전"),
    FURNITURE("가구"),
    GROCERIES("식품");

    private final String name;

    CouponCategory(String name) {
        this.name = name;
    }

    public static CouponCategory getCategory(String name) {
        return Arrays.stream(values())
                .filter(couponCategory -> couponCategory.name.equals(name))
                .findFirst()
                .orElseThrow(() -> new CouponException(ExceptionType.COUPON_CATEGORY_NOT_FOUND));
    }
}
