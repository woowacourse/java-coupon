package coupon.membercoupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

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
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long id;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "coupon_id", nullable = false)
    private Long couponId;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed;

    @Column(name = "issued_at", nullable = false)
    private LocalDateTime issuedAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public MemberCoupon(Long memberId, Long couponId) {
        this(memberId, couponId, false);
    }

    public MemberCoupon(Long memberId, Long couponId, Boolean isUsed) {
        this.memberId = memberId;
        this.couponId = couponId;
        this.isUsed = isUsed;
        this.issuedAt = LocalDateTime.now();
        this.expiresAt = this.issuedAt.plusDays(6).with(LocalTime.MAX);
    }
}
