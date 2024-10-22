package coupon.controller;

import coupon.domain.Coupon;
import coupon.domain.MemberCoupon;
import coupon.dto.CouponIssueRequest;
import coupon.service.MemberCouponService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberCouponService memberCouponService;

    public MemberController(MemberCouponService memberCouponService) {
        this.memberCouponService = memberCouponService;
    }

    @GetMapping("/{memberId}/coupons")
    public List<Coupon> getCoupons(@PathVariable Long memberId) {
        return memberCouponService.getCoupons(memberId);

    }

    @PostMapping("/{memberId}/coupons")
    public MemberCoupon issueCoupon(@PathVariable Long memberId, @RequestBody CouponIssueRequest couponIssueRequest) {
        return memberCouponService.issueCoupon(memberId, couponIssueRequest.couponId());
    }
}
