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
    private Integer discountAmount;

    @Column(nullable = false)
    private Integer discountRate;

    @Column(nullable = false)
    private Integer minOrderAmount;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime endedAt;

    private Coupon(
            Long id,
            String name,
            CouponCategory category,
            Integer discountAmount,
            Integer discountRate,
            Integer minOrderAmount,
            LocalDateTime startedAt,
            LocalDateTime endedAt
    ) {
        validate(name, discountAmount, discountRate, minOrderAmount, startedAt, endedAt);
        this.id = id;
        this.name = name;
        this.category = category;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.minOrderAmount = minOrderAmount;
        this.startedAt = startedAt;
        this.endedAt = endedAt;
    }

    private void validate(
            String name,
            Integer discountAmount,
            Integer discountRate,
            Integer minOrderAmount,
            LocalDateTime startedAt,
            LocalDateTime endedAt
    ) {
        validateName(name);
        validateDiscountAmount(discountAmount);
        validateDiscountRate(discountRate);
        validateMinOrderAmount(minOrderAmount);
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

    private void validateDiscountRate(Integer discountRate) {
        if (discountRate < 3 || 20 < discountRate) {
            throw new IllegalArgumentException("쿠폰의 할인율은 3% 이상 20% 이하여야 합니다.");
        }
    }

    private void validateMinOrderAmount(Integer minOrderAmount) {
        if (minOrderAmount < 5000 || 100000 < minOrderAmount) {
            throw new IllegalArgumentException("쿠폰의 최소 주문 금액은 5000원 이상 100000원 이하여야 합니다.");
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
            Integer discountAmount,
            Integer minOrderAmount,
            LocalDateTime startedAt,
            LocalDateTime endedAt
    ) {
        this(
                null,
                name,
                category,
                discountAmount,
                Math.round((float) discountAmount / minOrderAmount * 100),
                minOrderAmount,
                startedAt,
                endedAt
        );
    }
}
