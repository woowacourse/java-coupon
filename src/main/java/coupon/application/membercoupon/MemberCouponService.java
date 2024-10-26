package coupon.application.membercoupon;

import java.util.List;
import coupon.application.coupon.CouponReadService;
import coupon.domain.coupon.Coupon;
import coupon.domain.membercoupon.MemberCoupon;
import coupon.domain.membercoupon.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberCouponService {

    private final MemberCouponRepository memberCouponRepository;
    private final CouponReadService couponReadService;

    public List<MemberCouponResponse> getMemberCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findAllByMemberId(memberId);

        return memberCoupons.stream()
                .map(this::getMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse getMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponReadService.getCoupon(memberCoupon.getCouponId());
        return MemberCouponResponse.of(memberCoupon, coupon);
    }
}
