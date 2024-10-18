package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinimumOrderAmount minimumOrderAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Category category;

    @Embedded
    private IssuancePeriod issuancePeriod;

    public Coupon(
            Long id,
            String name,
            int minimumOrderAmount,
            int discountAmount,
            String category,
            LocalDate issuanceStart,
            LocalDate issuanceEnd
    ) {
        this.id = id;
        this.name = new CouponName(name);
        this.minimumOrderAmount = new MinimumOrderAmount(minimumOrderAmount);
        this.discountAmount = new DiscountAmount(discountAmount, this.minimumOrderAmount);
        this.category = Category.valueOf(category);
        this.issuancePeriod = new IssuancePeriod(issuanceStart, issuanceEnd);
    }

    public boolean isIssuable(LocalDateTime issuedAt) {
        return issuancePeriod.isIssuable(issuedAt);
    }
}
