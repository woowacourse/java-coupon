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
        this.couponName = couponName;
        this.discountMount = discountMount;
        this.minimumMount = minimumMount;
        this.category = category;
        this.period = period;
    }
}
