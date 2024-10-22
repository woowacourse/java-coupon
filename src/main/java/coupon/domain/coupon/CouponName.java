package coupon.domain.coupon;

import coupon.common.ErrorConstant;
import coupon.common.exception.CouponException;
import lombok.Getter;

@Getter
public class CouponName {

    public static final int MAX_COUPON_NAME_LENGTH = 30;

    private final String name;

    public CouponName(String name) {
        validate(name);
        this.name = name;
    }

    private void validate(String name) {
        if (name == null || name.isBlank()) {
            throw new CouponException(ErrorConstant.COUPON_NAME_IS_NULL_OR_EMPTY);
        }

        int nameLength = name.length();
        if (MAX_COUPON_NAME_LENGTH < nameLength) {
            throw new CouponException(ErrorConstant.COUPON_NAME_IS_NOT_IN_RANGE);
        }
    }
}
