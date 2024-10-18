package coupon.coupon.service;

import org.springframework.stereotype.Component;

@Component
public class CouponDiscountAmountValidator {

    public static final int MIN_AMOUNT = 1000;
    public static final int MAX_AMOUNT = 10_000;
    private static final int AMOUNT_UNIT = 500;
    private static final int MIN_RATE = 3;
    private static final int MAX_RATE = 20;

    public void validate(int amount, int minOrderAmount) {
        validateDiscountAmountRange(amount);
        validateDiscountAmountUnit(amount);
        validateRateRange(amount, minOrderAmount);
    }

    private void validateDiscountAmountRange(int amount) {
        if (amount < MIN_AMOUNT || amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("쿠폰의 할인 금액은 %d원 이상 %d원 이하여야 해요.".formatted(MIN_AMOUNT, MAX_AMOUNT));
        }
    }

    private void validateDiscountAmountUnit(int amount) {
        if (amount % AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("쿠폰의 할인 금액은 %d원 단위어야 해요.".formatted(AMOUNT_UNIT));
        }
    }

    private void validateRateRange(int amount, int couponMinOrderAmount) {
        double rate = (double) amount / couponMinOrderAmount * 100;
        int realRate = (int) Math.floor(rate);
        if (realRate < MIN_RATE || realRate > MAX_RATE) {
            throw new IllegalArgumentException("쿠폰의 할인율은 %d%% 이상 %d%% 이하여야 해요.".formatted(MIN_RATE, MAX_RATE));
        }
    }
}
