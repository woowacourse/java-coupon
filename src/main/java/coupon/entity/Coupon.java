package coupon.entity;

import coupon.CouponException;
import lombok.Getter;

@Getter
public class Coupon {

    public static final int MAX_NAME_LENGTH = 30;
    private final String name;

    public Coupon(String name) {
        validateName(name);
        this.name = name;
    }

    private void validateName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new CouponException("name is too long");
        }
    }
}
