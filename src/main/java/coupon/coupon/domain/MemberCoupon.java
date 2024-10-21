package coupon.coupon.domain;

import coupon.member.domain.Member;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MemberCoupon {

    private final Coupon coupon;
    private final Member member;
    private final boolean used;
    private final DatePeriod datePeriod;

    public MemberCoupon(Coupon coupon, Member member, boolean used, LocalDateTime issuedAt) {
        this.coupon = coupon;
        this.member = member;
        this.used = used;
        this.datePeriod = new DatePeriod(issuedAt, issuedAt.plusDays(7));
    }
}
