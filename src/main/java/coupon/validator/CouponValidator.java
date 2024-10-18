package coupon.validator;

import coupon.entity.Coupon;
import coupon.entity.CouponCategory;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class CouponValidator {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_ORDER_AMOUNT = 5_000;
    private static final int MAX_ORDER_AMOUNT = 100_000;

    public void validate(Coupon coupon) {
        validateName(coupon.getName());
        validateAmount(coupon.getDiscountAmount(), coupon.getMinimumOrderAmount());
        validateCategory(coupon.getCategory());
        validateDate(coupon.getValidFrom(), coupon.getValidTo());
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is empty");
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("name is too long");
        }
    }

    private void validateAmount(int discountAmount, int minimumOrderAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT
                || discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("invalid discountAmount");
        }
        if (minimumOrderAmount < MIN_ORDER_AMOUNT || minimumOrderAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException("minimumOrderAmount is negative");
        }
    }

    private void validateCategory(CouponCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("category is null");
        }
    }

    private void validateDate(LocalDateTime validFrom, LocalDateTime validTo) {
        if (validFrom == null) {
            throw new IllegalArgumentException("validFrom is null");
        }
        if (validTo == null) {
            throw new IllegalArgumentException("validTo is null");
        }
        if (validFrom.isAfter(validTo)) {
            throw new IllegalArgumentException("validFrom is after validTo");
        }
    }
}
