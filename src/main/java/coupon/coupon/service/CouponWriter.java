package coupon.coupon.service;

import coupon.coupon.domain.Coupon;
import coupon.coupon.repository.CouponEntity;
import coupon.coupon.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CouponWriter {

    private final CouponRepository couponRepository;
    private final CouponCache couponCache;

    @Transactional
    public Long save(Coupon coupon) {
        CouponEntity couponEntity = new CouponEntity(coupon);
        CouponEntity savedCoupon = couponRepository.save(couponEntity);
        couponCache.save(savedCoupon);
        return savedCoupon.getId();
    }
}
