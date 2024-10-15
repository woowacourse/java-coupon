package coupon.domain.coupon;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coupon_id")
    private Long id;

    @Embedded
    private CouponName name;

    @Embedded
    private CouponDiscountAmount discountAmount;

    @Embedded
    private CouponMinOrderAmount minOrderAmount;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private CouponCategory category;

    @Embedded
    private CouponIssueDate issueDate;

    public Coupon(
            String name,
            int discountAmount,
            int minOrderAmount,
            CouponCategory category,
            LocalDate issueStartDate,
            LocalDate issueEndDate
    ) {
        this(null, name, discountAmount, minOrderAmount, category, issueStartDate, issueEndDate);
    }

    public Coupon(
            Long id,
            String name,
            int discountAmount,
            int minOrderAmount,
            CouponCategory category,
            LocalDate issueStartDate,
            LocalDate issueEndDate
    ) {
        this.id = id;
        this.name = new CouponName(name);
        this.minOrderAmount = new CouponMinOrderAmount(minOrderAmount);
        this.discountAmount = new CouponDiscountAmount(discountAmount, this.minOrderAmount);
        this.category = category;
        this.issueDate = CouponIssueDate.createWithTime(issueStartDate, issueEndDate);
    }

    public LocalDateTime getIssueEndedAt() {
        return issueDate.getIssueEndedAt();
    }
}
