package coupon.domain;

import java.io.Serializable;

import coupon.exception.InvalidCouponNameException;
import lombok.Getter;

@Getter
public class CouponName implements Serializable {

    public static final int MAX_LENGTH = 30;

    private final String value;

    public CouponName(final String value) {
        validateLength(value);
        this.value = value;
    }

    private void validateLength(final String value) {
        if (value == null || value.isBlank() || value.length() > MAX_LENGTH) {
            throw new InvalidCouponNameException("쿠폰 이름이 올바르지 않습니다.");
        }
    }
}
