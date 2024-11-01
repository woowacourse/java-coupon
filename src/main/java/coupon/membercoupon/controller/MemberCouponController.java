package coupon.membercoupon.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.service.MemberCouponService;

@RestController
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    public MemberCouponController(final MemberCouponService memberCouponService) {
        this.memberCouponService = memberCouponService;
    }

    @GetMapping("/api/members/{memberId}/coupons")
    public List<MemberCoupon> memberCoupons(@PathVariable("memberId") final Long memberId) {
        return memberCouponService.getMemberCoupons(memberId);
    }
}
