package coupon.member.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id")
    @ManyToOne(optional = false)
    private Member member;

    @JoinColumn(name = "coupon_id")
    @ManyToOne(optional = false)
    private CouponEntity couponEntity;

    @Column(name = "is_used", nullable = false)
    private boolean isUsed;

    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime created_at;

    @Column(name = "expires_at", nullable = false, columnDefinition = "DATETIME(6)")
    private LocalDateTime expiresAt;

    public MemberCoupon(Member member, CouponEntity couponEntity) {
        this(null,
                member,
                couponEntity,
                false,
                LocalDateTime.now(),
                LocalDateTime.now()
                        .plusDays(7)
                        .with(LocalTime.MAX));
    }
}
