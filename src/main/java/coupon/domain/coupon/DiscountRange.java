package coupon.domain.coupon;

import lombok.Getter;

@Getter
public class DiscountRange {

    private static final int MIN_LIMIT = 3;
    private static final int MAX_LIMIT = 20;

    private final int discountRange;

    public DiscountRange(long discountAmount, long minOrderAmount) {
        int discountRange = calculateDiscountRange(discountAmount, minOrderAmount);
        validateDiscountRange(discountRange);
        this.discountRange = discountRange;
    }

    private static int calculateDiscountRange(long discountAmount, long minOrderAmount) {
        return (int) ((discountAmount / (double) minOrderAmount) * 100);
    }

    private static void validateDiscountRange(int discountRange) {
        if (discountRange < MIN_LIMIT || discountRange > MAX_LIMIT) {
            throw new IllegalArgumentException(String.format("정해진 할인율 범위를 벗어났습니다. - 범위: %d%% ~%d%%", MIN_LIMIT, MAX_LIMIT));
        }
    }
}
