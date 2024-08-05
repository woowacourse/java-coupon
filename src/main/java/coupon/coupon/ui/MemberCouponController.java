package coupon.coupon.ui;

import static java.util.stream.Collectors.toList;

import coupon.coupon.service.CouponIssuer;
import coupon.coupon.service.MemberCouponService;
import coupon.coupon.ui.dto.IssueCouponRequest;
import coupon.coupon.ui.dto.MemberCouponResponse;
import coupon.coupon.ui.dto.UseCouponRequest;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/member-coupons")
public class MemberCouponController {

    private final CouponIssuer couponIssuer;
    private final MemberCouponService memberCouponService;

    public MemberCouponController(CouponIssuer couponIssuer, MemberCouponService memberCouponService) {
        this.couponIssuer = couponIssuer;
        this.memberCouponService = memberCouponService;
    }

    @PostMapping
    public Long issueCoupon(@RequestBody IssueCouponRequest request) {
        return couponIssuer.issueCoupon(request.couponId(), request.memberId()).getId();
    }

    @GetMapping("/by-member-id")
    public List<MemberCouponResponse> getMemberCoupons(@RequestParam("memberId") Long memberId) {
        return memberCouponService.findUsableMemberCoupons(memberId).stream()
                .map(MemberCouponResponse::from)
                .collect(toList());
    }

    @PostMapping("/{memberCouponId:^\\d+$}/use")
    public void useCoupon(@PathVariable Long memberCouponId, @RequestBody UseCouponRequest useCouponRequest) {
        if (!memberCouponId.equals(useCouponRequest.memberCouponId())) {
            throw new IllegalArgumentException("잘못된 쿠폰 번호입니다.");
        }
        memberCouponService.useCoupon(useCouponRequest.memberId(), useCouponRequest.memberCouponId());
    }
}

