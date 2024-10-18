package coupon.coupon.domain;

import coupon.BaseEntity;
import coupon.coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor
public class Coupon extends BaseEntity {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_NAME_LENGTH = 1;
    private static final BigDecimal MIN_DISCOUNT_AMOUNT = BigDecimal.valueOf(1000);
    private static final BigDecimal MAX_DISCOUNT_AMOUNT = BigDecimal.valueOf(10000);
    private static final BigDecimal DISCOUNT_UNIT = BigDecimal.valueOf(500);
    private static final double MIN_DISCOUNT_RATIO = 0.03;
    private static final double MAX_DISCOUNT_RATIO = 0.2;
    private static final BigDecimal MIN_ORDER_AMOUNT = BigDecimal.valueOf(5000);
    private static final BigDecimal MAX_ORDER_AMOUNT = BigDecimal.valueOf(100000);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = MAX_NAME_LENGTH)
    private String name;

    @Column(name = "discount_amount")
    private BigDecimal discountAmount;

    @Column(name = "minimum_order_amount")
    private BigDecimal minimumOrderAmount;

    @Column(name = "category")
    @Enumerated(value = EnumType.STRING)
    private Category category;

    @Column(name = "issue_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issuedAt;

    @Column(name = "expired_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime expiredAt;

    public Coupon(
            final Long id,
            final String name,
            final BigDecimal discountAmount,
            final BigDecimal minimumOrderAmount,
            final Category category,
            final LocalDateTime issuedAt,
            final LocalDateTime expiredAt
    ) {
        validate(name, discountAmount, minimumOrderAmount, category, issuedAt, expiredAt);
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public Coupon(
            final String name,
            final int discountAmount,
            final int minimumOrderAmount,
            final Category category,
            final LocalDateTime issuedAt,
            final LocalDateTime expiredAt
    ) {
        this(
                null,
                name,
                BigDecimal.valueOf(discountAmount),
                BigDecimal.valueOf(minimumOrderAmount),
                category,
                issuedAt,
                expiredAt
        );
    }

    private void validate(
            final String name,
            final BigDecimal discountAmount,
            final BigDecimal minimumOrderAmount,
            final Category category,
            final LocalDateTime issuedAt,
            final LocalDateTime expiredAt
    ) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateDiscountRatio(discountAmount, minimumOrderAmount);
        validateMinimumOrderAmount(minimumOrderAmount);
        validateExpirationPeriod(issuedAt, expiredAt);
    }

    private void validateName(final String name) {
        validateNameNotBlank(name);
        validateNameLength(name);
    }

    private void validateNameNotBlank(final String name) {
        if (name == null || name.isBlank()) {
            throw new CouponException("쿠폰의 이름은 비어있을 수 없습니다");
        }
    }

    private void validateNameLength(final String name) {
        final var nameLength = name.length();
        if (nameLength < MIN_NAME_LENGTH || nameLength > MAX_NAME_LENGTH) {
            throw new CouponException(
                    "쿠폰 이름의 길이는 " + MIN_NAME_LENGTH + "자 이상, " + MAX_NAME_LENGTH + "자 이하여야 합니다: " + nameLength
            );
        }
    }

    private void validateDiscountAmount(final BigDecimal discountAmount) {
        validateDiscountAmountRange(discountAmount);
        validateDiscountAmountUnit(discountAmount);
    }

    private void validateDiscountAmountRange(final BigDecimal discountAmount) {
        final var isDiscountAmountLowerThanMinimumAmount = discountAmount.compareTo(MIN_DISCOUNT_AMOUNT) < 0;
        final var isDiscountAmountGreaterThanMaximumAmount = discountAmount.compareTo(MAX_DISCOUNT_AMOUNT) > 0;
        if (isDiscountAmountLowerThanMinimumAmount || isDiscountAmountGreaterThanMaximumAmount) {
            throw new CouponException("쿠폰 할인 금액은 " + MIN_DISCOUNT_AMOUNT + "이상, " + MAX_DISCOUNT_AMOUNT + "이하여야 합니다.");
        }
    }

    private void validateDiscountAmountUnit(final BigDecimal discountAmount) {
        final var remainder = discountAmount.remainder(DISCOUNT_UNIT);
        if (!remainder.equals(BigDecimal.ZERO)) {
            throw new CouponException("쿠폰 할인 금액은 " + DISCOUNT_UNIT + "으로 나누어 떨어져야 합니다.");
        }
    }

    private void validateDiscountRatio(final BigDecimal discountAmount, final BigDecimal minimumOrderAmount) {
        final var discountRatio = discountAmount.divide(minimumOrderAmount, 2, RoundingMode.HALF_UP).doubleValue();
        if (MIN_DISCOUNT_RATIO > discountRatio || discountRatio > MAX_DISCOUNT_RATIO) {
            throw new CouponException(
                    "쿠폰 할인율은 " + MIN_DISCOUNT_RATIO + " 이상, " + MAX_DISCOUNT_RATIO + "이하여야 합니다: " + discountRatio
            );
        }
    }

    private void validateMinimumOrderAmount(final BigDecimal minimumOrderAmount) {
        final var isMinimumOrderAmountLowerThanMinimumAmount = minimumOrderAmount.compareTo(MIN_ORDER_AMOUNT) < 0;
        final var isMaximumOrderAmountGreaterThanMaximumAmount = minimumOrderAmount.compareTo(MAX_ORDER_AMOUNT) > 0;
        if (isMinimumOrderAmountLowerThanMinimumAmount || isMaximumOrderAmountGreaterThanMaximumAmount) {
            throw new CouponException(
                    "최소 주문 금액은 " + MIN_ORDER_AMOUNT + " 이상, " + MAX_ORDER_AMOUNT + " 이하여야 합니다: " + minimumOrderAmount
            );
        }
    }

    private void validateExpirationPeriod(LocalDateTime issuedAt, LocalDateTime expiredAt) {
        if (issuedAt.isAfter(expiredAt)) {
            throw new CouponException("쿠폰 발급 시간은 만료 시간보다 과거일 수 없습니다");
        }
    }
}
