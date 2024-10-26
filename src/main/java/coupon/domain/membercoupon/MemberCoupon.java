package coupon.domain.membercoupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int COUPON_EXPIRATION_DAYS = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false)
    private Coupon coupon;

    public MemberCoupon(Member member, Coupon coupon, LocalDateTime issuedAt) {
        this.member = member;
        this.coupon = coupon;
        this.used = false;
        this.issuedAt = issuedAt;
        this.expiredAt = toExpiredAt(issuedAt);
    }

    private LocalDateTime toExpiredAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(COUPON_EXPIRATION_DAYS)
                .with(LocalTime.of(23, 59, 59, 999_999_000));
    }
}
