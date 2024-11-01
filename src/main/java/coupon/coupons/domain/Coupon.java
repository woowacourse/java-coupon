package coupon.coupons.domain;

import java.time.LocalDateTime;
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
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName couponName;

    @Embedded
    private PricingCondition pricingCondition;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Embedded
    private Duration duration;

    public Coupon(Long id, CouponName couponName, PricingCondition pricingCondition, Category category, Duration duration) {
        this.id = id;
        this.couponName = couponName;
        this.pricingCondition = pricingCondition;
        this.category = category;
        this.duration = duration;
    }

    public Coupon(String name, Integer discountAmount, Integer minOrderAmount, String category, LocalDateTime startDate, LocalDateTime endDate) {
        this(null, new CouponName(name), new PricingCondition(discountAmount, minOrderAmount), Category.from(category), new Duration(startDate, endDate));
    }
}
