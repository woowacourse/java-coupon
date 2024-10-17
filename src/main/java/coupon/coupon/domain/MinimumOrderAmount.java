package coupon.coupon.domain;

import jakarta.persistence.Embeddable;

@Embeddable
public record MinimumOrderAmount(int amount) {

    private static final int MINIMUM_ORDER_AMOUNT = 5_000;
    private static final int MAXIMUM_ORDER_AMOUNT = 100_000;

    public MinimumOrderAmount {
        validateAmount(amount);
    }

    public void validateAmount(int amount) {
        if (amount < MINIMUM_ORDER_AMOUNT || amount > MAXIMUM_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 5,000원 이상 10,000원 이하이어야 합니다.");
        }
    }
}
