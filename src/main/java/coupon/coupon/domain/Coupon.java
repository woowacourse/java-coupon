package coupon.coupon.domain;

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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "coupon")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Coupon {

    private static final int COUPON_NAME_MAX_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1_000;
    private static final int MAX_DISCOUNT_AMOUNT = 10_000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_MINIMUM_ORDER_PRICE = 5_000;
    private static final int MAX_MINIMUM_ORDER_PRICE = 100_000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;
    private static final LocalTime ISSUE_START_TIME = LocalTime.MIDNIGHT;
    private static final LocalTime ISSUE_END_TIME = LocalTime.of(23, 59, 59, 999_999_000);
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @Column(name = "discount_amount", nullable = false)
    private BigDecimal discountAmount;

    @Column(name = "minimum_order_price", nullable = false)
    private BigDecimal minimumOrderPrice;

    @Column(name = "coupon_category", columnDefinition = "VARCHAR(50)", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CouponCategory couponCategory;

    @Column(name = "issue_started_at", nullable = false)
    private LocalDateTime issueStartedAt;

    @Column(name = "issue_ended_at", nullable = false)
    private LocalDateTime issueEndedAt;

    public Coupon(String name, BigDecimal discountAmount, BigDecimal minimumOrderPrice, CouponCategory couponCategory,
            LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        validate(name, discountAmount, minimumOrderPrice, issueStartedAt, issueEndedAt);
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderPrice = minimumOrderPrice;
        this.couponCategory = couponCategory;
        this.issueStartedAt = issueStartedAt;
        this.issueEndedAt = issueEndedAt;
    }

    private void validate(String name, BigDecimal discountAmount, BigDecimal minimumOrderPrice,
            LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinimumOrderPrice(minimumOrderPrice);
        validateDiscountRate(discountAmount, minimumOrderPrice);
        validateIssueDate(issueStartedAt, issueEndedAt);
    }

    private void validateName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 반드시 존재해야 합니다.");
        }
        if (name.length() > COUPON_NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("이름의 길이는 최대 %d자 입니다.".formatted(COUPON_NAME_MAX_LENGTH));
        }
    }

    private void validateDiscountAmount(BigDecimal discountAmount) {
        if (discountAmount.compareTo(BigDecimal.valueOf(MIN_DISCOUNT_AMOUNT)) < 0) {
            throw new IllegalArgumentException("할인 금액은 %,d원 이상이어야 합니다.".formatted(MIN_DISCOUNT_AMOUNT));
        }
        if (discountAmount.compareTo(BigDecimal.valueOf(MAX_DISCOUNT_AMOUNT)) > 0) {
            throw new IllegalArgumentException("할인 금액은 %,d원 이하여야 합니다.".formatted(MAX_DISCOUNT_AMOUNT));
        }
        if (discountAmount.remainder(BigDecimal.valueOf(DISCOUNT_AMOUNT_UNIT)).compareTo(BigDecimal.ZERO) != 0) {
            throw new IllegalArgumentException("할인 금액은 %,d원 단위로 설정할 수 있습니다.".formatted(DISCOUNT_AMOUNT_UNIT));
        }
    }

    private void validateMinimumOrderPrice(BigDecimal minimumOrderPrice) {
        if (minimumOrderPrice.compareTo(BigDecimal.valueOf(MIN_MINIMUM_ORDER_PRICE)) < 0) {
            throw new IllegalArgumentException("최소 주문 금액은 %,d원 이상이어야 합니다.".formatted(MIN_MINIMUM_ORDER_PRICE));
        }
        if (minimumOrderPrice.compareTo(BigDecimal.valueOf(MAX_MINIMUM_ORDER_PRICE)) > 0) {
            throw new IllegalArgumentException("최소 주문 금액은 %,d원 이하여야 합니다.".formatted(MAX_MINIMUM_ORDER_PRICE));
        }
    }

    private void validateDiscountRate(BigDecimal discountAmount, BigDecimal minimumOrderPrice) {
        int discountRate = calculateDiscountRate(discountAmount, minimumOrderPrice);
        if (discountRate < MIN_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 %d%% 이상이어야 한다.".formatted(MIN_DISCOUNT_RATE));
        }
        if (discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 %d%% 이하여야 한다.".formatted(MAX_DISCOUNT_RATE));
        }
    }

    private int calculateDiscountRate(BigDecimal discountAmount, BigDecimal minimumOrderPrice) {
        return discountAmount.multiply(BigDecimal.valueOf(100))
                .divide(minimumOrderPrice, 0, RoundingMode.DOWN)
                .intValue();
    }

    private void validateIssueDate(LocalDateTime issueStartedAt, LocalDateTime issueEndedAt) {
        if (issueStartedAt.isAfter(issueEndedAt)) {
            throw new IllegalArgumentException("발급 시작일은 발급 종료일보다 이전이어야 합니다.");
        }
        if (!issueStartedAt.toLocalTime().equals(ISSUE_START_TIME)) {
            throw new IllegalArgumentException("발급 시작일 시각은 %s여야 합니다."
                    .formatted(ISSUE_START_TIME.format(DATE_TIME_FORMATTER)));
        }
        if (!issueEndedAt.toLocalTime().equals(ISSUE_END_TIME)) {
            throw new IllegalArgumentException("발급 종료일 시각은 %s여야 합니다."
                    .formatted(ISSUE_END_TIME.format(DATE_TIME_FORMATTER)));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coupon coupon = (Coupon) o;
        return Objects.equals(id, coupon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
