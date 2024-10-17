package coupon.domain;

import coupon.exception.CouponException;
import lombok.Getter;

@Getter
public class CouponName {

    private final String name;

    public CouponName(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new CouponException("쿠폰 이름은 반드시 존재해야 합니다.");
        }

        if (name.length() > 30) {
            throw new CouponException("쿠폰 이름은 최대 30자 이하여야 합니다.");
        }
    }
}
