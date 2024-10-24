package coupon.coupon.ui;

import java.net.URI;
import coupon.coupon.application.CouponResponse;
import coupon.coupon.application.CouponService;
import coupon.coupon.application.CreateCouponRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping("/coupons/{couponId}")
    public ResponseEntity<CouponResponse> getCoupon(@PathVariable Long couponId) {
        CouponResponse responses = couponService.getCoupon(couponId);

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/coupons")
    public ResponseEntity<Void> createCoupon(@RequestBody CreateCouponRequest request) {
        Long couponId = couponService.createCoupon(request);

        URI location = URI.create("/api/v1/coupons/" + couponId);
        return ResponseEntity.created(location).build();
    }
}
