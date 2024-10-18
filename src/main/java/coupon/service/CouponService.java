package coupon.service;

import coupon.entity.Coupon;
import coupon.exception.CouponNotFoundException;
import coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    @Transactional
    public Long create(Coupon coupon) {
        Coupon savedCoupon = couponRepository.save(coupon);
        return savedCoupon.getId();
    }

    @Transactional(readOnly = true)
    public Coupon getCoupon(Long id) {
        return couponRepository.findById(id)
                .orElseGet(() -> getCouponFromWriteDatabase(id));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Coupon getCouponFromWriteDatabase(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(() -> new CouponNotFoundException(id));
    }
}
