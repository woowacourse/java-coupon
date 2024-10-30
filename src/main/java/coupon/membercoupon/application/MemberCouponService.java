package coupon.membercoupon.application;

import java.util.List;
import coupon.coupon.application.CouponResponse;
import coupon.coupon.application.CouponService;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCouponService {

    private final CouponService couponService;
    private final MemberCouponRepository memberCouponRepository;
    private final MemberCouponMapper memberCouponMapper;

    @Transactional(readOnly = true)
    public List<MemberCouponWithCouponResponse> getMemberCouponWithCoupons(Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponRepository.findByMemberId(memberId);

        return memberCoupons.stream()
                .map(memberCoupon -> {
                    CouponResponse couponResponse = couponService.getCoupon(memberCoupon.getCouponId());
                    return memberCouponMapper.toWithCouponResponse(memberCoupon, couponResponse);
                })
                .toList();
    }
}
