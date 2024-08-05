package coupon.coupon.ui;

import static java.util.stream.Collectors.toList;

import coupon.coupon.domain.Coupon;
import coupon.coupon.service.CouponService;
import coupon.coupon.service.MemberCouponService;
import coupon.coupon.ui.dto.CouponResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;
    private final MemberCouponService memberCouponService;

    public CouponController(CouponService couponService, MemberCouponService memberCouponService) {
        this.couponService = couponService;
        this.memberCouponService = memberCouponService;
    }

    @GetMapping("{couponId:^\\d+$}")
    public CouponResponse getCoupon(@PathVariable("couponId") Long couponId) {
        return CouponResponse.from(couponService.getCoupon(couponId));
    }

    @GetMapping("{couponId:^\\d+$}/issued-count")
    public Long getCouponIssuedCount(@PathVariable("couponId") Long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);
        return memberCouponService.findIssuedCouponCount(coupon.getId());
    }

    @GetMapping("{couponId:^\\d+$}/used-count")
    public Long getCouponUsedCount(@PathVariable("couponId") Long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);
        return memberCouponService.findUsedCouponCount(coupon.getId());
    }

    @GetMapping("/issuable")
    public List<CouponResponse> findIssuableCoupons() {
        return couponService.findIssuableCoupons().stream()
                .map(CouponResponse::from)
                .collect(toList());
    }
}
