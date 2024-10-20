package coupon.domain;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Coupon coupon;

    @Column(name = "used", nullable = false)
    private boolean isUsed;

    @Column(name = "issued_time", nullable = false)
    private LocalDateTime issuedTime;

    @Column(name = "expiration", nullable = false)
    private LocalDateTime expiration;

    public MemberCoupon(Member member, boolean isUsed, LocalDateTime issuedTime, LocalDateTime expiration) {
        this.id = null;
        this.member = member;
        this.isUsed = isUsed;
        this.issuedTime = issuedTime;
        this.expiration = expiration;
    }

    protected MemberCoupon() {
    }
}
