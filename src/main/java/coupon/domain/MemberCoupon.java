package coupon.domain;

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
    private Coupon coupon;

    @ManyToOne
    private Member member;

    private boolean used;
//회원에게 발급된 쿠폰은 발급일 포함 7일 동안 사용 가능하다. 만료 일의 23:59:59.999999 까지 사용할 수 있다.
    private LocalDateTime issuedAt;

    private LocalDateTime expiredAt;

    public MemberCoupon(Coupon coupon, Member member, boolean used, LocalDateTime issuedAt, LocalDateTime expiredAt) {
        this.coupon = coupon;
        this.member = member;
        this.used = used;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    protected MemberCoupon() {}

    public static MemberCoupon issue(Member member, Coupon coupon) {
        if (!coupon.issuable()) {
            throw new IllegalStateException("쿠폰을 발급할 수 없는 상태입니다: " + coupon);
        }
        return new MemberCoupon(coupon, member, false, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
    }
}
