package coupon.coupon.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import coupon.coupon.CouponException;

public class Coupon {

    private static final BigDecimal MIN_OF_MINIMUM_ORDER_AMOUNT = BigDecimal.valueOf(5000);
    private static final BigDecimal MAX_OF_MINIMUM_ORDER_AMOUNT = BigDecimal.valueOf(100000);
    private static final BigDecimal MINIMUM_DISCOUNT_RATE = BigDecimal.valueOf(3);
    private static final BigDecimal MAXIMUM_DISCOUNT_RATE = BigDecimal.valueOf(20);

    private final CouponName name;
    private final DiscountAmount discountAmount;
    private final BigDecimal minimumOrderAmount;
    private final Category category;
    private final LocalDate startAt;
    private final LocalDate endAt;

    public Coupon(String name, long discountAmount, long minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        this(new CouponName(name), new DiscountAmount(discountAmount), BigDecimal.valueOf(minimumOrderAmount), category, startAt, endAt);
    }

    public Coupon(CouponName name, DiscountAmount discountAmount, BigDecimal minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        validateMinimumOrderAmount(minimumOrderAmount);
        validateDiscountRate(discountAmount, minimumOrderAmount);
        validateTerm(startAt, endAt);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.startAt = startAt;
        this.endAt = endAt;
    }


    private void validateMinimumOrderAmount(BigDecimal minimumOrderAmount) {
        validateRangeOfMinimumOrderAmount(minimumOrderAmount);
    }

    private void validateRangeOfMinimumOrderAmount(BigDecimal minimumOrderAmount) {
        if (minimumOrderAmount.compareTo(MIN_OF_MINIMUM_ORDER_AMOUNT) < 0 || (minimumOrderAmount.compareTo(MAX_OF_MINIMUM_ORDER_AMOUNT) > 0)) {
            throw new CouponException("최소 주문 금액은 5000원 이상, 100000원 이하이어야 합니다.");
        }
    }

    private void validateDiscountRate(DiscountAmount discountAmount, BigDecimal minimumOrderAmount) {
        BigDecimal discountRate = discountAmount.getDiscountAmount()
                .multiply(BigDecimal.valueOf(100))
                .divide(minimumOrderAmount, 0, RoundingMode.DOWN);
        if (discountRate.compareTo(MINIMUM_DISCOUNT_RATE) < 0 || discountRate.compareTo(MAXIMUM_DISCOUNT_RATE) > 0) {
            throw new CouponException("할인율은 3% 이상, 20% 이하이어야 합니다.");
        }
    }

    private void validateTerm(LocalDate startAt, LocalDate endAt) {
        if (endAt.isBefore(startAt)) {
            throw new CouponException("종료일이 시작일보다 앞설 수 없습니다.");
        }
    }
}
