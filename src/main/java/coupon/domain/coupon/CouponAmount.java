package coupon.domain.coupon;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CouponAmount {

    @Min(1_000)
    @Max(10_000)
    private Integer discountAmount;

    @Min(5_000)
    @Max(100_000)
    private Integer minOrderAmount;

    public CouponAmount(Integer discountAmount, Integer minOrderAmount) {
        validateDiscountAmount(discountAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
    }

    private void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("할인 금액은 500원 단위로 설정해야 합니다: %d".formatted(discountAmount));
        }
    }

    private void validateDiscountRate(Integer discountAmount, Integer minOrderAmount) {
        int discountRate = discountAmount * 100 / minOrderAmount;
        if (discountRate < 3) {
            throw new IllegalArgumentException("할인율은 3%% 이상이어야 합니다: %d".formatted(discountRate));
        }
        if (discountRate > 20) {
            throw new IllegalArgumentException("할인율은 20%% 이하이어야 합니다: %d".formatted(discountRate));
        }
    }
}
