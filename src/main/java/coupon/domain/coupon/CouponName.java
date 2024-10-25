package coupon.domain.coupon;

import coupon.exception.CouponNameValidationException;
import jakarta.persistence.Embeddable;

@Embeddable
public class CouponName {
    public static final int MAX_LENGTH = 30;

    private String name;

    public CouponName(String name) {
        validateLength(name);
        this.name = name;
    }

    protected CouponName() {
    }

    private void validateLength(String name) {
        if (name.isEmpty() || name.length() > MAX_LENGTH) {
            throw new CouponNameValidationException("쿠폰 이름은 1자 이상 %d자 이하여야 합니다.".formatted(MAX_LENGTH));
        }
    }
}
