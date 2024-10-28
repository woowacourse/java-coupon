package coupon.entity.coupon;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CouponCategory {
    FASHION("패션"),
    ELECTRONICS("가전"),
    FURNITURE("가구"),
    FOOD("식품");

    private final String displayName;
}
