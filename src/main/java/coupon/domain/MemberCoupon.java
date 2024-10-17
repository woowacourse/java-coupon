package coupon.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCoupon {

    private static final long COUPON_USABLE_DAYS = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private boolean used;

    private LocalDateTime issuedAt;

    private LocalDateTime useEndedAt;

    public MemberCoupon(Coupon coupon, Member member, boolean used, LocalDateTime issuedAt, LocalDateTime useEndedAt) {
        this.coupon = coupon;
        this.member = member;
        this.used = used;
        this.issuedAt = issuedAt;
        this.useEndedAt = useEndedAt;
    }

    public static MemberCoupon issue(Member member, Coupon coupon) {
        coupon.issue();

        MemberCoupon memberCoupon = new MemberCoupon();
        memberCoupon.member = member;
        memberCoupon.coupon = coupon;
        memberCoupon.issuedAt = LocalDateTime.now();
        memberCoupon.useEndedAt = memberCoupon.issuedAt.plusDays(COUPON_USABLE_DAYS);
        memberCoupon.used = false;

        return memberCoupon;
    }
}
