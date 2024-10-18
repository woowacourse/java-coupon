package coupon.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import coupon.service.CouponService;
import coupon.service.dto.CreateCouponRequest;
import coupon.service.dto.CreateCouponResponse;
import coupon.service.dto.GetCouponResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupon")
    public ResponseEntity<CreateCouponResponse> createCoupon(@RequestBody CreateCouponRequest request) {
        final CreateCouponResponse response = couponService.createCoupon(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/coupon")
    public ResponseEntity<List<GetCouponResponse>>getCoupon() {
        final List<GetCouponResponse> response = couponService.getAllCoupon();
        return ResponseEntity.ok(response);
    }
}
