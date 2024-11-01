package coupon.controller;

import coupon.domain.MemberCoupon;
import coupon.dto.CouponIssueRequest;
import coupon.dto.MemberCouponResponse;
import coupon.service.MemberService;
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

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/{memberId}/coupons")
    public List<MemberCouponResponse> getCoupons(@PathVariable Long memberId) {
        return memberService.getCoupons(memberId);

    }

    @PostMapping("/{memberId}/coupons")
    public MemberCoupon issueCoupon(@PathVariable Long memberId, @RequestBody CouponIssueRequest couponIssueRequest) {
        return memberService.issueCoupon(memberId, couponIssueRequest.couponId());
    }
}
