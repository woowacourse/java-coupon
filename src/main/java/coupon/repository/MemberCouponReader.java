package coupon.repository;

import coupon.domain.Coupon;
import coupon.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCouponReader {

    private final MemberCouponRepository memberCouponRepository;

    public long countIssueWithMemberAndCoupon(final Member member, final Coupon coupon) {
        return memberCouponRepository.findByMemberIdAndCouponId(member.getId(), coupon.getId())
                .stream()
                .count();
    }
}
