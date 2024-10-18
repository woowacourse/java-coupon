package coupon.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private CouponDiscount discount;

    @Enumerated(EnumType.STRING)
    private CouponCategory category;

    @Embedded
    private CouponIssuancePeriod period;

    public Coupon(String name,
                  BigDecimal discountAmount,
                  BigDecimal minimumOrderAmount,
                  CouponCategory category,
                  LocalDate issuanceStartDate,
                  LocalDate issuanceEndDate) {
        this(null,
                new CouponName(name),
                new CouponDiscount(discountAmount, minimumOrderAmount),
                category,
                new CouponIssuancePeriod(issuanceStartDate, issuanceEndDate)
        );
    }
}
