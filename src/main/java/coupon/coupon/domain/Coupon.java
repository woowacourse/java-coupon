package coupon.coupon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    private static final Long MIN_DISCOUNT_RATE = 3L;
    private static final Long MAX_DISCOUNT_RATE = 20L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private CouponName name;
    @Embedded
    private CouponDiscountAmount discountAmount;
    @Embedded
    private CouponMinimumOrderAmount minimumOrderAmount;
    @Enumerated(EnumType.STRING)
    private CouponCategory category;
    @Embedded
    private CouponIssuancePeriod issuancePeriod;

    public Coupon(
            Long id,
            String name,
            Long discountAmount,
            Long minimumOrderAmount,
            String category,
            LocalDateTime startAt,
            LocalDateTime endAt
    ) {
        validateDiscountRate(discountAmount, minimumOrderAmount);
        this.id = id;
        this.name = new CouponName(name);
        this.discountAmount = new CouponDiscountAmount(discountAmount);
        this.minimumOrderAmount = new CouponMinimumOrderAmount(minimumOrderAmount);
        this.category = CouponCategory.getCategory(category);
        this.issuancePeriod = new CouponIssuancePeriod(startAt, endAt);
    }

    private void validateDiscountRate(Long discountAmount, Long minimumOrderAmount) {
        long discountRate = discountAmount * 100 / minimumOrderAmount;
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율이 " + MIN_DISCOUNT_RATE + "% 이상, " + MAX_DISCOUNT_RATE + "% 이하여야 합니다.");
        }
    }
}
