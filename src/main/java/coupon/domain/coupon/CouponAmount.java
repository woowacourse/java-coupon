package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponAmount {

    private static final int DISCOUNT_AMOUNT_MIN = 1_000;
    private static final int DISCOUNT_AMOUNT_MAX = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_ORDER_AMOUNT_MIN = 5_000;
    private static final int MIN_ORDER_AMOUNT_MAX = 100_000;
    private static final int DISCOUNT_RATE_MIN = 3;
    private static final int DISCOUNT_RATE_MAX = 20;

    private int discountAmount;
    private int minOrderAmount;

    public CouponAmount(Integer discountAmount, Integer minOrderAmount) {
        validate(discountAmount, minOrderAmount);
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
    }

    private void validate(Integer discountAmount, Integer minOrderAmount) {
        validateIsNull(discountAmount, minOrderAmount);
        validateDiscountAmountRange(discountAmount);
        validateMinOrderAmountRange(minOrderAmount);
        validateDiscountAmountUnit(discountAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
    }

    private void validateIsNull(Integer discountAmount, Integer minOrderAmount) {
        if (discountAmount == null) {
            throw new IllegalArgumentException("할인 금액은 null일 수 없습니다.");
        }
        if (minOrderAmount == null) {
            throw new IllegalArgumentException("최소 주문 금액은 null일 수 없습니다.");
        }
    }

    private void validateDiscountAmountRange(Integer discountAmount) {
        if (discountAmount < DISCOUNT_AMOUNT_MIN) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상이어야 합니다: %d".formatted(discountAmount));
        }
        if (discountAmount > DISCOUNT_AMOUNT_MAX) {
            throw new IllegalArgumentException("할인 금액은 10,000원 이하이어야 합니다: %d".formatted(discountAmount));
        }
    }

    private void validateMinOrderAmountRange(Integer minOrderAmount) {
        if (minOrderAmount < MIN_ORDER_AMOUNT_MIN) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상이어야 합니다: %d".formatted(minOrderAmount));
        }
        if (minOrderAmount > MIN_ORDER_AMOUNT_MAX) {
            throw new IllegalArgumentException("최소 주문 금액은 100,000원 이하이어야 합니다: %d".formatted(minOrderAmount));
        }
    }

    private void validateDiscountAmountUnit(Integer discountAmount) {
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정해야 합니다: %d".formatted(discountAmount));
        }
    }

    private void validateDiscountRate(Integer discountAmount, Integer minOrderAmount) {
        int discountRate = discountAmount * 100 / minOrderAmount;
        if (discountRate < DISCOUNT_RATE_MIN) {
            throw new IllegalArgumentException("할인율은 3%% 이상이어야 합니다: %d".formatted(discountRate));
        }
        if (discountRate > DISCOUNT_RATE_MAX) {
            throw new IllegalArgumentException("할인율은 20%% 이하이어야 합니다: %d".formatted(discountRate));
        }
    }
}
