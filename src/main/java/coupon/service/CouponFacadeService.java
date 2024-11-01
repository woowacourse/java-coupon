package coupon.service;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponResponse;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponFacadeService {

    private final CouponService couponService;
    private final MemberCouponService memberCouponService;

    public CouponFacadeService(CouponService couponService, MemberCouponService memberCouponService) {
        this.couponService = couponService;
        this.memberCouponService = memberCouponService;
    }

    @Transactional(readOnly = true)
    public List<MemberCouponResponse> getMemberCoupon(long memberId) {
        return memberCouponService.getMemberCoupon(memberId)
                .stream()
                .map(this::toMemberCouponResponse)
                .toList();
    }

    private MemberCouponResponse toMemberCouponResponse(MemberCoupon memberCoupon) {
        Coupon coupon = couponService.getCoupon(memberCoupon.getCouponId());
        return MemberCouponResponse.of(memberCoupon, coupon);
    }
}
