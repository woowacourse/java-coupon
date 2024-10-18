package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id", nullable = false)
    private Long id;

    @Embedded
    private CouponName couponName;

    @Embedded
    private DiscountAmount discountAmount;

    @Embedded
    private MinimumOrderAmount minimumOrderAmount;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "category", nullable = false)
    private CouponCategory couponCategory;

    private CouponPeriod couponPeriod;

    @Column(name = "issue_limit", nullable = false)
    private long issueLimit;

    @Column(name = "issue_count", nullable = false)
    private long issueCount;

    public Coupon(
            CouponName couponName,
            DiscountAmount discountAmount,
            MinimumOrderAmount minimumOrderAmount,
            CouponCategory couponCategory,
            CouponPeriod couponPeriod,
            long issueLimit,
            long issueCount
    ) {
        validateAmountRate(discountAmount, minimumOrderAmount);

        this.couponName = couponName;
        this.discountAmount = discountAmount;
        this.minimumOrderAmount = minimumOrderAmount;
        this.couponCategory = couponCategory;
        this.couponPeriod = couponPeriod;
        this.issueLimit = issueLimit;
        this.issueCount = issueCount;
    }

    public Coupon(
            String couponName,
            int discountAmount,
            int minimumOrderAmount,
            CouponCategory couponCategory,
            LocalDateTime issueStartedAt,
            LocalDateTime issueEndedAt,
            long issueLimit,
            long issueCount
    ) {
        this(
                new CouponName(couponName),
                new DiscountAmount(discountAmount),
                new MinimumOrderAmount(minimumOrderAmount),
                couponCategory,
                new CouponPeriod(issueStartedAt, issueEndedAt),
                issueLimit,
                issueCount
        );
    }

    public void issue() {
        if (couponPeriod.isIssuable()) {
            throw new IllegalArgumentException("쿠폰을 발급할 수 없는 시간입니다.");
        }
        if (issueLimit <= issueCount) {
            throw new IllegalArgumentException("쿠폰을 더 이상 발급할 수 없습니다.");
        }
        this.issueCount++;
    }

    private void validateAmountRate(DiscountAmount discountAmount, MinimumOrderAmount minimumOrderAmount) {
        int discountRate = discountAmount.getAmount() * 100 / minimumOrderAmount.getAmount();

        if (discountRate < MIN_DISCOUNT_RATE || MAX_DISCOUNT_RATE < discountRate) {
            throw new IllegalArgumentException(
                    "할인율은 " + MIN_DISCOUNT_RATE + "% 이상 " + MAX_DISCOUNT_RATE + "% 이하여야 합니다."
            );
        }
    }
}
