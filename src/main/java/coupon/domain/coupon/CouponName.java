package coupon.domain.coupon;

import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponName {

    private static final int MAX_LENGTH = 30;

    @Column(name = "name", nullable = false)
    private String value;

    public CouponName(String value) {
        validateIsNotBlank(value);
        validateMaxLength(value);
        this.value = value;
    }

    private void validateIsNotBlank(String value) {
        if (value == null || value.isBlank()) {
            throw new CouponException(ExceptionType.COUPON_NAME_BLANK);
        }
    }

    private void validateMaxLength(String value) {
        if (value.length() > MAX_LENGTH) {
            throw new CouponException(ExceptionType.COUPON_NAME_LENGTH_EXCEED);
        }
    }
}
