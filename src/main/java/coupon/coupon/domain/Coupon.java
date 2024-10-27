package coupon.coupon.domain;

import java.time.LocalDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    protected static final long MIN_DISCOUNT_RATE = 3L;
    protected static final long MAX_DISCOUNT_RATE = 20L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "name", nullable = false))
    private CouponName name;
    @Embedded
    @AttributeOverride(name = "discountAmount", column = @Column(name = "discount_amount", nullable = false))
    private CouponDiscountAmount discountAmount;
    @Embedded
    @AttributeOverride(name = "minimumOrderAmount", column = @Column(name = "minimum_order_amount", nullable = false))
    private CouponMinimumOrderAmount minimumOrderAmount;
    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CouponCategory category;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "startDate", column = @Column(name = "start_date", nullable = false)),
            @AttributeOverride(name = "endDate", column = @Column(name = "end_date", nullable = false))
    })
    private CouponIssuancePeriod issuancePeriod;

    public Coupon(
            Long id,
            String name,
            Long discountAmount,
            Long minimumOrderAmount,
            String category,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        this(name, discountAmount, minimumOrderAmount, category, startDate, endDate);
        this.id = id;
    }

    public Coupon(
            String name,
            Long discountAmount,
            Long minimumOrderAmount,
            String category,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        validateDiscountRate(discountAmount, minimumOrderAmount);
        this.name = new CouponName(name);
        this.discountAmount = new CouponDiscountAmount(discountAmount);
        this.minimumOrderAmount = new CouponMinimumOrderAmount(minimumOrderAmount);
        this.category = CouponCategory.getCategory(category);
        this.issuancePeriod = new CouponIssuancePeriod(startDate, endDate);
    }

    private void validateDiscountRate(Long discountAmount, Long minimumOrderAmount) {
        long discountRate = discountAmount * 100 / minimumOrderAmount;
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율이 " + MIN_DISCOUNT_RATE + "% 이상, " + MAX_DISCOUNT_RATE + "% 이하여야 합니다.");
        }
    }
}
