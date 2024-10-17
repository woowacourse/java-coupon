package coupon.coupon.domain;

import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean used;

    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;

    protected MemberCoupon() {
    }

    public MemberCoupon(Coupon coupon, Member member) {
        this(null, coupon, member);
    }

    private MemberCoupon(Long id, Coupon coupon, Member member) {
        this.id = id;
        this.coupon = coupon;
        this.member = member;
        this.used = false;
        this.issuedAt = LocalDateTime.now();
        this.expiredAt = calculateExpiredAt(this.issuedAt);
    }

    private LocalDateTime calculateExpiredAt(LocalDateTime issuedAt) {
        return issuedAt.plusDays(7).with(LocalTime.of(23, 59, 59, 999_999_000));
    }

    public Long getId() {
        return id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public Member getMember() {
        return member;
    }

    public boolean isUsed() {
        return used;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getExpiredAt() {
        return expiredAt;
    }
}
