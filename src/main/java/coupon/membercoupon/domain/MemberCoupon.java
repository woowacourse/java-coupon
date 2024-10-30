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

    private static final int COUPON_USABLE_DAYS = 6;

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

    @Column(name = "expires_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime expiresAt;

    public static MemberCoupon issue(Long memberId, Coupon coupon) {
        coupon.issue();

        return new MemberCoupon(
                coupon.getId(),
                memberId,
                false,
                LocalDateTime.now()
        );
    }

    public MemberCoupon(Long couponId, Long memberId, boolean used, LocalDateTime issuedAt) {
        this(null, couponId, memberId, used, issuedAt, issuedAt.plusDays(COUPON_USABLE_DAYS));
    }

    public MemberCoupon(
            Long id,
            Long couponId,
            Long memberId,
            boolean used,
            LocalDateTime issuedAt,
            LocalDateTime expiresAt
    ) {
        this.id = id;
        this.couponId = couponId;
        this.memberId = memberId;
        this.used = used;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }
}
