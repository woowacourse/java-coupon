package coupon;

import java.math.BigDecimal;
import java.math.RoundingMode;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class CouponDiscountApply {

    private static final BigDecimal PERCENT_MULTIPLIER = new BigDecimal(100);
    private static final BigDecimal MAXIMUM_DISCOUNT_PERCENT = new BigDecimal("20");
    private static final BigDecimal MINIMUM_DISCOUNT_PERCENT = new BigDecimal("3");

    @Embedded
    private CouponDiscountAmount discountAmount;

    @Embedded
    private CouponApplicableAmount applicableAmount;

    public CouponDiscountApply(String discountAmount, String applicableAmount) {
        this(new BigDecimal(discountAmount), new BigDecimal(applicableAmount));
    }

    public CouponDiscountApply(BigDecimal discountAmount, BigDecimal applicableAmount) {
        validateDiscountPercent(discountAmount, applicableAmount);

        this.discountAmount = new CouponDiscountAmount(discountAmount);
        this.applicableAmount = new CouponApplicableAmount(applicableAmount);
    }

    private void validateDiscountPercent(BigDecimal discountAmount, BigDecimal applicableAmount) {
        BigDecimal discountPercent = calculateDiscountPercent(discountAmount, applicableAmount);

        validateMinimumPercent(discountPercent);
        validateMaximumPercent(discountPercent);
    }

    private BigDecimal calculateDiscountPercent(BigDecimal discountAmount, BigDecimal applicableAmount) {
        return discountAmount
                .divide(applicableAmount, 3, RoundingMode.FLOOR)
                .multiply(PERCENT_MULTIPLIER);
    }

    private void validateMinimumPercent(BigDecimal amount) {
        if (amount.compareTo(MINIMUM_DISCOUNT_PERCENT) < 0) {
            String message = "할인율은 " + MINIMUM_DISCOUNT_PERCENT + "% 이상이어야 합니다.";
            throw new IllegalArgumentException(message);
        }
    }

    private void validateMaximumPercent(BigDecimal amount) {
        if (amount.compareTo(MAXIMUM_DISCOUNT_PERCENT) > 0) {
            String message = "할인율은 " + MAXIMUM_DISCOUNT_PERCENT + "% 이하여야 합니다.";
            throw new IllegalArgumentException(message);
        }
    }
}
