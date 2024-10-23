package coupon.domain;

import coupon.exception.CouponException;
import java.util.Arrays;

public enum Category {

    FASHION,
    APPLIANCE,
    FURNITURE,
    FOOD;

    public static Category from(String name) {
        return Arrays.stream(values())
                .filter(value -> value.name().equals(name))
                .findAny()
                .orElseThrow(() -> new CouponException(String.format("%s에 해당하는 Category가 존재하지 않습니다.", name)));
    }
}
