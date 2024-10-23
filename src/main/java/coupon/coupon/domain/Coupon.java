package coupon.coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Coupon {

    private static final int MAX_NAME_LENGTH = 30;
    private static final int MIN_DISCOUNT_AMOUNT = 1000;
    private static final int MAX_DISCOUNT_AMOUNT = 10000;
    private static final int DISCOUNT_AMOUNT_UNIT = 500;
    private static final int MIN_ORDER_AMOUNT = 5000;
    private static final int MAX_ORDER_AMOUNT = 100000;
    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = MAX_NAME_LENGTH, nullable = false)
    private String name;

    @Column(nullable = false)
    private CouponCategory category;

    @Column(nullable = false)
    private int discountAmount;

    @Column(nullable = false)
    private int minOrderAmount;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime endedAt;

    private Coupon(
            Long id,
            String name,
            CouponCategory category,
            int discountAmount,
            int minOrderAmount,
            LocalDateTime startedAt,
            LocalDateTime endedAt
    ) {
        validate(name, discountAmount, minOrderAmount, startedAt, endedAt);
        this.id = id;
        this.name = name;
        this.category = category;
        this.discountAmount = discountAmount;
        this.minOrderAmount = minOrderAmount;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    private void validate(
            String name,
            int discountAmount,
            int minOrderAmount,
            LocalDateTime startedAt,
            LocalDateTime endedAt
    ) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateMinOrderAmount(minOrderAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
        validateIssuedPeriod(startedAt, endedAt);
    }

    private void validateName(String name) {
        if (name == null || name.isEmpty() || name.length() > MAX_NAME_LENGTH) {
            throw new IllegalArgumentException("쿠폰의 이름은 1자 이상 30자 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount < MIN_DISCOUNT_AMOUNT || MAX_DISCOUNT_AMOUNT < discountAmount) {
            throw new IllegalArgumentException("쿠폰의 할인 금액은 1000원 이상 10000원 이하여야 합니다.");
        }

        if (discountAmount % DISCOUNT_AMOUNT_UNIT != 0) {
            throw new IllegalArgumentException("쿠폰의 할인 금액은 500원 단위로 설정해야 합니다.");
        }
    }

    private void validateMinOrderAmount(Integer minOrderAmount) {
        if (minOrderAmount < MIN_ORDER_AMOUNT || MAX_ORDER_AMOUNT < minOrderAmount) {
            throw new IllegalArgumentException("쿠폰의 최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(int discountAmount, int minOrderAmount) {
        long discountRate = Math.round((double) discountAmount / minOrderAmount * 100);
        if (discountRate < MIN_DISCOUNT_RATE || MAX_DISCOUNT_RATE < discountRate) {
            throw new IllegalArgumentException("쿠폰의 할인율은 3% 이상 20% 이하여야 합니다.");
        }
    }

    private void validateIssuedPeriod(LocalDateTime startedAt, LocalDateTime endedAt) {
        if (endedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("쿠폰의 종료일은 시작일보다 이후여야 합니다.");
        }
    }

    public Coupon(
            String name,
            CouponCategory category,
            int discountAmount,
            int minOrderAmount,
            LocalDateTime startedAt,
            LocalDateTime endedAt
    ) {
        this(
                null,
                name,
                category,
                discountAmount,
                minOrderAmount,
                startedAt,
                endedAt
        );
    }
}
