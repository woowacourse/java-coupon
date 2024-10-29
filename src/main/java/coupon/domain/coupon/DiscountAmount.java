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
public class DiscountAmount {

    private static final int MIN_AMOUNT_CONSTRAINT = 1000;
    private static final int MAX_AMOUNT_CONSTRAINT = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_RATE_CONSTRAINT = 3;
    private static final int MAX_RATE_CONSTRAINT = 20;

    @Column(nullable = false)
    private int discountAmount;

    public DiscountAmount(int discountAmount, MinimumOrderAmount minimumOrderAmount) {
        validateDiscountConstraint(discountAmount, minimumOrderAmount);
        this.discountAmount = discountAmount;
    }

    private void validateDiscountConstraint(int discountAmount, MinimumOrderAmount minimumOrderAmount) {
        validateAmount(discountAmount);
        validateRate(discountAmount, minimumOrderAmount);
    }

    private void validateAmount(int discountAmount) {
        if (discountAmount < MIN_AMOUNT_CONSTRAINT || discountAmount > MAX_AMOUNT_CONSTRAINT) {
            throw new CouponException("할인 금액은 %d원 이상 %d원 이하여야 합니다.".formatted(
                    MIN_AMOUNT_CONSTRAINT, MAX_AMOUNT_CONSTRAINT
            ));
        }
        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new CouponException("할인 금액은 %d원 단위로 입력해야 합니다.".formatted(MIN_RATE_CONSTRAINT));
        }
    }

    private void validateRate(int discountAmount, MinimumOrderAmount minimumOrderAmount) {
        int discountRate = discountRate(discountAmount, minimumOrderAmount);
        if (discountRate < MIN_RATE_CONSTRAINT || discountRate > MAX_RATE_CONSTRAINT) {
            throw new CouponException("할인율은 %d%% 이상 %d%% 이하여야 합니다.".formatted(
                    MIN_RATE_CONSTRAINT, MAX_RATE_CONSTRAINT
            ));
        }
    }

    private int discountRate(int discountAmount, MinimumOrderAmount minimumOrderAmount) {
        return discountAmount * 100 / minimumOrderAmount.getMinimumOrderAmount();
    }
}
