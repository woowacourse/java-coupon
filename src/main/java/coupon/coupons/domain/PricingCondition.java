package coupon.coupons.domain;

import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PricingCondition {

    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_STEP = 500;

    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    private static final int MIN_ORDER_AMOUNT = 5000;
    private static final int MAX_ORDER_AMOUNT = 100000;

    @Column(nullable = false)
    private Integer discountAmount;

    @Column(nullable = false)
    private Integer minOrderAmount;

    public PricingCondition(Integer discountAmount, Integer minOrderAmount) {
        validateDiscount(discountAmount);
        validateMinOrderAmount(minOrderAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
    }

    private void validateIsNull(Object value, String exceptionMessage) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }

    private void validateDiscount(Integer discountAmount) {
        validateIsNull(discountAmount, "할인 금액은 반드시 존재해야 합니다.");
        if (discountAmount < MIN_DISCOUNT_AMOUNT || discountAmount > MAX_DISCOUNT_AMOUNT) {
            throw new IllegalArgumentException("할인 금액은 1,000원 이상 10,000원 이하여야 합니다.");
        }
        if (discountAmount % DISCOUNT_STEP != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }

    private void validateMinOrderAmount(Integer minOrderAmount) {
        validateIsNull(minOrderAmount, "최소 주문 금액은 반드시 존재해야 합니다.");
        if (minOrderAmount < MIN_ORDER_AMOUNT || minOrderAmount > MAX_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상 100,000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(Integer discountAmount, Integer minOrderAmount) {
        int calculatedDiscountRate = (int) Math.floor(((double) discountAmount / minOrderAmount) * 100);
        if (calculatedDiscountRate < MIN_DISCOUNT_RATE || calculatedDiscountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 3% 이상 20% 이하여야 합니다.");
        }
    }
}
