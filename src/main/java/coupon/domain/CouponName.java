package coupon.domain;

import coupon.exception.InvalidCouponNameException;
import lombok.Getter;

@Getter
public class CouponName {

    private final String value;

    public CouponName(final String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(final String value) {
        if (value == null || value.isBlank() || value.length() > 30) {
            throw new InvalidCouponNameException("쿠폰 이름이 올바르지 않습니다.");
        }
    }
}
