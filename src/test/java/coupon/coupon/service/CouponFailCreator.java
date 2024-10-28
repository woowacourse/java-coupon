package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponFailCreator {

    @Autowired
    CouponService couponService;

    @Transactional
    void create(Coupon coupon) {
        couponService.create(coupon);
        throw new RuntimeException("coupon save fail");
    }
}
