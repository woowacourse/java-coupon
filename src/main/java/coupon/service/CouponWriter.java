package coupon.service;

import coupon.domain.Coupon;
import coupon.repository.CouponEntity;
import coupon.repository.CouponRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CouponWriter {

    private final CouponRepository couponRepository;

    @Transactional
    public Long save(Coupon coupon) {
        CouponEntity couponEntity = new CouponEntity(coupon);
        CouponEntity savedCoupon = couponRepository.save(couponEntity);
        return savedCoupon.getId();
    }
}
