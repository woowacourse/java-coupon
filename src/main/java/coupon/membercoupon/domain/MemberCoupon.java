package coupon.membercoupon.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import coupon.coupons.domain.Coupon;
import coupon.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member_coupon")
public class MemberCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    public MemberCoupon(Long couponId, Long memberId, LocalDateTime issuedAt) {
        validateIssuedAt(issuedAt);
        this.couponId = couponId;
        this.memberId = memberId;
        this.isUsed = false;
        this.issuedAt = issuedAt;
        this.expiresAt = calculateExpiryDate(issuedAt);
    }

    public MemberCoupon(Coupon coupon, Member member, LocalDateTime issuedAt) {
        this(coupon.getId(), member.getId(), issuedAt);
    }

    private void validateIssuedAt(LocalDateTime issuedAt) {
        if (Objects.isNull(issuedAt)) {
            throw new IllegalArgumentException("발급 일시는 반드시 존재해야 합니다.");
        }
    }

    private LocalDateTime calculateExpiryDate(LocalDateTime issuedAt) {
        return issuedAt.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
    }
}
