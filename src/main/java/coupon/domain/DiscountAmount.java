package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class DiscountAmount {

    private static final int DISCOUNT_UNIT = 500;
    private static final int DISCOUNT_MIN_AMOUNT = 1_000;
    private static final int DISCOUNT_MAX_AMOUNT = 10_000;
    private static final int DISCOUNT_RATE_MIN_AMOUNT = 3;
    private static final int DISCOUNT_RATE_MAX_AMOUNT = 20;

    private int discountAmount;

    public DiscountAmount(int discountAmount, int minimumOrderAmount) {
        validateDiscountAmount(discountAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmount(int amount) {
        if (amount % DISCOUNT_UNIT != 0) {
            throw new CouponException("할인 금액은 500원 단위여야 합니다.");
        }

        if (amount < DISCOUNT_MIN_AMOUNT || amount > DISCOUNT_MAX_AMOUNT) {
            throw new CouponException("할인 금액은 1000원 이상 10,000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(int amount, int minimumOrderAmount) {
        int discountRate = (int) Math.floor(((double) amount / minimumOrderAmount) * 100);
        if (discountRate < DISCOUNT_RATE_MIN_AMOUNT || discountRate > DISCOUNT_RATE_MAX_AMOUNT) {
            throw new CouponException("할인율은 3% 이상 20% 이하여야 합니다.");
        }
    }
}
