package coupon.coupon.service;

import coupon.coupon.repository.CouponEntity;
import coupon.coupon.repository.IssuedCouponEntity;
import coupon.coupon.service.dto.CouponResponse;
import coupon.coupon.service.dto.IssuedCouponResponse;
import coupon.coupon.service.dto.MemberCouponResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CouponLookupFacade {

    private final CouponLookupService couponLookupService;
    private final IssuedCouponLookupService issuedCouponLookupService;

    public List<CouponResponse> getAllIssuableCoupons() {
        return couponLookupService.getAllIssuableCoupons()
                .stream()
                .map(CouponResponse::from)
                .toList();
    }

    public MemberCouponResponse getAllIssuableCouponsAndIssuedCoupons(long memberId) {
        List<CouponResponse> issuableCoupons = couponLookupService.getAllIssuableCoupons()
                .stream()
                .map(CouponResponse::from)
                .toList();
        List<IssuedCouponResponse> issuedCoupons = issuedCouponLookupService.getAllIssuedCouponForMember(memberId)
                .stream()
                .map(this::mapIssuedCouponResponse)
                .toList();
        return new MemberCouponResponse(issuableCoupons, issuedCoupons);
    }

    private IssuedCouponResponse mapIssuedCouponResponse(IssuedCouponEntity issuedCoupon) {
        long couponId = issuedCoupon.getCouponId();
        CouponEntity coupon = couponLookupService.getCoupon(couponId);
        return IssuedCouponResponse.from(issuedCoupon, coupon);
    }
}
