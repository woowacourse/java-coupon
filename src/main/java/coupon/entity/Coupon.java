package coupon.entity;

import coupon.CouponException;
import lombok.Getter;

@Getter
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT = 1000;
    private static final int MAX_DISCOUNT = 10000;

    private final String name;
    private final int discount;

    public Coupon(String name, int discount) {
        validate(name, discount);
        this.name = name;
        this.discount = discount;
    }

    private void validate(String name, int discount) {
        validateName(name);
        validateDiscount(discount);
    }

    private void validateName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new CouponException("name is too long");
        }
    }

    private void validateDiscount(int discount) {
        if (discount < MIN_DISCOUNT || discount > MAX_DISCOUNT) {
            throw new CouponException("discount is out of range");
        }
    }
}
