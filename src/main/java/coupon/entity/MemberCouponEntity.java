package coupon.entity;

import coupon.domain.MemberCoupon;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_coupon")
public class MemberCouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long couponId;

    @ManyToOne
    @JoinColumn(nullable = false)
    private MemberEntity member;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public static MemberCouponEntity from(MemberCoupon memberCoupon) {
        return new MemberCouponEntity(
                memberCoupon.getId(),
                memberCoupon.getCouponId(),
                MemberEntity.from(memberCoupon.getMember()),
                memberCoupon.isUsed(),
                memberCoupon.getIssuedAt(),
                memberCoupon.getExpiredAt()
        );
    }

    public MemberCoupon toMemberCoupon() {
        return new MemberCoupon(id, couponId, member.toMember(), isUsed, issuedAt, expiredAt);
    }
}
