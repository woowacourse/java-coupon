package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.service.services.CouponReaderService;
import coupon.coupon.service.services.CouponWriterService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponWriterService couponWriterService;
    private final CouponReaderService couponReaderService;

    public void create(Coupon coupon) {
        couponWriterService.create(coupon);
    }

    public Coupon getCoupon(long couponId) {
        Optional<Coupon> coupon = couponReaderService.getCoupon(couponId);
        return coupon.orElseGet(() -> couponWriterService.getCoupon(couponId));
    }
}
