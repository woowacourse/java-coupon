package coupon.coupon.domain;

import lombok.Getter;

@Getter
public class DiscountMoney {

    private static final Long MINIMUM_DISCOUNT_AMOUNT_MONEY = 1000L;
    private static final Long MAXIMUM_DISCOUNT_AMOUNT_MONEY = 10000L;
    private static final Long DISCOUNT_AMOUNT_UNIT = 500L;

    private final Long discountAmount;

    public DiscountMoney(Long discountAmount) {
        validateDiscountAmountValue(discountAmount);
        validateDiscountAmountUnit(discountAmount);
        this.discountAmount = discountAmount;
    }

    private void validateDiscountAmountValue(Long discountAmount) {
        if (discountAmount < MINIMUM_DISCOUNT_AMOUNT_MONEY) {
            throw new IllegalArgumentException("할인 금액이 최소인 [%d] 금액보다 적습니다.".formatted(MINIMUM_DISCOUNT_AMOUNT_MONEY));
        }

        if (discountAmount > MAXIMUM_DISCOUNT_AMOUNT_MONEY) {
            throw new IllegalArgumentException("할인 금액이 최대인 [%d] 금액보다 많습니다.".formatted(MAXIMUM_DISCOUNT_AMOUNT_MONEY));
        }
    }

    private void validateDiscountAmountUnit(Long discountAmount) {
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("할인 금액의 단위는 [%d] 로 설정해야 합니다.".formatted(DISCOUNT_AMOUNT_UNIT));
        }
    }
}
