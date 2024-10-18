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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int COUPON_EXPIRATION_DAYS = 7;

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
        this.expiredAt = getExpiredAt(issuedAt);
    }

    private LocalDateTime getExpiredAt(LocalDateTime issuedAt) {
        LocalDate expirationDate = issuedAt.plusDays(COUPON_EXPIRATION_DAYS).toLocalDate();
        LocalTime expirationTime = LocalTime.MAX;
        return LocalDateTime.of(expirationDate, expirationTime);
    }
}
