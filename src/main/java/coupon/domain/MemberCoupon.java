package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@ToString
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Coupon coupon;

    @ManyToOne
    private Member member;

    private boolean used;

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

    public void use() {
        LocalDate today = LocalDate.now();
        LocalDate expiredDate = expiredAt.toLocalDate();

        if (today.isAfter(expiredDate)) {
            throw new IllegalStateException("쿠폰이 만료되었습니다: " + this);
        }

        used = true;
    }
}
