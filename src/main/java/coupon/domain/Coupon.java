package coupon.domain;

import java.time.LocalDateTime;
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

    public Coupon(String name, Integer discountAmount, Integer minOrderAmount, String category, LocalDateTime startDate, LocalDateTime endDate) {
        this.couponName = new CouponName(name);
        this.pricingCondition = new PricingCondition(discountAmount, minOrderAmount);
        this.category = Category.from(category);
        this.duration = new Duration(startDate, endDate);
    }
}
