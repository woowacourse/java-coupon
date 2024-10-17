package coupon.domain;

import coupon.exception.CouponException;
import lombok.Getter;

@Getter
public class MinimumOrderAmount {

    private final int amount;

    public MinimumOrderAmount(int amount) {
        validateMinimumOrderAmount(amount);
        this.amount = amount;
    }

    private void validateMinimumOrderAmount(int amount) {
        if (amount < 5000 || amount > 100000) {
            throw new CouponException("최소 주문 금액은 5,000원 이상, 100,000원 이하여야 합니다.");
        }
    }
}
