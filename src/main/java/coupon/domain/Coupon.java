package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private Name name;

    @Column(name = "discount_mount", nullable = false)
    @Embedded
    private DiscountAmount discountAmount;

    @Column(name = "discount_rate", nullable = false)
    @Embedded
    private DiscountRate discountRate;

    @Column(name = "minimum_order_amount", nullable = false)
    @Embedded
    private MinimumOrderAmount minimumOrderAmount;

    @Column(name = "issuance_period", nullable = false)
    @Embedded
    private IssuancePeriod issuancePeriod;

}
