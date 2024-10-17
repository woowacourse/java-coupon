package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
public class CouponReadService {

    private final CouponRepository couponRepository;

    public CouponReadService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon findCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new NoSuchElementException("coupon with id " + couponId + " not found"));
    }
}
