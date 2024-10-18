package coupon.domain.coupon;

import coupon.domain.coupon.exception.InvalidCouponNameException;
import lombok.Getter;

@Getter
public class Name {

    private final String value;

    public Name(String value) {
        if (value == null || value.length() > 30) {
            throw new InvalidCouponNameException("쿠폰 이름은 반드시 존재해야 하고 30자 이하여야 합니다.");
        }
        this.value = value;
    }

}
