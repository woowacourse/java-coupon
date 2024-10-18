package coupon.service;

import coupon.domain.coupon.Coupon;
import coupon.repository.CouponRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CouponWriter {

    private final CouponRepository couponRepository;

    public CouponWriter(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public Coupon save(Coupon coupon){
        return couponRepository.save(coupon);
    }

    public Coupon findCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new NoSuchElementException("coupon with id " + couponId + " not found"));
    }
}
