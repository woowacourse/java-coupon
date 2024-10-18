package coupon.domain.coupon;

import coupon.domain.Category;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private static final int MINIMUM_DISCOUNT_RATE = 3;
    private static final int MAXIMUM_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private CouponName couponName;
    @Embedded
    private DiscountMount discountMount;
    @Embedded
    private MinimumMount minimumMount;
    private Category category;
    @Embedded
    private Period period;

    public Coupon(CouponName couponName, DiscountMount discountMount, MinimumMount minimumMount, Category category,
                  Period period) {
        validateDiscountRate(discountMount, minimumMount);
        this.couponName = couponName;
        this.discountMount = discountMount;
        this.minimumMount = minimumMount;
        this.category = category;
        this.period = period;
    }

    private void validateDiscountRate(DiscountMount discountMount, MinimumMount minimumMount) {
        float discountRate = (float) discountMount.getDiscountMount() / minimumMount.getMinimumMount() * 100;
        int truncatedDiscountRate = (int) discountRate;
        if (truncatedDiscountRate < MINIMUM_DISCOUNT_RATE || truncatedDiscountRate > MAXIMUM_DISCOUNT_RATE) {
            throw new IllegalArgumentException("쿠폰 할인율은 3% 이상, 20% 이하여야 합니다.");
        }
    }
}
