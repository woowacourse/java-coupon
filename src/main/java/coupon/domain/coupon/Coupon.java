package coupon.domain.coupon;

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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    private static final int MIN_DISCOUNT_RATE = 3;
    private static final int MAX_DISCOUNT_RATE = 20;

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
    private IssuePeriod issuePeriod;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    public Coupon(String name,
                  int discountAmount,
                  int minOrderAmount,
                  LocalDate issueStartedDate,
                  LocalDate issueEndedDate,
                  Category category) {
        this.name = new CouponName(name);
        this.discountAmount = new DiscountAmount(discountAmount);
        this.minOrderAmount = new MinOrderAmount(minOrderAmount);
        validateDiscountRate(discountAmount, minOrderAmount);
        this.issuePeriod = new IssuePeriod(issueStartedDate, issueEndedDate);
        this.category = category;
    }

    private void validateDiscountRate(int discountAmount, int minOrderAmount) {
        int discountRate = (int) Math.floor((double) discountAmount / minOrderAmount * 100);
        if (discountRate < MIN_DISCOUNT_RATE || discountRate > MAX_DISCOUNT_RATE) {
            throw new IllegalArgumentException("할인율은 %d%% 이상 %d%% 이하여야 합니다."
                    .formatted(MIN_DISCOUNT_RATE, MAX_DISCOUNT_RATE));
        }
    }

    public void issue(LocalDateTime now) {
        if (!issuePeriod.isIssuable(now)) {
            throw new IllegalArgumentException("쿠폰 발급 기간이 아닙니다.");
        }
    }

    public String getName() {
        return name.getName();
    }

    public int getDiscountAmount() {
        return discountAmount.getDiscountAmount();
    }

    public int getMinOrderAmount() {
        return minOrderAmount.getMinOrderAmount();
    }

    public LocalDateTime getIssueStartedAt() {
        return issuePeriod.getIssueStartedAt();
    }

    public LocalDateTime getIssueEndedAt() {
        return issuePeriod.getIssueEndedAt();
    }
}
