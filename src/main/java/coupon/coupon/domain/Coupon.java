package coupon.coupon.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "discount_amount")
    private int discountAmount;
    @Column(name = "minimum_order_price")
    private int minimumOrderPrice;
    @Column(name = "coupon_status", columnDefinition = "VARCHAR(30)")
    @Enumerated(value = EnumType.STRING)
    private CouponStatus couponStatus;
    @Column(name = "issuable", columnDefinition = "BOOLEAN")
    private boolean issuable;
    @Column(name = "usable", columnDefinition = "BOOLEAN")
    private boolean usable;
    @Column(name = "issue_started_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueStartedAt;
    @Column(name = "issue_ended_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime issueEndedAt;
    @Column(name = "limit_type", columnDefinition = "VARCHAR(20)")
    @Enumerated(value = EnumType.STRING)
    private CouponLimitType limitType;
    @Column(name = "issue_limit")
    private Long issueLimit;
    @Column(name = "issue_count")
    private Long issueCount;
    @Column(name = "use_limit")
    private Long useLimit;
    @Column(name = "use_count")
    private Long useCount;
    @Column(name = "created_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime createdAt;
    @Column(name = "modified_at", columnDefinition = "DATETIME(6)")
    private LocalDateTime modifiedAt;

    public void issue() {
        if (this.issueStartedAt.isAfter(LocalDateTime.now()) || this.issueEndedAt.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("쿠폰을 발급할 수 없는 시간입니다.");
        }
        if (couponStatus.isNotIssuable() || !this.issuable) {
            throw new IllegalArgumentException("쿠폰을 발급할 수 없는 상태입니다.");
        }
        if (this.limitType.isNotIssueCountLimit()) {
            return;
        }
        if (this.issueLimit <= this.issueCount) {
            throw new IllegalArgumentException("쿠폰을 더 이상 발급할 수 없습니다.");
        }
        this.issueCount++;
    }

    public boolean isIssuableCoupon(LocalDateTime localDateTime) {
        if (this.issueStartedAt.isAfter(localDateTime) || this.issueEndedAt.isBefore(localDateTime)) {
            return false;
        }
        if (couponStatus.isNotIssuable() || !this.issuable) {
            return false;
        }
        if (this.limitType.isNotIssueCountLimit()) {
            return true;
        }
        return this.issueLimit > this.issueCount;
    }

    public void use() {
        if (couponStatus.isNotUsable() || !this.usable) {
            throw new IllegalArgumentException("쿠폰 사용이 불가능합니다.");
        }
        if (this.limitType.isNotUseCountLimit()) {
            return;
        }
        if (this.useLimit <= this.useCount) {
            throw new IllegalArgumentException("쿠폰을 더 이상 사용할 수 없습니다.");
        }
        this.useCount++;
    }

    public boolean isUsableCoupon() {
        if (couponStatus.isNotUsable() || !this.usable) {
            return false;
        }
        if (this.limitType.isNotUseCountLimit()) {
            return true;
        }
        return this.useLimit > this.useCount;
    }

    public void setStatus(CouponStatus couponStatus) {
        this.couponStatus = couponStatus;
    }

    public void setIssuable(boolean issuable) {
        this.issuable = issuable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }
}
