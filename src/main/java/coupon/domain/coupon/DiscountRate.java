package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DiscountRate {

    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Column(nullable = false)
    private int discountRate;

    private DiscountRate(int discountRate) {
        this.discountRate = discountRate;
    }

    public static DiscountRate calculateDiscountRate(int discountAmount, int baseAmount) {
        ValidateBaseAmount(baseAmount);
        int discountRate = (int) Math.floor((double) discountAmount / baseAmount * 100);
        validateDiscountRate(discountRate);
        return new DiscountRate(discountRate);
    }

    private static void ValidateBaseAmount(int baseAmount) {
        if (baseAmount == 0) {
            throw new IllegalArgumentException("기준 금액은 0원 이상이어야 합니다.");
        }
    }

    private static void validateDiscountRate(int discountRate) {
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 %d%% 이상 %d%% 이하여야 합니다."
                    .formatted(MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE));
        }
    }
}
