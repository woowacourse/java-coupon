package coupon.repository;

import coupon.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCouponWriter {

    private static final int MEMBER_MAX_COUNT = 5;

    private final CouponIssuer couponIssuer;
    private final MemberCouponReader memberCouponReader;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCoupon create(final Member member, final Coupon coupon) {
        validateCount(member, coupon);
        final MemberCoupon memberCoupon = couponIssuer.issueMemberCoupon(member, coupon);
        return memberCouponRepository.save(memberCoupon);
    }

    private void validateCount(final Member member, final Coupon coupon) {
        final long count = memberCouponReader.countIssueWithMemberAndCoupon(member, coupon);
        if (count > MEMBER_MAX_COUNT) {
            throw new IllegalArgumentException(String.format("멤버당 최대 생성 개수는 %d 이하입니다", count));
        }
    }
}
