package coupon.domain.discountpolicy;

import coupon.domain.DiscountPolicy;

public class DiscountMoneyPolicy implements DiscountPolicy {
    private static final int MINIMUM_DISCOUNT_AMOUNT_MONEY = 1000;
    private static final int MAXIMUM_DISCOUNT_AMOUNT_MONEY = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;

    private final int discountAmount;

    public DiscountMoneyPolicy(int discountAmount) {
        validateDiscountAmountValue(discountAmount);
        validateDiscountAmountUnit(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmountValue(int discountAmount) {
        if (discountAmount < MINIMUM_DISCOUNT_AMOUNT_MONEY) {
            throw new IllegalArgumentException("할인 금액이 최소인 [%d] 금액보다 적습니다.".formatted(MINIMUM_DISCOUNT_AMOUNT_MONEY));
        }

        if (discountAmount > MAXIMUM_DISCOUNT_AMOUNT_MONEY) {
            throw new IllegalArgumentException("할인 금액이 최대인 [%d] 금액보다 많습니다.".formatted(MAXIMUM_DISCOUNT_AMOUNT_MONEY));
        }
    }

    private void validateDiscountAmountUnit(int discountAmount) {
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액의 단위는 [%d] 로 설정해야 합니다.".formatted(DISCOUNT_AMOUNT_UNIT));
        }
    }

    @Override
    public int apply(int money) {
        if (money - discountAmount < 0) {
            return 0;
        }
        return money - discountAmount;
    }
}
