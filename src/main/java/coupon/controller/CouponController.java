package coupon.controller;

import coupon.domain.Coupon;
import coupon.dto.request.SaveCouponRequest;
import coupon.service.CouponCommandService;
import coupon.service.CouponQueryService;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/coupons")
public class CouponController {

    private final CouponCommandService couponCommandService;
    private final CouponQueryService couponQueryService;

    @PostMapping("/save")
    public ResponseEntity<Void> save(SaveCouponRequest request) {
        long couponId = couponCommandService.save(request).getId();
        return ResponseEntity.created(URI.create("/coupons/" + couponId))
                .build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Coupon> findById(@PathVariable long id) {
        return ResponseEntity.ok(couponQueryService.findById(id));
    }
}
