package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.repository.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private static final int COUPON_ISSUE_LIMIT = 5;
    private final MemberCouponRepository memberCouponRepository;

    public MemberCoupon issue(Member member, Coupon coupon) {
        validate(member, coupon);
        return MemberCoupon.issue(member, coupon);
    }

    private void validate(Member member, Coupon coupon) {
        if (memberCouponRepository.countByMemberAndCoupon(member, coupon) > COUPON_ISSUE_LIMIT) {
            throw new IllegalArgumentException("발급 수량 제한을 초과하였습니다.");
        }
    }

    public List<MemberCoupon> readAllMemberCoupons(Member member) {
        return memberCouponRepository.findByMember(member);
    }
}
