package coupon.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import coupon.domain.coupon.Coupon;

@Entity
public class MemberCoupon {

    private static final int COUPON_VALIDITY_PERIOD_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(nullable = false)
    private LocalDate issuedAt;

    protected MemberCoupon() {
    }

    public MemberCoupon(Member member, Coupon coupon, LocalDate issuedAt) {
        this.member = member;
        this.coupon = coupon;
        this.isUsed = false;
        this.issuedAt = issuedAt;
    }

    public void use(LocalDate date) {
        if (isUsed) {
            throw new IllegalStateException("이미 사용된 쿠폰은 사용할 수 없습니다.");
        }
        if (date.isBefore(issuedAt) || !date.isBefore(issuedAt.plusDays(COUPON_VALIDITY_PERIOD_DAYS))) {
            throw new IllegalArgumentException("쿠폰이 사용한 기간을 벗어났습니다.");
        }
        this.isUsed = true;
    }

    public boolean isUsed() {
        return isUsed;
    }
}
