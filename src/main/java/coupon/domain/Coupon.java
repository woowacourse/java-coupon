package coupon.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CouponName name;

    @Column(name = "coupon_category", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CouponCategory category;

    @Embedded
    private CouponIssuableDuration expiryDuration;

    @Embedded
    private CouponDiscountApply discountApply;

    public Coupon(
            CouponName name,
            CouponCategory category,
            CouponIssuableDuration expiryDuration,
            String discountAmount,
            String applicableAmount
    ) {
        this(null, name, category, expiryDuration, new CouponDiscountApply(discountAmount, applicableAmount));
    }

    public MemberCoupon issue(Long memberId) {
        validateIssuable();

        return MemberCoupon.issue(id, memberId);
    }

    private void validateIssuable() {
        if (!expiryDuration.isIssuable()) {
            throw new IllegalStateException("쿠폰 발급 기간이 지났습니다.");
        }
    }
}
