package coupon.membercoupon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import coupon.membercoupon.controller.dto.MemberCouponResponses;
import coupon.membercoupon.domain.MemberCoupon;
import coupon.membercoupon.service.MemberCouponService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/membercoupons")
public class MemberCouponController {

    private final MemberCouponService memberCouponService;

    @GetMapping("/member/{memberId}")
    public ResponseEntity<MemberCouponResponses> getMemberCouponsByMemberId(@PathVariable Long memberId) {
        List<MemberCoupon> memberCoupons = memberCouponService.readAllByMemberId(memberId);
        return ResponseEntity.ok(MemberCouponResponses.from(memberCoupons));
    }
}
