package coupon.coupon.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CouponCategory {

    FASHION("패션"),
    HOME_APPLIANCE("가전"),
    FURNITURE("가구"),
    FOOD("식품"),
    ;

    private final String value;
}
