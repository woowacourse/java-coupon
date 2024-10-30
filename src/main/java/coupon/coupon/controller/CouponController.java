package coupon.coupon.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import coupon.coupon.controller.dto.CouponResponse;
import coupon.coupon.controller.dto.CouponResponses;
import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponService;
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
        MemberCoupons memberCoupons = memberCouponService.readAllByMemberId(memberId);
        CouponResponses couponResponses = getCouponsFromMemberCoupons(memberCoupons);

        return ResponseEntity.ok(couponResponses);
    }

    private CouponResponses getCouponsFromMemberCoupons(MemberCoupons memberCoupons) {
        List<Long> couponIds = memberCoupons.getCouponIds();
        List<Coupon> coupons = couponIds.stream()
                .map(couponService::readByIdFromReaderWithCache)
                .flatMap(Optional::stream)
                .toList();

        return CouponResponses.from(coupons);
    }
}
