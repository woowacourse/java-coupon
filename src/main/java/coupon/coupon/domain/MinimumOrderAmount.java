package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record MinimumOrderAmount(Long minimumOrderAmount) {

    private static final Long MINIMUM_ORDER_AMOUNT = 5_000L;
    private static final Long MAXIMUM_ORDER_AMOUNT = 100_000L;

    public MinimumOrderAmount {
        validateAmount(minimumOrderAmount);
    }

    public void validateAmount(Long minimumOrderAmount) {
        if (minimumOrderAmount < MINIMUM_ORDER_AMOUNT || minimumOrderAmount > MAXIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format("최소 주문 금액은 %,d원 이상 %,d원 이하이어야 합니다.", MINIMUM_ORDER_AMOUNT, MAXIMUM_ORDER_AMOUNT));
        }
    }
}
