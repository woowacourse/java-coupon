package coupon.coupon.domain;

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

    @Column(nullable = false, name = "discount_rate")
    private int value;

    public DiscountRate(int discountAmount, int minOrderPrice) {
        int discountRate = calculateDiscountRate(discountAmount, minOrderPrice);
        validateDiscountRate(discountRate);
        this.value = discountRate;
    }

    private void validateDiscountRate(int discountRate) {
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException(
                    String.format("할인율은 %d%% 이상, %d%% 이하이어야 합니다.", MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE)
            );
        }
    }

    private int calculateDiscountRate(int discountAmount, int minOrderPrice) {
        return (int) (((double) discountAmount / minOrderPrice) * 100);
    }
}
