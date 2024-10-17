package coupon.coupon.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Objects;
import coupon.coupon.CouponException;

public class Coupon {

    private static final int MAX_LENGTH = 30;
    private static final BigDecimal UNIT = BigDecimal.valueOf(500);
    private static final BigDecimal MINIMUM_DISCOUNT_AMOUNT = BigDecimal.valueOf(1000);
    private static final BigDecimal MAXIMUM_DISCOUNT_AMOUNT = BigDecimal.valueOf(10000);
    private static final BigDecimal MIN_OF_MINIMUM_ORDER_AMOUNT = BigDecimal.valueOf(5000);
    private static final BigDecimal MAX_OF_MINIMUM_ORDER_AMOUNT = BigDecimal.valueOf(100000);
    private static final BigDecimal MINIMUM_DISCOUNT_RATE = BigDecimal.valueOf(3);
    private static final BigDecimal MAXIMUM_DISCOUNT_RATE = BigDecimal.valueOf(20);

    private final String name;
    private final BigDecimal discountAmount;
    private final BigDecimal minimumOrderAmount;
    private final Category category;
    private final LocalDate startAt;
    private final LocalDate endAt;

    public Coupon(String name, long discountAmount, long minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        this(name, BigDecimal.valueOf(discountAmount), BigDecimal.valueOf(minimumOrderAmount), category, startAt, endAt);
    }

    public Coupon(String name, BigDecimal discountAmount, BigDecimal minimumOrderAmount, Category category, LocalDate startAt, LocalDate endAt) {
        validateName(name);
        validateDiscountAmount(discountAmount);
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

    private void validateName(String name) {
        validateNull(name);
        validateNameLength(name);
    }

    private void validateNull(String name) {
        if (Objects.isNull(name)) {
            throw new CouponException("쿠폰 이름이 누락되었습니다.");
        }
    }

    private void validateNameLength(String name) {
        if (name.isEmpty() || name.length() > MAX_LENGTH) {
            throw new CouponException("쿠폰은 30자 이하의 이름을 설정해주세요.");
        }
    }

    private void validateDiscountAmount(BigDecimal discountAmount) {
        validateUnit(discountAmount);
        validateRangeOfDiscountAmount(discountAmount);
    }

    private void validateUnit(BigDecimal discountAmount) {
        if (!discountAmount.remainder(UNIT).equals(BigDecimal.ZERO)) {
            throw new CouponException("할인 금액은 500원 단위로 설정할 수 있습니다.");
        }
    }

    private void validateRangeOfDiscountAmount(BigDecimal discountAmount) {
        if (discountAmount.compareTo(MINIMUM_DISCOUNT_AMOUNT) < 0 || discountAmount.compareTo(MAXIMUM_DISCOUNT_AMOUNT) > 0) {
            throw new CouponException("할인 금액은 1000원 이상, 10000원 이하이어야 합니다.");
        }
    }

    private void validateMinimumOrderAmount(BigDecimal minimumOrderAmount) {
        validateRangeOfMinimumOrderAmount(minimumOrderAmount);
    }

    private void validateRangeOfMinimumOrderAmount(BigDecimal minimumOrderAmount) {
        if (minimumOrderAmount.compareTo(MIN_OF_MINIMUM_ORDER_AMOUNT) < 0 || (minimumOrderAmount.compareTo(MAX_OF_MINIMUM_ORDER_AMOUNT) > 0)) {
            throw new CouponException("최소 주문 금액은 5000원 이상, 100000원 이하이어야 합니다.");
        }
    }

    private void validateDiscountRate(BigDecimal discountAmount, BigDecimal minimumOrderAmount) {
        BigDecimal discountRate = discountAmount
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
