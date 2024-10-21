package coupon.membercoupon.domain;

import java.time.LocalDateTime;
import coupon.coupon.domain.Coupon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupons")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCoupon {

    private static final int COUPON_USABLE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long id;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "used", nullable = false)
    private boolean used;

    @Column(name = "issued_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime issuedAt;

    @Column(name = "expired_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime expiredAt;

    public MemberCoupon(Long couponId, Long memberId, boolean used, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this(null, couponId, memberId, used, issuedAt, expiredAt);
    }

    public MemberCoupon(
            Long id,
            Long couponId,
            Long memberId,
            boolean used,
            LocalDateTime issuedAt,
            LocalDateTime expiredAt
    ) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.used = used;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public static MemberCoupon issue(Long memberId, Coupon coupon) {
        return new MemberCoupon(
                coupon.getId(),
                memberId,
                false,
                LocalDateTime.now(),
                coupon.getIssueEndedAt().plusDays(COUPON_USABLE_DAYS)
        );
    }
}
