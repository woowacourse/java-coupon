package coupon.controller;

import coupon.domain.Coupon;
import coupon.dto.CouponSaveRequest;
import coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping("/coupons")
    public ResponseEntity<Long> save(@Valid @RequestBody CouponSaveRequest couponSaveRequest) {
        Coupon coupon = couponService.save(couponSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(coupon.getId());
    }
}
