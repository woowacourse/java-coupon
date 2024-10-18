package coupon.member.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import coupon.config.BaseTimeEntity;
import coupon.coupon.domain.entity.CouponEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id", callSuper = false)
public class MemberCoupon extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member")
    @ManyToOne(optional = false)
    private Member member;

    @JoinColumn(name = "coupon")
    @ManyToOne(optional = false)
    private CouponEntity couponEntity;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    public MemberCoupon(Member member, CouponEntity couponEntity, LocalDateTime expiresAt) {
        this(null, member, couponEntity, false, expiresAt);
    }
}
