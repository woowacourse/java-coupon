package coupon.domain.membercoupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int COUPON_EXPIRATION_DAYS = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long couponId;

    public MemberCoupon(LocalDateTime issuedAt, Long memberId, Long couponId) {
        this.used = false;
        this.issuedAt = issuedAt;
        this.expiredAt = toExpiredAt(issuedAt);
        this.memberId = memberId;
        this.couponId = couponId;
    }

    public static MemberCoupon issue(LocalDateTime issuedAt, Member member, Coupon coupon) {
        coupon.issue(issuedAt);
        return new MemberCoupon(issuedAt, member.getId(), coupon.getId());
    }

    private LocalDateTime toExpiredAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(COUPON_EXPIRATION_DAYS)
                .with(LocalTime.of(23, 59, 59, 999_999_000));
    }
}
