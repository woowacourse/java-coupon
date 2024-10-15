package coupon.controller;

import coupon.domain.Coupon;
import coupon.dto.CouponCreateRequest;
import coupon.repository.CouponRepository;
import coupon.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/coupons")
    public ResponseEntity<Void> createCoupon(CouponCreateRequest request) {
        Coupon coupon = request.toEntity();
        couponService.createCoupon(coupon);

        return ResponseEntity.created(URI.create("/coupons/" + coupon.getId())).build();
    }

    @GetMapping("/coupons/{id}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable Long id) {
        Coupon coupon = couponService.getCoupon(id);

        return ResponseEntity.ok(coupon);
    }
}
