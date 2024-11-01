package coupon.membercoupon.infrastructure.repository;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import coupon.membercoupon.domain.MemberCoupon;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_coupon")
@Entity
public class MemberCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    public MemberCouponEntity(final MemberCoupon memberCoupon) {
        this(
                memberCoupon.getId(),
                memberCoupon.memberId(),
                memberCoupon.couponId(),
                memberCoupon.isUsed(),
                memberCoupon.getCreatedAt(),
                memberCoupon.getExpiredAt()
        );
    }

    public MemberCouponEntity(
            final Long id,
            final Long memberId,
            final Long couponId,
            final boolean isUsed,
            final LocalDateTime createdAt,
            final LocalDateTime expiredAt
    ) {
        this.id = id;
        this.memberId = memberId;
        this.couponId = couponId;
        this.isUsed = isUsed;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }
}
