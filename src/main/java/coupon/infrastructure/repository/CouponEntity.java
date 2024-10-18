package coupon.infrastructure.repository;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import coupon.domain.coupon.Coupon;
import coupon.domain.coupon.ProductionCategory;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
@Entity
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private int minimumOrderAmount;

    @Column(nullable = false)
    private int discountAmount;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private ProductionCategory productionCategory;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime endDate;

    public CouponEntity(final Coupon coupon) {
        this(
                null,
                coupon.getNameValue(),
                coupon.getMinimumOrderAmountValue(),
                coupon.getDiscountAmountValue(),
                coupon.getProductionCategory(),
                coupon.getCouponStartDateValue(),
                coupon.getCouponEndDateValue()
        );
    }

    public CouponEntity(
            final Long id,
            final String name,
            final int minimumOrderAmount,
            final int discountAmount,
            final ProductionCategory productionCategory,
            final LocalDateTime startDate,
            final LocalDateTime endDate
    ) {
        this.id = id;
        this.name = name;
        this.minimumOrderAmount = minimumOrderAmount;
        this.productionCategory = productionCategory;
        this.discountAmount = discountAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Coupon toDomain() {
        return Coupon.create(name, minimumOrderAmount, discountAmount, productionCategory, startDate, endDate);
    }
}
