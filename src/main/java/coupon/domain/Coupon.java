package coupon.domain;

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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName couponName;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinimumOrderAmount minimumOrderAmount;

    @Embedded
    private CouponPeriod couponPeriod;

    @Enumerated(EnumType.STRING)
    private Category category;

    public Coupon(CouponName couponName, DiscountAmount discountAmount, MinimumOrderAmount minimumOrderAmount, Category category, CouponPeriod couponPeriod) {
        this.couponName = couponName;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.category = category;
        this.couponPeriod = couponPeriod;
    }
}
