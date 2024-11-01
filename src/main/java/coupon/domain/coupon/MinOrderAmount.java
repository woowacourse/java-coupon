package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinOrderAmount {

    private static final int MIN_MIN_ORDER_AMOUNT = 5_000;
    private static final int MAX_MIN_ORDER_AMOUNT = 100_000;

    @Column(nullable = false)
    private int minOrderAmount;

    protected MinOrderAmount(int minOrderAmount) {
        validateMinOrderAmount(minOrderAmount);
        this.minOrderAmount = minOrderAmount;
    }

    private void validateMinOrderAmount(int minOrderAmount) {
        if (minOrderAmount < MIN_MIN_ORDER_AMOUNT || minOrderAmount > MAX_MIN_ORDER_AMOUNT) {
            throw new IllegalArgumentException("최소 주문 금액은 %d원 이상 %d원 이하여야 합니다."
                    .formatted(MIN_MIN_ORDER_AMOUNT, MAX_MIN_ORDER_AMOUNT));
        }
    }
}
