package coupon.domain;

import coupon.domain.time.TimeSupplier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CouponIssuer {

    private final static int EXPIRED_DAY = 7;

    private final TimeSupplier timeSupplier;

    public MemberCoupon issueMemberCoupon(final Member member, final Coupon coupon) {
        final LocalDateTime issueTime = timeSupplier.supply();
        return new MemberCoupon(coupon.getId(), member.getId(), false, issueTime, expireTime(issueTime));
    }

    private LocalDateTime expireTime(final LocalDateTime issueTime) {
        return issueTime.plusDays(EXPIRED_DAY);
    }
}
