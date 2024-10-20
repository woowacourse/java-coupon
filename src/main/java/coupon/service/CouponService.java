package coupon.service;

import coupon.domain.coupon.Coupon;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    private final CouponDBService couponDBService;

    public CouponService(CouponDBService couponDBService) {
        this.couponDBService = couponDBService;
    }

    public Coupon create(Coupon coupon) {
        return couponDBService.create(coupon);
    }

    public Coupon findCoupon(long couponId) {
        return couponDBService.findById(couponId);
    }
}
