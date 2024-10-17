package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record CouponName(@Column(name = "coupon_name", nullable = false) String value) {

    private static final int COUPON_NAME_MAX_LENGTH = 30;

    public CouponName {
        if (value.isBlank()) {
            throw new IllegalArgumentException("쿠폰 이름은 반드시 존재해야 합니다.");
        }

        if (value.length() > COUPON_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("쿠폰 이름 길이는 최대 " + COUPON_NAME_MAX_LENGTH + "자 이하입니다.");
        }
    }
}
