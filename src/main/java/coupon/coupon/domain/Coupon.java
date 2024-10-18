package coupon.coupon.domain;

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
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private DiscountRate discountRate;

    @Embedded
    private OrderPrice orderPrice;

    @Column(columnDefinition = "VARCHAR(255)")
    @Enumerated(value = EnumType.STRING)
    private CouponCategory category;

    @Embedded
    private IssuePeriod issuePeriod;

    public Coupon(
            CouponName name,
            DiscountAmount discountAmount,
            DiscountRate discountRate,
            OrderPrice orderPrice,
            CouponCategory category,
            IssuePeriod issuePeriod
    ) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.discountRate = discountRate;
        this.orderPrice = orderPrice;
        this.category = category;
        this.issuePeriod = issuePeriod;
    }
}
