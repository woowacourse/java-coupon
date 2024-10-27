package coupon.coupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(nullable = false, name = "coupon_id")
    private Coupon coupon;

    @Column(nullable = false)
    private boolean used;

    @Column(nullable = false)
    private LocalDateTime issuedAt;

    @Column(nullable = false)
    private LocalDateTime expiredAt;

    protected MemberCoupon() {
    }

    public static MemberCoupon issue(Member member, Coupon coupon) {
        return new MemberCoupon(member, coupon);
    }

    public MemberCoupon(Member member, Coupon coupon) {
        this.member = member;
        this.coupon = coupon;
        this.used = false;
        this.issuedAt = LocalDateTime.now();
        this.expiredAt = calculateExpiredAt(this.issuedAt);
    }

    private LocalDateTime calculateExpiredAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(7).with(LocalTime.of(23, 59, 59, 999_999_000));
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
