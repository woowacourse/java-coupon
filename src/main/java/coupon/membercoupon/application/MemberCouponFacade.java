package coupon.membercoupon.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import coupon.coupon.application.CouponService;
import coupon.coupon.domain.Coupons;
import coupon.membercoupon.domain.MemberCoupons;
import coupon.membercoupon.dto.FindAllMemberCouponResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberCouponFacade {

    private final CouponService couponService;
    private final MemberCouponService memberCouponService;

    @Transactional
    public FindAllMemberCouponResponse getMemberCouponsByMemberId(Long memberId) {
        MemberCoupons memberCoupons = memberCouponService.getMemberCouponsByMemberId(memberId);
        List<Long> couponIds = memberCoupons.getCouponIds();
        Coupons coupons = couponService.getAllByCouponsIdIn(couponIds);
        return FindAllMemberCouponResponse.of(memberCoupons, coupons);
    }
}
