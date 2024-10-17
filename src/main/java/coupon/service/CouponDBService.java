package coupon.service;

import coupon.domain.Coupon;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;

@Service
public class CouponDBService {

    private final CouponWriteService couponWriteService;
    private final CouponReadService couponReadService;

    public CouponDBService(CouponWriteService couponWriteService, CouponReadService couponReadService) {
        this.couponWriteService = couponWriteService;
        this.couponReadService = couponReadService;
    }

    public Coupon create(Coupon coupon) {
        return couponWriteService.save(coupon);
    }

    public Coupon findById(long id) {
        try {
            return couponReadService.findCoupon(id);
        } catch (NoSuchElementException exception) {
            return couponWriteService.findCoupon(id);
        }
    }
}
