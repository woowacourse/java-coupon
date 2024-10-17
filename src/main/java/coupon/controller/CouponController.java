package coupon.controller;

import coupon.dto.SaveCouponRequest;
import coupon.service.CouponCommandService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponCommandService couponCommandService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(SaveCouponRequest request) {
        long couponId = couponCommandService.save(request);
        return ResponseEntity.created(URI.create("/coupons/" + couponId))
                .build();
    }
}
