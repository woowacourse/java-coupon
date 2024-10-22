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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
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
        if (name.length() > 30) {
            throw new IllegalArgumentException("쿠폰의 이름은 30자 이하여야 합니다.");
        }
    }

    private void validateDiscountAmount(Integer discountAmount) {
        if (discountAmount < 1000 || 10000 < discountAmount) {
            throw new IllegalArgumentException("쿠폰의 할인 금액은 1000원 이상 10000원 이하여야 합니다.");
        }

        if (discountAmount % 500 != 0) {
            throw new IllegalArgumentException("쿠폰의 할인 금액은 500원 단위로 설정해야 합니다.");
        }
    }

    private void validateMinOrderAmount(Integer minOrderAmount) {
        if (minOrderAmount < 5000 || 100000 < minOrderAmount) {
            throw new IllegalArgumentException("쿠폰의 최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
        }
    }

    private void validateDiscountRate(int discountAmount, int minOrderAmount) {
        long discountRate = Math.round((double) discountAmount / minOrderAmount * 100);
        if (discountRate < 3 || 20 < discountRate) {
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
