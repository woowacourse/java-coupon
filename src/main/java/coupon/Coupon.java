package coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Column(name = "coupont_category", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CouponCategory category;

    @Embedded
    private CouponIssuableDuration expiryDuration;

    @Embedded
    private CouponDiscountApply discountApply;

    public Coupon(
            CouponName name,
            CouponCategory category,
            CouponIssuableDuration expiryDuration,
            String discountAmount,
            String applicableAmount
    ) {
        this(null, name, category, expiryDuration, new CouponDiscountApply(discountAmount, applicableAmount));
    }
}
