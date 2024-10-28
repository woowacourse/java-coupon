package coupon.controller;

import coupon.entity.Coupon;
import coupon.entity.CouponCategory;
import coupon.service.CouponService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupon/test/{name}")
    public ResponseEntity<String> create(@PathVariable String name) {
        Coupon coupon = couponService.create(Coupon.builder()
                .name(name)
                .discountAmount(1000)
                .minimumOrderAmount(5000)
                .category(CouponCategory.FASHION)
                .validFrom(LocalDateTime.now())
                .validTo(LocalDateTime.now().plusDays(7))
                .build());
        Coupon saved = couponService.create(coupon);

        return ResponseEntity.ok("couponId: " + saved.getId());
    }

    @GetMapping("/coupon/test/{couponId}")
    public ResponseEntity<Coupon> getCoupon(@PathVariable Long couponId) {
        Coupon coupon = couponService.getCoupon(couponId);

        return ResponseEntity.ok(coupon);
    }
}
