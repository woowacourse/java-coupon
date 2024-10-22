package coupon.entity;

import coupon.domain.MemberCoupon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "used", columnDefinition = "boolean")
    private Boolean used;

    @Column(name = "issued_at", columnDefinition = "datetime(6)")
    private LocalDateTime issuedAt;

    @Column(name = "expired_at", columnDefinition = "datetime(6)")
    private LocalDateTime expiredAt;

    public MemberCouponEntity(MemberCoupon memberCoupon) {
        this.memberId = memberCoupon.getMemberId();
        this.couponId = memberCoupon.getCouponId();
        this.used = memberCoupon.getUsed();
        this.issuedAt = memberCoupon.getIssuedAt();
        this.expiredAt = memberCoupon.getExpiredAt();
    }

    public MemberCoupon toDomain() {
        return new MemberCoupon(id, memberId, couponId, used, issuedAt, expiredAt);
    }
}
