package coupon.domain;

import coupon.domain.exception.CouponConditionException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class MemberCoupon {

    private static final int USED_DAYS_INCLUDE = 7;
    private static final LocalTime EXPIRED_TIME = LocalTime.MAX;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private Long couponId;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    public MemberCoupon(Long memberId, Coupon coupon, LocalDateTime issuedAt) {
        validateNotNull(memberId, coupon, issuedAt);
        validateIssuedAt(coupon, issuedAt);

        this.memberId = memberId;
        this.couponId = coupon.getId();
        this.isUsed = false;
        this.issuedAt = issuedAt;
        this.expiredAt = findExpiredAt(issuedAt);
    }

    private void validateNotNull(Long memberId, Coupon coupon, LocalDateTime issuedAt) {
        if (memberId == null || coupon == null || issuedAt == null) {
            throw new CouponConditionException("Member coupon must have all args.");
        }
    }

    private void validateIssuedAt(Coupon coupon, LocalDateTime issuedAt) {
        if (coupon.canIssue(issuedAt)) {
            return;
        }
        throw new CouponConditionException("Member coupon must issue when allowed coupon condition.");
    }

    private LocalDateTime findExpiredAt(LocalDateTime issuedAt) {
        LocalDate expiredDate = issuedAt.toLocalDate().plusDays(USED_DAYS_INCLUDE - 1);
        return LocalDateTime.of(expiredDate, EXPIRED_TIME);
    }
}
