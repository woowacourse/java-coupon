package coupon.service;

import coupon.domain.Coupon;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponDBService couponDBService;

    public CouponService(CouponDBService couponDBService) {
        this.couponDBService = couponDBService;
    }

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponDBService.create(coupon);
    }

    public Coupon findCoupon(long couponId) {
        return couponDBService.findById(couponId);
    }
}
