package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record MinimumOrderAmount(Long minimumOrderAmount) {

    private static final Long MINIMUM_ORDER_AMOUNT = 5_000L;
    private static final Long MAXIMUM_ORDER_AMOUNT = 100_000L;

    public MinimumOrderAmount {
        validateMinimumOrderAmountNull(minimumOrderAmount);
        validateAmount(minimumOrderAmount);
    }

    private void validateMinimumOrderAmountNull(final Long minimumOrderAmount) {
        if (minimumOrderAmount == null) {
            throw new IllegalArgumentException("최소 주문 금액은 null이 될 수 없습니다.");
        }
    }

    private void validateAmount(final Long minimumOrderAmount) {
        if (minimumOrderAmount < MINIMUM_ORDER_AMOUNT || minimumOrderAmount > MAXIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException(String.format("최소 주문 금액은 %,d원 이상 %,d원 이하이어야 합니다.", MINIMUM_ORDER_AMOUNT, MAXIMUM_ORDER_AMOUNT));
        }
    }
}
