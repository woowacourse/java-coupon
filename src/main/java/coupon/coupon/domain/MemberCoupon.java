package coupon.coupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import coupon.coupon.CouponException;

@Entity
public class MemberCoupon {

    public static final String COUPON_ISSUE_MESSAGE = "쿠폰을 발급할 수 있는 기한이 지났습니다.";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    protected MemberCoupon() {
    }

    public static MemberCoupon issue(Member member, Coupon coupon) {
        LocalDateTime issuedAt = LocalDateTime.now();
        validate(coupon, issuedAt);
        return new MemberCoupon(member, coupon, issuedAt);
    }

    private static void validate(Coupon coupon, LocalDateTime issuedAt) {
        if (coupon.isUnableToIssue(issuedAt)) {
            throw new CouponException(COUPON_ISSUE_MESSAGE);
        }
    }

    private MemberCoupon(Member member, Coupon coupon, LocalDateTime issuedAt) {
        this.memberId = member.getId();
        this.couponId = coupon.getId();
        this.used = false;
        this.issuedAt = issuedAt;
        this.expiredAt = calculateExpiredAt(this.issuedAt);
    }

    private LocalDateTime calculateExpiredAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(7).with(LocalTime.of(23, 59, 59, 999_999_000));
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
