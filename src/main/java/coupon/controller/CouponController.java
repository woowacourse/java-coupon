package coupon.controller;


import coupon.service.CouponService;
import coupon.data.CouponEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public List<CouponEntity> findAll() {
        return couponService.findCoupons();
    }
}
