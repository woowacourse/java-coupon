package coupon.domain.coupon;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
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
    private MinOrderAmount minOrderAmount;

    @Embedded
    private DiscountRate discountRate;

    @Embedded
    private IssuePeriod issuePeriod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    public Coupon(String name,
                  int discountAmount,
                  int minOrderAmount,
                  LocalDateTime issueStartedAt,
                  LocalDateTime issueEndedAt,
                  Category category) {
        this.name = new CouponName(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minOrderAmount = new MinOrderAmount(minOrderAmount);
        this.discountRate = DiscountRate.calculateDiscountRate(discountAmount, minOrderAmount);
        this.issuePeriod = new IssuePeriod(issueStartedAt, issueEndedAt);
        this.category = category;
    }
}
