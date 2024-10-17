package coupon.domain;

import coupon.exception.CouponException;
import lombok.Getter;

@Getter
public class DiscountAmount {

    private final int amount;

    public DiscountAmount(int amount, int minimumOrderAmount) {
        validateDiscountAmount(amount);
        validateDiscountRate(amount, minimumOrderAmount);
        this.amount = amount;
    }

    private void validateDiscountAmount(int amount) {
        if (amount % 500 != 0) {
            throw new CouponException("할인 금액은 500원 단위여야 합니다.");
        }

        if (amount < 1000 || amount > 10_000) {
            throw new CouponException("할인 금액은 1000원 이상 10,000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(int amount, int minimumOrderAmount) {
        int discountRate = (int) Math.floor(((double) amount / minimumOrderAmount) * 100);
        if (discountRate < 3 || discountRate > 20) {
            throw new CouponException("할인율은 3% 이상 20% 이하여야 합니다.");
        }
    }
}
