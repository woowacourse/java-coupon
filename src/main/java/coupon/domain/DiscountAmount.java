package coupon.domain;

import coupon.exception.CouponException;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class DiscountAmount {

    private int discountAmount;

    public DiscountAmount(int discountAmount, int minimumOrderAmount) {
        validateDiscountAmount(discountAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
        this.discountAmount = discountAmount;
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
