package coupon;


import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;


    @GetMapping
    public List<Coupon> findAll() {
        return couponService.getCoupons();
    }

    @GetMapping("/save")
    void saveAny() {
        couponService.create(new Coupon(UUID.randomUUID().toString()));
    }
}
