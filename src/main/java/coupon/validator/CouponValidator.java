package coupon.validator;

import coupon.entity.Coupon;
import coupon.entity.CouponCategory;
import coupon.exception.CouponErrorMessage;
import coupon.exception.CouponException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponValidator {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_ORDER_AMOUNT = 5_000;
    private static final int MAX_ORDER_AMOUNT = 100_000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    public void validateCanIssue(Coupon coupon) {
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(coupon.getValidFrom()) || now.isAfter(coupon.getValidTo())) {
            throw new CouponException(CouponErrorMessage.COUPON_NOT_AVAILABLE);
        }
    }

    public void validate(Coupon coupon) {
        validateName(coupon.getName());
        validateAmount(coupon.getDiscountAmount(), coupon.getMinimumOrderAmount());
        validateCategory(coupon.getCategory());
        validateDate(coupon.getValidFrom(), coupon.getValidTo());
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new CouponException(CouponErrorMessage.COUPON_NAME_EMPTY);
        }
        if (name.length() > MAX_NAME_LENGTH) {
            throw new CouponException(CouponErrorMessage.COUPON_NAME_TOO_LONG);
        }
    }

    private void validateAmount(int discountAmount, int minimumOrderAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT
                || discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new CouponException(CouponErrorMessage.COUPON_INVALID_DISCOUNT_AMOUNT);
        }
        if (minimumOrderAmount < MIN_ORDER_AMOUNT || minimumOrderAmount > MAX_ORDER_AMOUNT) {
            throw new CouponException(CouponErrorMessage.COUPON_INVALID_MIN_ORDER_AMOUNT);
        }
        validateDiscountRate(discountAmount, minimumOrderAmount);
    }

    private void validateDiscountRate(int discountAmount, int minimumOrderAmount) {
        int discountRate = discountAmount * 100 / minimumOrderAmount;
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new CouponException(CouponErrorMessage.COUPON_INVALID_DISCOUNT_RATE);
        }
    }

    private void validateCategory(CouponCategory category) {
        if (category == null) {
            throw new CouponException(CouponErrorMessage.COUPON_CATEGORY_NULL);
        }
    }

    private void validateDate(LocalDateTime validFrom, LocalDateTime validTo) {
        if (validFrom == null) {
            throw new CouponException(CouponErrorMessage.COUPON_VALID_FROM_NULL);
        }
        if (validTo == null) {
            throw new CouponException(CouponErrorMessage.COUPON_VALID_TO_NULL);
        }
        if (validFrom.isAfter(validTo)) {
            throw new CouponException(CouponErrorMessage.COUPON_VALID_FROM_AFTER_VALID_TO);
        }
    }
}
