package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponDiscountAmount {

    public static final int MIN_AMOUNT = 1000;
    public static final int MAX_AMOUNT = 10_000;
    private static final int AMOUNT_UNIT = 500;
    private static final int MIN_RATE = 3;
    private static final int MAX_RATE = 20;

    @Column(name = "dicount_amount", nullable = false)
    private int amount;

    public CouponDiscountAmount(int amount, int minOrderAmount) {
        discountAmountRange(amount);
        discountAmountUnit(amount);
        validateRateRange(amount, minOrderAmount);
        this.amount = amount;
    }

    private void discountAmountRange(int amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("쿠폰의 할인 금액은 %d원 이상 %d원 이하여야 해요.".formatted(MIN_AMOUNT, MAX_AMOUNT));
        }
    }

    private void discountAmountUnit(int amount) {
        if (amount % AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("쿠폰의 할인 금액은 %d원 단위어야 해요.".formatted(AMOUNT_UNIT));
        }
    }

    private void validateRateRange(int amount, int couponMinOrderAmount) {
        int rate = amount / couponMinOrderAmount;
        if (rate < MIN_RATE || rate > MAX_RATE) {
            throw new IllegalArgumentException("쿠폰의 할인율은 %d%% 이상 %d%% 이하여야 해요.".formatted(MIN_RATE, MAX_RATE));
        }
    }
}
