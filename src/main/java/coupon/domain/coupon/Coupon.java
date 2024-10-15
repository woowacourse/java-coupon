package coupon.domain.coupon;

import java.math.BigDecimal;
import coupon.exception.CouponException;
import coupon.exception.ExceptionType;
import jakarta.persistence.Column;
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

    private static final int MINIMUM_DISCOUNT_RATE = 3;
    private static final int MAXIMUM_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinimumOrderAmount minimumOrderAmount;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponCategory category;

    @Embedded
    private CouponPeriod period;

    public Coupon(
            CouponName name,
            DiscountAmount discountAmount,
            MinimumOrderAmount minimumOrderAmount,
            CouponCategory category,
            CouponPeriod period
    ) {
        this(null, name, discountAmount, minimumOrderAmount, category, period);
    }

    public Coupon(
            Long id,
            CouponName name,
            DiscountAmount discountAmount,
            MinimumOrderAmount minimumOrderAmount,
            CouponCategory category,
            CouponPeriod period
    ) {
        this.id = id;
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.period = period;
        validateDiscountRate(discountAmount, minimumOrderAmount);
    }

    private void validateDiscountRate(DiscountAmount discountAmount, MinimumOrderAmount minimumOrderAmount) {
        BigDecimal discountRate = discountAmount.getDiscountRate(minimumOrderAmount);

        if (discountRate.compareTo(BigDecimal.valueOf(MINIMUM_DISCOUNT_RATE)) < 0 ||
            discountRate.compareTo(BigDecimal.valueOf(MAXIMUM_DISCOUNT_RATE)) > 0) {
            throw new CouponException(ExceptionType.COUPON_DISCOUNT_RATE);
        }
    }
}
