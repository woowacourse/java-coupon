package coupon.domain.coupon;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Category {

    FASHION("패션"),
    HOME_APPLIANCES("가전"),
    FURNITURE("가구"),
    FOOD("식품");

    private final String name;
}
