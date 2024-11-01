package coupon.domain;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "member_coupon")
public class MemberCoupon {

    private static final long COUPON_AVAILABLE_DURATION = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private long memberId;

    @Column(name = "coupon_id")
    private long couponId;

    @Column(name = "used", nullable = false)
    private boolean isUsed;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    public MemberCoupon(
            Member member,
            Coupon coupon
    ) {
        LocalDateTime temporalIssuedAt = LocalDateTime.now();
        LocalDateTime temporalExpiredAt = LocalDate.now().plusDays(COUPON_AVAILABLE_DURATION).atTime(LocalTime.MAX);
        validateIssuedAt(coupon, temporalIssuedAt);
        validateExpiration(temporalExpiredAt);
        this.memberId = member.getId();
        this.couponId = coupon.getId();
        this.isUsed = false;
        this.issuedAt = temporalIssuedAt;
        this.expiredAt = temporalExpiredAt;
    }

    private void validateIssuedAt(Coupon coupon, LocalDateTime issuedAt) {
        if (!coupon.isBetweenIssueDuration(issuedAt)) {
            throw new IllegalArgumentException("쿠폰 발급 기간에 발급되지 않은 쿠폰입니다.");
        }
    }

    private void validateExpiration(LocalDateTime expiredAt) {
        long diffDays = Duration.between(LocalDateTime.now(), expiredAt).toDays();
        if (diffDays > COUPON_AVAILABLE_DURATION) {
            throw new IllegalArgumentException("회원 쿠폰의 만료일은 발급일로부터 " + COUPON_AVAILABLE_DURATION + "이내여야 합니다");
        }
    }

    protected MemberCoupon() {
    }

    public Long getId() {
        return id;
    }

    public long getCouponId() {
        return couponId;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
