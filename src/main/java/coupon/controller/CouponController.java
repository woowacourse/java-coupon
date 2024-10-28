package coupon.controller;


import coupon.service.CouponService;
import coupon.data.Coupon;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public List<Coupon> findAll() {
        return couponService.findCoupons();
    }
}
