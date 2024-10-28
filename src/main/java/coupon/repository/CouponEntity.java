package coupon.repository;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import coupon.domain.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "DISCOUNT_RATE", nullable = false)
    private Long discountRate;

    @Column(name = "START_DATE", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "EXPIRATION_DATE", nullable = false)
    private LocalDateTime expirationDate;

    public static CouponEntity toEntity(final Coupon coupon) {
        return new CouponEntity(
                null,
                coupon.getName().getValue(),
                coupon.getDiscountAmount().getValue(),
                coupon.getMinimumOrderAmount().getValue(),
                coupon.getDiscountRate().getValue(),
                coupon.getValidityPeriod().getStartDate(),
                coupon.getValidityPeriod().getExpirationDate()
        );
    }

    public Coupon toDomain() {
        return Coupon.builder()
                .name(name)
                .discountAmount(discountAmount)
                .discountRate(discountRate)
                .minimumOrderAmount(minimumOrderAmount)
                .startDate(startDate)
                .expirationDate(expirationDate)
                .build();
    }
}
