package coupon.controller;

import coupon.domain.Member;
import coupon.domain.MemberCoupon;
import coupon.dto.MemberCouponResponse;
import coupon.service.CouponService;
import coupon.service.MemberCouponService;
import coupon.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberCouponController {

    private final MemberService memberService;
    private final MemberCouponService memberCouponService;
    private final CouponService couponService;

    @GetMapping("/members/{id}/coupons")
    public ResponseEntity<List<MemberCouponResponse>> readMemberCoupons(@PathVariable("id") Long memberId) {
        Member member = memberService.readMember(memberId);
        List<MemberCoupon> memberCoupons = memberCouponService.readAllMemberCoupons(member);

        List<MemberCouponResponse> responses = memberCoupons.stream()
                .map(memberCoupon -> MemberCouponResponse.of(memberCoupon, couponService.getCoupon(memberCoupon.getCouponId())))
                .toList();

        return ResponseEntity.ok(responses);
    }
}
