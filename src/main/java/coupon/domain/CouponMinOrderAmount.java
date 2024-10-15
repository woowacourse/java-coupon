package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CouponMinOrderAmount {

    private static final int MIN_ORDER_AMOUNT_SIZE = 5_000;
    private static final int MAX_ORDER_AMOUNT_SIZE = 100_000;

    @Column(name = "min_order_amount", nullable = false)
    private int value;

    public CouponMinOrderAmount(int minOrderAmount) {
        validateOrderAmountSize(minOrderAmount);

        this.value = minOrderAmount;
    }

    private void validateOrderAmountSize(int minOrderAmount) {
        if (minOrderAmount < MIN_ORDER_AMOUNT_SIZE || minOrderAmount > MAX_ORDER_AMOUNT_SIZE) {
            String message = "주문 최소 금액은 %,d원 이상 %,d원 이하만 가능합니다."
                    .formatted(MIN_ORDER_AMOUNT_SIZE, MAX_ORDER_AMOUNT_SIZE);

            throw new IllegalArgumentException(message);
        }
    }
}
