package coupon.coupon.domain;

import coupon.member.domain.Member;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@EqualsAndHashCode(of = {"id"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final int EXPIRED_PERIOD = 7;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_coupon_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "is_used")
    private boolean used;

    @Column(name = "issued_at")
    private LocalDateTime issuedAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    public MemberCoupon(Coupon coupon, Member member, LocalDateTime issuedAt) {
        this(null, coupon, member, issuedAt);
    }

    public MemberCoupon(Long id, Coupon coupon, Member member, LocalDateTime issuedAt) {
        this.id = id;
        this.coupon = coupon;
        this.member = member;
        this.issuedAt = issuedAt;
        this.expiresAt = calculateExpiryDate(issuedAt);
        this.used = false;
    }

    private LocalDateTime calculateExpiryDate(LocalDateTime issuedAt) {
        return issuedAt.plusDays(EXPIRED_PERIOD).with(LocalTime.MAX);
    }

    public void useCoupon() {
        validateIsUsed();
        validateIsExpired();
        this.used = true;
    }

    private void validateIsUsed() {
        if (this.used) {
            throw new IllegalStateException("이미 사용된 쿠폰입니다.");
        }
    }

    private void validateIsExpired() {
        if (LocalDateTime.now().isAfter(expiresAt)) {
            throw new IllegalStateException("쿠폰이 만료되었습니다.");
        }
    }
}
