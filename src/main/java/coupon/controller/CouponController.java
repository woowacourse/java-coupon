package coupon.controller;

import coupon.domain.Coupon;
import coupon.service.CouponService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/{couponId}")
    public Coupon getCoupon(@PathVariable Long couponId) {
        return couponService.getCoupon(couponId);
    }

    @GetMapping("/{couponId}/immediate")
    public Coupon getCouponImmediately(@PathVariable Long couponId) {
        return couponService.getCouponImmediately(couponId);
    }

    @PostMapping
    public Coupon createCoupon(@RequestBody Coupon coupon) {
        return couponService.createCoupon(coupon);
    }
}
