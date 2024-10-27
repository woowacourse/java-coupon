package coupon.coupon.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import coupon.coupon.controller.dto.CouponResponse;
import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponService;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.service.MemberCouponService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final MemberCouponService memberCouponService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<CouponResponse>> getCouponsByMember(@PathVariable Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponService.readAllByMemberId(memberId);
        List<CouponResponse> couponResponses = getCouponsFromMemberCoupons(memberCoupons);

        return ResponseEntity.ok(couponResponses);
    }

    private List<CouponResponse> getCouponsFromMemberCoupons(List<MemberCoupon> memberCoupons) {
        List<Long> couponIds = memberCoupons.stream()
                .map(MemberCoupon::getCouponId)
                .toList();

        List<Coupon> coupons = couponIds.stream()
                .map(couponService::readByIdFromReaderWithCache)
                .flatMap(Optional::stream)
                .toList();

        return coupons.stream()
                .map(coupon -> new CouponResponse(coupon))
                .toList();
    }
}
