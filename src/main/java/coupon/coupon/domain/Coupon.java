package coupon.coupon.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import coupon.infrastructure.audit.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "coupon")
@EqualsAndHashCode(of = "id", callSuper = false)
public class Coupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Coupon(
            final String name,
            final Long discountAmount,
            final Long minimumOrderAmount,
            final LocalDate issueStartDate,
            final LocalDate issueEndDate
    ) {
        this.name = new Name(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.discountRate = DiscountRate.from(discountAmount, minimumOrderAmount);
        this.minimumOrderAmount = new MinimumOrderAmount(minimumOrderAmount);
        this.issuancePeriod = new IssuancePeriod(issueStartDate, issueEndDate);
    }
}
