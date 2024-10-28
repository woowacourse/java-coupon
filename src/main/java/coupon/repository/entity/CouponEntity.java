package coupon.repository.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import coupon.domain.Coupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DISCOUNT_AMOUNT", nullable = false)
    private Long discountAmount;

    @Column(name = "MINIMUM_ORDER_AMOUNT", nullable = false)
    private Long minimumOrderAmount;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;

    public CouponEntity(
            final String name,
            final Long discountAmount,
            final Long minimumOrderAmount,
            final LocalDateTime startDate,
            final LocalDateTime endDate
    ) {
        this.name = name;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static CouponEntity toEntity(final Coupon coupon) {
        return new CouponEntity(
                coupon.getName().getValue(),
                coupon.getDiscountAmount().getValue(),
                coupon.getMinimumOrderAmount().getValue(),
                coupon.getValidityPeriod().getStartDate(),
                coupon.getValidityPeriod().getEndDate()
        );
    }

    public Coupon toDomain() {
        return new Coupon(
                name,
                discountAmount,
                minimumOrderAmount,
                startDate,
                endDate
        );
    }
}
