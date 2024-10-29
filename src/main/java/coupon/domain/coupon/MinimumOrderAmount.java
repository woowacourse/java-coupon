package coupon.domain.coupon;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MinimumOrderAmount {

    private static final int MIN_AMOUNT = 5000;
    private static final int MAX_AMOUNT = 100000;

    @Column(nullable = false)
    private int minimumOrderAmount;

    public MinimumOrderAmount(int minimumOrderAmount) {
        validateMinimumOrderAmount(minimumOrderAmount);
        this.minimumOrderAmount = minimumOrderAmount;
    }

    private void validateMinimumOrderAmount(int amount) {
        if (amount < MIN_AMOUNT) {
            throw new CouponException("최소 주문 금액은 %d원 이상이어야 합니다.".formatted(MIN_AMOUNT));
        }
        if (amount > MAX_AMOUNT) {
            throw new CouponException("최소 주문 금액은 %d원 이하여야 합니다.".formatted(MAX_AMOUNT));
        }
    }
}
