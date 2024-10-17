package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponRepository;
import java.util.NoSuchElementException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public Coupon create(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    public Coupon findCoupon(long couponId) {
        return couponRepository.findById(couponId)
                .orElseThrow(() -> new NoSuchElementException("coupon with id " + couponId + " not found"));
    }
}
