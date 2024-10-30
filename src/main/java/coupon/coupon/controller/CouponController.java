package coupon.coupon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import coupon.coupon.controller.dto.CouponResponses;
import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponService;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.domain.MemberCoupons;
import coupon.membercoupon.service.MemberCouponService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final MemberCouponService memberCouponService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<CouponResponses> getCouponsByMember(@PathVariable Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponService.readAllByMemberId(memberId);
        return ResponseEntity.ok(getCouponResponses(memberCoupons));
    }

    private CouponResponses getCouponResponses(List<MemberCoupon> rawMemberCoupons) {
        MemberCoupons memberCoupons = new MemberCoupons(rawMemberCoupons);
        List<Long> couponIds = memberCoupons.getCouponIds();
        List<Coupon> coupons = couponService.readAllByIdsFromReaderWithCache(couponIds);

        return CouponResponses.from(coupons);
    }
}
