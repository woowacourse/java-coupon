package coupon.domain.coupon;

import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int COUPON_USABLE_DATE = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public MemberCoupon(Long memberId, Coupon coupon, LocalDateTime issuedAt) {
        validateCoupon(coupon, issuedAt);
        this.memberId = memberId;
        this.couponId = coupon.getId();
        this.isUsed = false;
        this.issuedAt = issuedAt;
        this.expiredAt = getExpiredAt(issuedAt);
    }

    private void validateCoupon(Coupon coupon, LocalDateTime issuedAt) {
        if (!coupon.isIssuable(issuedAt)) {
            throw new CouponException("쿠폰 발급 기간이 아닙니다.");
        }
    }

    private LocalDateTime getExpiredAt(LocalDateTime issuedAt) {
        return LocalDateTime.of(issuedAt.toLocalDate().plusDays(COUPON_USABLE_DATE), LocalTime.MAX);
    }

    public boolean isExpired() {
        return !expiredAt.isAfter(LocalDateTime.now());
    }
}
