package coupon.domain.coupon;

import coupon.domain.Category;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
    private DiscountRate discountRate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(nullable = false)
    private IssueDuration issueDuration;

    public Coupon(
            CouponName couponName,
            DiscountAmount discountAmount,
            MinimumOrderAmount minimumOrderAmount,
            Category category,
            IssueDuration issueDuration
    ) {
        this.couponName = couponName;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.discountRate = new DiscountRate(discountAmount, minimumOrderAmount);
        this.category = category;
        this.issueDuration = issueDuration;
    }
}
