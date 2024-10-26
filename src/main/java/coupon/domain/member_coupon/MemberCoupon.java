package coupon.domain.member_coupon;

import coupon.domain.coupon.Coupon;
import coupon.domain.member.Member;
import coupon.exception.CouponIssueDateException;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class MemberCoupon {
    private static final int DEFAULT_USING_PERIOD = 6;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long memberId;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    private boolean isUsed;

    private LocalDate issuedAt;

    private LocalDate expiredAt;

    public static MemberCoupon issue(long mebmerId, Coupon coupon) {
        LocalDate issuedAt = LocalDate.now();
        if(!coupon.issueAvailable(issuedAt)) {
            throw new CouponIssueDateException();
        }

        LocalDate expiredAt = issuedAt.plusDays(DEFAULT_USING_PERIOD);
        return new MemberCoupon(mebmerId, coupon, false, issuedAt, expiredAt);
    }

    public MemberCoupon(long memberId, Coupon coupon, boolean isUsed, LocalDate issuedAt, LocalDate expiredAt) {
        this.memberId = memberId;
        this.coupon = coupon;
        this.isUsed = isUsed;
        this.issuedAt = issuedAt;
        this.expiredAt = expiredAt;
    }

    public String getCouponName() {
        return coupon.getCouponName();
    }
}
