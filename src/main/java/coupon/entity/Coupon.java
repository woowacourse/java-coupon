package coupon.entity;

import coupon.CouponException;
import lombok.Getter;

@Getter
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_MINIMUM_ORDER = 5000;
    private static final int MAX_MINIMUM_ORDER = 100000;
    private static final int MIN_DISCOUNT = 1000;
    private static final int MAX_DISCOUNT = 10000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final int PERCENT_MULTIPLIER = 100;

    private final String name;
    private final int discount;
    private final int minimumOrder;
    private final Category category;

    public Coupon(String name, int discount, int minimumOrder, Category category) {
        validate(name, discount, minimumOrder);
        this.name = name;
        this.discount = discount;
        this.minimumOrder = minimumOrder;
        this.category = category;
    }

    private void validate(String name, int discount, int minimumOrder) {
        validateName(name);
        validateMinimumOrder(minimumOrder);
        validateDiscount(discount, minimumOrder);
    }

    private void validateName(String name) {
        if (name.length() > MAX_NAME_LENGTH) {
            throw new CouponException("name is too long");
        }
    }

    private void validateMinimumOrder(int minimumOrder) {
        if (minimumOrder < MIN_MINIMUM_ORDER || minimumOrder > MAX_MINIMUM_ORDER) {
            throw new CouponException("minimumOrder is out of range");
        }
    }

    private void validateDiscount(int discount, int minimumOrder) {
        if (discount < MIN_DISCOUNT || discount > MAX_DISCOUNT) {
            throw new CouponException("discount is out of range");
        }
        int rate = (discount * PERCENT_MULTIPLIER) / minimumOrder;
        if (rate < MIN_DISCOUNT_RATE || rate > MAX_DISCOUNT_RATE) {
            throw new CouponException("discount rate is out of range");
        }
    }
}
