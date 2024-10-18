package membercoupon.domain;

import coupon.domain.Coupon;
import coupon.exception.CouponException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @OneToOne(fetch = FetchType.LAZY)
    @Column(nullable = false)
    private Coupon coupon;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public MemberCoupon(Long id, Long memberId, Coupon coupon, LocalDateTime issuedAt) {
        validateCoupon(coupon, issuedAt);

        this.id = id;
        this.memberId = memberId;
        this.coupon = coupon;
        this.isUsed = false;
        this.issuedAt = issuedAt;
        this.expiredAt = LocalDateTime.of(issuedAt.toLocalDate().plusDays(6), LocalTime.MAX);
    }

    private void validateCoupon(Coupon coupon, LocalDateTime issuedAt) {
        if (!coupon.isIssuable(issuedAt)) {
            throw new CouponException("쿠폰 발급 기간이 아닙니다.");
        }
    }

    public boolean isExpired() {
        return !expiredAt.isAfter(LocalDateTime.now());
    }
}
